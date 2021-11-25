package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Timer;

public class Arm extends DeviceBase {

    public Servo arm = null;
    public Servo claw = null;
    public DcMotorEx arm1 = null;

    Timer timer = new Timer();
    double ARM_DOWN = 0.25;
    double ARM_UP = 0.9;
    double CLAW_OPEN = 0.35;
    double CLAW_CLOSE = 0.85;
    public boolean isUp = false;
    public boolean isOpen = false;

    public void init(HardwareMap hardwareMap) {
        arm = hardwareMap.get(Servo.class, "arm");
        arm.setPosition(ARM_UP);
        claw = hardwareMap.get(Servo.class, "claw");
        claw.setPosition(CLAW_CLOSE);
    }

    public void rotate(double pos){
        arm.setPosition(pos);
    }

    public void up(){
        arm.setPosition(ARM_UP);
        isUp = true;
    }
    public void down(){
        arm.setPosition(ARM_DOWN);
        isUp = false;
    }

    public void open(){
        claw.setPosition(CLAW_OPEN);
        isOpen = true;
    }

    public void close(){
        claw.setPosition(CLAW_CLOSE);
        isOpen = false;
    }

    public void loop() {

    }

    public void stop() {
        arm.setPosition(0.5);
    }

    @Override
    public void autoLoop(double time){
        super.autoLoop(time);
        switch(tasks.getName()){
            case "TaskOpen":
                open();
                break;
            case "TaskClose":
                close();
                break;
            case "TaskDown":
                down();
                break;
            case "TaskUp":
                up();
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
