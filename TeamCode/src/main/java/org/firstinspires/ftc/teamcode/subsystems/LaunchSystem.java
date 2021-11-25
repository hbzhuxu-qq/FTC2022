package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Timer;
import java.util.TimerTask;

public class LaunchSystem extends DeviceBase {

    public DcMotorEx leftFlywheel = null;
    public DcMotorEx rightFlywheel = null;
    Servo pusher = null;
    Timer timer = new Timer();

    public void init(HardwareMap hardwareMap) {

        leftFlywheel = hardwareMap.get(DcMotorEx.class, "left");
        rightFlywheel = hardwareMap.get(DcMotorEx.class, "right");

        leftFlywheel.setDirection(DcMotorEx.Direction.FORWARD);
        rightFlywheel.setDirection(DcMotorEx.Direction.FORWARD);

        leftFlywheel.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightFlywheel.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        leftFlywheel.setVelocityPIDFCoefficients(6,1,0,0);
        rightFlywheel.setVelocityPIDFCoefficients(6,1,0,0);

        pusher = hardwareMap.get(Servo.class, "pusher");
        pusher.setPosition(0.5);
    }

    public void loop() {

    }

    public void launch(double speed) {
        leftFlywheel.setVelocity(speed);
        rightFlywheel.setVelocity(speed);
    }

    public void push(){
        pusher.setPosition(0.0);
        timer.schedule(new TaskRetract(),200);
    }

    public void retract(){
        pusher.setPosition(0.5);
    }

    class TaskRetract extends TimerTask{

        @Override
        public void run() {
            retract();
        }
    }

    public void stop() {
        leftFlywheel.setVelocity(0);
        rightFlywheel.setVelocity(0);
    }
    @Override
    public void autoLoop(double time) {
        super.autoLoop(time);
        switch (tasks.getName()) {
            case "TaskShoot":
                push();
                break;
            case "TaskFlyWheelStart":
                launch(tasks.getD(0));
                break;
            case "TaskFlyWheelStop":
                stop();
                break;
            case "TaskAngleUp":
                //angleup();
                break;
            case "TaskAngleDown":
                //angledown();
                break;
            case "TaskAngleBack":
                //angleback();
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
