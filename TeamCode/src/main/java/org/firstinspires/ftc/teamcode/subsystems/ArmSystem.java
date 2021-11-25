package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.RobotConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;

public class ArmSystem extends DeviceBase {

    public Servo wrist = null;
    public Servo claw = null;
    public DcMotorEx arm = null;
    public LimitSensor limit = null;

    Timer timer = new Timer();

    public static final int TICK_PER_REV = 28*40;
    public static final double TICK_PER_DEGREE = TICK_PER_REV/360.0;

    public static final double ARM_GEAR_RATIO = 20.0/20;//robotConfig中设置的比例/电机实际齿轮箱比例
    public static final double ARM_RANGE_DEGREE = 270;//电机两个物理限位之间的最大角度
    public static final double ARM_SPEED_MANUAL = ARM_RANGE_DEGREE * ARM_GEAR_RATIO /2;// 用2秒跑完全程

    public static final double[] posInit = {0.0, 0.0 ,0.0};//motor encoder tick, wrist servo angle, claw servo angle
    public static final double[] posLevelTop = {160*TICK_PER_DEGREE, 0.5 ,0.0};
    public static final double[] posLevelMid = {180*TICK_PER_DEGREE, 0.75,0.0};
    public static final double[] posLevelBottom = {200*TICK_PER_DEGREE, 1.0 ,0.0};
    public static final double[] posIntake = {270*TICK_PER_DEGREE, 0.0 ,0.0};

    public static final int LEVEL_INIT = 0;
    public static final int LEVEL_BOTTOM = 3;
    public static final int LEVEL_MID = 2;
    public static final int LEVEL_TOP = 1;
    public static final int LEVEL_INTAKE = 4;
    int currPos = 0; //0:init 1:levelTop 2:levelMid  3:levelBottom  4:intake
    List<double[] > allPos = Arrays.asList(posInit,posLevelTop,posLevelMid,posLevelBottom,posIntake);

    double ARM_DOWN = 0.25;
    double ARM_UP = 0.9;
    double CLAW_OPEN = 0.35;
    double CLAW_CLOSE = 0.85;

    @Override
    public void init(HardwareMap hardwareMap) {
        super.init(hardwareMap);
        arm = hardwareMap.get(DcMotorEx.class, RobotConfig.ARM_MOTOR);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        wrist = hardwareMap.get(Servo.class, RobotConfig.ARM_SERVO_WRIST);
        wrist.scaleRange(ARM_DOWN, ARM_UP);

        claw = hardwareMap.get(Servo.class, RobotConfig.ARM_SERVO_CLAW);

        limit = new LimitSensor(hardwareMap, RobotConfig.ARM_LIMIT_TEAM);

        actionInit();
    }


    public void actionInit(){
        currPos = LEVEL_INIT;
        actionTo(LEVEL_INIT);
    }

    public void actionLevel1(){
        currPos = LEVEL_BOTTOM;
        actionTo(LEVEL_BOTTOM);
    }

    public void actionLevel2(){
        currPos = LEVEL_MID;
        actionTo(LEVEL_MID);
    }

    public void actionLevel3(){
        currPos = LEVEL_TOP;
        actionTo(LEVEL_TOP);
    }

    public void actionIntake(){
        currPos = LEVEL_INTAKE;
        actionTo(LEVEL_INTAKE);
    }

    public void actionTo(int level){
        if(level<0) level = 0;
        if(level>=allPos.size())  level = allPos.size()-1;
        action(allPos.get(level));
    }


    public void action(double[] pos){
        arm.setTargetPosition((int) pos[0]);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        wrist.setPosition(pos[1]);

        claw.setPosition(pos[2]);
    }

    public void rotate(double pos){
        wrist.setPosition(pos);
    }

    public void wristUpManual(){
        wrist.setPosition(wrist.getPosition()+0.01);
    }

    public void wristDownManual(){
        wrist.setPosition(wrist.getPosition()-0.01);
    }

    public void clawOpenManual(){
        claw.setPosition(claw.getPosition()+0.01);
    }

    public void clawCloseManual(){
        claw.setPosition(claw.getPosition()-0.01);
    }


    // 手动抬头
    public void upManual(){
        arm.setVelocity(ARM_SPEED_MANUAL, AngleUnit.DEGREES);
    }

    // 手动降低
    public void downManual(){
        arm.setVelocity(ARM_SPEED_MANUAL, AngleUnit.DEGREES);
    }

    // 手动停止
    public void stopManual(){
        arm.setVelocity(0);
    }


    public void up(){
        actionTo(currPos+1);
    }

    public void down(){
        actionTo(currPos-1);
    }

    public void open(){
        claw.setPosition(CLAW_OPEN);
    }

    public void close(){
        claw.setPosition(CLAW_CLOSE);
    }

    public void loop() {

    }

    @Override
    public void stop() {

    }



    @Override
    public void autoLoop(double time){
        super.autoLoop(time);
        switch(tasks.getName()){
            case TASK_ARM_POS_TO:
                actionTo(tasks.getI(0));
                break;
            case TASK_ARM_POS_INIT:
                actionInit();
                break;
            case TASK_ARM_POS_INTAKE:
                actionIntake();
                break;
            case TASK_ARM_POS_LEVEL1:
                actionLevel1();
                break;
            case TASK_ARM_POS_LEVEL2:
                actionLevel2();
                break;
            case TASK_ARM_POS_LEVEL3:
                actionLevel3();
                break;
            case TASK_ARM_OPEN:
                open();
                break;
            case TASK_ARM_CLOSE:
                close();
                break;
            case TASK_ARM_DOWN:
                down();
                break;
            case TASK_ARM_UP:
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
