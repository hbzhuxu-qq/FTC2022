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

public class HeadSystem extends DeviceBase {

    public Servo mouth = null;
    public DcMotorEx neck = null;
    public static final int TICK_PER_REV = 28*60;
    public static final double TICK_PER_DEGREE = TICK_PER_REV/360.0;
    public static final double NECK_GEAR_RATIO = 60.0/20;//电机实际齿轮箱比例/robotConfig中设置的比例
    public static final double NECK_RANGE_DEGREE = 120;//电机两个物理限位之间的最大角度
    public static final double NECK_SPEED_MANUAL = NECK_RANGE_DEGREE*NECK_GEAR_RATIO/2;// 用2秒跑完全程
    public LimitSensor limit = null;

    Timer timer = new Timer();
    double[] posInit = {0.0, 0.0};
    double[] posIntake = {0.0, 0.0};
    double[] posLevel1 = {TICK_PER_DEGREE*60, 0.0};
    double[] posLevel2 = {TICK_PER_DEGREE*90, 0.0};
    double[] posLevel3 = {TICK_PER_DEGREE*110, 0.0};
    public static final int LEVEL_INIT = 0;
    public static final int LEVEL_INTAKE = 1;
    public static final int LEVEL_1 = 2;
    public static final int LEVEL_2 = 3;
    public static final int LEVEL_3 = 4;

    List<double[] > allPos = Arrays.asList(posInit,posIntake,posLevel1,posLevel2,posLevel3);

    int currPos = 0; //-1:init  0:intake  1:level1 2:level2 3:level3

    public int targetPos = -1;

    double MOUTH_OPEN = 0.35;
    double MOUTH_CLOSE = 0.85;

    @Override
    public void init(HardwareMap hardwareMap) {
        super.init(hardwareMap);

        neck = hardwareMap.get(DcMotorEx.class, RobotConfig.HEAD_MOTOR);
        neck.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        neck.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        neck.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        mouth = hardwareMap.get(Servo.class, RobotConfig.HEAD_SERVO_MOUSE);

        limit = new LimitSensor(hardwareMap, RobotConfig.HEAD_LIMIT_MOUSE);

        actionInit();
    }


    public void actionInit(){
        actionTo(LEVEL_INIT);
    }

    public void actionLevel1(){
        actionTo(LEVEL_1);
    }

    public void actionLevel2(){
        actionTo(LEVEL_2);
    }

    public void actionLevel3(){
        actionTo(LEVEL_3);
    }

    public void actionIntake(){

        actionTo(LEVEL_INTAKE);
    }

    public void actionTo(int level){
        if(level<0) level = 0;
        if(level>allPos.size()-1)  level = allPos.size()-1;
        action(posInit);
        currPos = level;
    }

    public void action(double[] pos){
        neck.setTargetPosition((int) pos[0]);
        neck.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        mouth.setPosition(pos[1]);
    }

    public void up(){
        actionTo(currPos+1);
    }

    public void down(){
        actionTo(currPos-1);
    }

    public void open(){
        mouth.setPosition(MOUTH_OPEN);
    }

    public void close(){
        mouth.setPosition(MOUTH_CLOSE);
    }

    // 手动抬头
    public void upManual(){
        neck.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        neck.setVelocity(NECK_SPEED_MANUAL, AngleUnit.DEGREES);
    }

    // 手动降低
    public void downManual(){
        neck.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        neck.setVelocity(NECK_SPEED_MANUAL, AngleUnit.DEGREES);
    }

    // 手动停止
    public void stopManual(){
        neck.setVelocity(0);
    }



    public void loop() {

    }
    @Override
    public void stop() {
        neck.setVelocity(0);
    }


    @Override
    public void autoLoop(double time){
        super.autoLoop(time);
        switch(tasks.getName()){
            case TASK_HEAD_POS_TO:
                actionTo(tasks.getI(0));
                break;
            case TASK_HEAD_POS_TO_TARGET:
                actionTo(targetPos);
                break;
            case TASK_HEAD_POS_INIT:
                actionInit();
                break;
            case TASK_HEAD_POS_INTAKE:
                actionIntake();
                break;
            case TASK_HEAD_POS_LEVEL1:
                actionLevel1();
                break;
            case TASK_HEAD_POS_LEVEL2:
                actionLevel2();
                break;
            case TASK_HEAD_POS_LEVEL3:
                actionLevel3();
                break;
            case TASK_HEAD_OPEN:
                open();
                break;
            case TASK_HEAD_CLOSE:
                close();
                break;
            case TASK_HEAD_DOWN:
                down();
                break;
            case TASK_HEAD_UP:
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
