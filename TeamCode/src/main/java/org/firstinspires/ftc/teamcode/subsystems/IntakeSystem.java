package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.RobotConfig;

import java.util.Timer;
import java.util.TimerTask;

public class IntakeSystem extends DeviceBase {

    public DcMotorEx collector = null;

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

    public void intake() {
        if(isRunning){
            stop();
        }else {
            intake(2 * 360);
        }
        isRunning = !isRunning;

    }

    public void out() {
        if(isRunning){
            stop();
        }else {
            intake(-2*360);
        }
        isRunning = !isRunning;
    }

    public void intake(double speed) {
        collector.setVelocity(speed, AngleUnit.DEGREES);
    }

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
                intake(2.0 * 360);
                break;
            case TASK_INTAKE_OUT:
                spit(-2.0 * 360);
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
