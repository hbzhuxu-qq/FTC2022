package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.RobotConfig;

import java.util.Timer;
import java.util.TimerTask;

public class IntakeSystem extends DeviceBase {

    public DcMotorEx collector = null;
    public static final double SPEED_ROUND = 2;
    public static final double SPEED_DEGREE = SPEED_ROUND*360;
    Timer timer = new Timer();
    boolean isRunning = true;


    @Override
    public void init(HardwareMap hardwareMap) {
        super.init(hardwareMap);

        collector = hardwareMap.get(DcMotorEx.class, RobotConfig.INTAKE_MOTOR);
        collector.setDirection(DcMotorEx.Direction.FORWARD);
        collector.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }


    public void loop() {

    }

    /**
     * 控制电机旋转速度
     *
     * */
    public void intakeManual() {
        if(isRunning){
            stop();
        }else {
            intake(SPEED_DEGREE);
        }
        isRunning = !isRunning;

    }

    /**
     * 控制电机旋转速度
     *
     * */
    public void outManual() {
        if(isRunning){
            stop();
        }else {
            intake(-SPEED_DEGREE);
        }
        isRunning = !isRunning;
    }

    /**
     * 控制电机旋转速度
     *
     * */
    public void intake(double speed) {
        collector.setVelocity(speed, AngleUnit.DEGREES);
    }

    /**
     * 启动，定时自动停止
     *
     * */
    public void intake(double speed, double timeInSecs) {
        intake(speed);
        timer.schedule(new TaskStop(), (long) (timeInSecs*1000));
    }

    //    public void initMotor("")
    class TaskStop extends TimerTask {
        @Override
        public void run() {
            stop();
        }
    }

    public void spit(double speed) {
        collector.setVelocity(-speed);
    }

    @Override
    public void stop() {
        collector.setVelocity(0);
    }

    @Override
    public void autoLoop(double time){
        super.autoLoop(time);
        switch(tasks.getName()){
            case TASK_INTAKE_IN:
                intake(SPEED_DEGREE);
                break;
            case TASK_INTAKE_OUT:
                spit(-SPEED_DEGREE);
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
