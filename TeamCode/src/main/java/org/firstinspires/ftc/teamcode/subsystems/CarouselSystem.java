package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.RobotConfig;

import java.util.Timer;
import java.util.TimerTask;

public class CarouselSystem extends DeviceBase {

    public DcMotorEx motor = null;

    double speed = 0.0;
    double speedPlusMax = 360.0;
    double speedPlus = 0;
    double speedIncressIntervel = 2000;

    Timer timer = new Timer();

    // 用于多线程控制
    class CarouselControlBezierSpeed extends TimerTask {
        ElapsedTime speedTimer = new ElapsedTime();

        @Override
        public void run() {
            speedTimer.reset();
            while (speedTimer.milliseconds() < speedIncressIntervel) {
                double bezierRate = getBezier(0.8,0.4,speedTimer.milliseconds() / speedIncressIntervel);
                speedPlus = bezierRate * speedPlusMax;
                motor.setVelocity(speed + speedPlus, AngleUnit.DEGREES);
            }
        }
        // 三阶贝塞尔曲线  2个数据点和2个控制点
        // 公式：B(t) = P0 * (1-t)^3 + 3 * P1 * t * (1-t)^2 + 3 * P2 * t^2 * (1-t) + P3 * t^3
        double getBezier(double p1, double p2, double t) {
            return 0 * Math.pow((1 - t), 3)
                    + 3 * p1 * t * Math.pow((1 - t), 2)
                    + 3 * p2 * Math.pow(t, 2) * (1 - t)
                    + 1 * Math.pow(t, 3);
        }
    }

    // 用于多线程控制
    class CarouselControlAutoStillSpeed extends TimerTask{
        ElapsedTime speedTimer = new ElapsedTime();
        @Override
        public void run() {
            speedTimer.reset();
            while(speedTimer.milliseconds()<speedIncressIntervel){
                speedPlus = speedTimer.milliseconds() * speedPlusMax / speedIncressIntervel;
                motor.setVelocity(speed+speedPlus, AngleUnit.DEGREES);
            }
        }
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        super.init(hardwareMap);

        motor = hardwareMap.get(DcMotorEx.class, RobotConfig.CAROUSEL_MOTOR);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setVelocity(0);
    }


    public void action(double[] pos){
        motor.setTargetPosition((int) pos[0]);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void goBerzer(){
        CarouselControlBezierSpeed task = new CarouselControlBezierSpeed();
        timer.schedule(task, 0);
    }

    public void goBerzer(double timeInMs){
        speedIncressIntervel = timeInMs;
        CarouselControlBezierSpeed task = new CarouselControlBezierSpeed();
        timer.schedule(task, 0);
    }

    public void goStill(){
        CarouselControlAutoStillSpeed task = new CarouselControlAutoStillSpeed();
        timer.schedule(task, 0);
    }

    public void goStill(double timeInMs){
        speedIncressIntervel = timeInMs;
        CarouselControlAutoStillSpeed task = new CarouselControlAutoStillSpeed();
        timer.schedule(task, 0);
    }

    public void goByTick(int tick){
        motor.setVelocity(tick);
    }

    public void goByDegree(int degree){
        motor.setVelocity(degree, AngleUnit.DEGREES);
    }

    public void loop() {

    }

    @Override
    public void stop() {
        motor.setVelocity(0);
    }


    @Override
    public void autoLoop(double time){
        super.autoLoop(time);
        switch(tasks.getName()){
            case TASK_CAROUSEL_BEZIER_ACC:
                goBerzer();
                break;
            case TASK_CAROUSEL_STILL_ACC:
                goStill();
                break;
            case TASK_CAROUSEL_STILL:
                goByDegree(tasks.getI(0));
                break;

            case TASK_STOP_WAIT:
                stop();
                break;
            case TASK_WAIT:
                break;
            case TASK_STOP:
                stop();
                break;

        }
    }
}
