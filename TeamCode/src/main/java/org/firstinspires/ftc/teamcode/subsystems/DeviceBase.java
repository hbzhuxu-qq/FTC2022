package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Task;
import org.firstinspires.ftc.teamcode.TaskMan;

import java.util.Timer;
import java.util.TimerTask;

public class DeviceBase {
    Timer scheduleTimer = new Timer();
    public HardwareMap hardwareMap = null;

    public TaskMan tasks = new TaskMan();

    public void init(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
    }

    public void start(){

    }

    public void stop(){

    }

    public void autoLoop(double time){
        tasks.loop(time);
    }

    public void loop(double time){

    }

    class TaskStop extends TimerTask{
        @Override
        public void run() {
            stop();
        }
    }

    /* *******************
     *  Tasks for drive
     * ********************/

    public static final String DRIVE_TASK_LINE_TO = "DRIVE_TASK_LINE_TO";
    public static final String DRIVE_TASK_LINE_TO_CONSTANT_HEADING = "DRIVE_TASK_LINE_TO_CONSTANT_HEADING";
    public static final String DRIVE_TASK_LINE_TO_LINEAR_HEADING = "DRIVE_TASK_LINE_TO_LINEAR_HEADING";
    public static final String DRIVE_TASK_LINE_TO_SPLINE_HEADING = "DRIVE_TASK_LINE_TO_SPLINE_HEADING";
    public static final String DRIVE_TASK_SPLINE_TO = "DRIVE_TASK_SPLINE_TO";
    public static final String DRIVE_TASK_SPLINE_TO_CONSTANT_HEADING = "DRIVE_TASK_SPLINE_TO_CONSTANT_HEADING";
    public static final String DRIVE_TASK_SPLINE_TO_LINEAR_HEADING = "DRIVE_TASK_SPLINE_TO_LINEAR_HEADING";
    public static final String DRIVE_TASK_SPLINE_TO_SPLINE_HEADING = "DRIVE_TASK_SPLINE_TO_SPLINE_HEADING";

    public static final String DRIVE_TASK_GO_DISTANCE = "DRIVE_TASK_GO_DISTANCE";
    public static final String DRIVE_TASK_STRAFE_DISTANCE = "DRIVE_TASK_STRAFE_DISTANCE";
    public static final String DRIVE_TASK_TURN_ANGLE = "DRIVE_TASK_TURN_ANGLE";
    public static final String DRIVE_TASK_GO_TIME = "DRIVE_TASK_GO_TIME";
    public static final String DRIVE_TASK_STRAFE_TIME = "DRIVE_TASK_STRAFE_TIME";
    public static final String DRIVE_TASK_TURN_TIME = "DRIVE_TASK_TURN_TIME";

    public static final String DRIVE_TASK_RESET_POS = "DRIVE_TASK_RESET_POS";

    public static final String DRIVE_TASK_SENSOR_UP = "DRIVE_TASK_SENSOR_UP";
    public static final String DRIVE_TASK_SENSOR_GROUND = "DRIVE_TASK_SENSOR_DOWN";


    public DeviceBase DriveTaskGoTime(double seconds, double speed){
        Task a = new Task(DRIVE_TASK_GO_TIME,seconds,speed);
        tasks.addTask(a);
        return this;
    }

    public DeviceBase DriveTaskStrafeTime(double seconds, double speed){
        Task a = new Task(DRIVE_TASK_STRAFE_TIME,seconds,speed);
        tasks.addTask(a);
        return this;
    }

    public DeviceBase DriveTaskTurnTime(double seconds, double angle){
        Task a = new Task(DRIVE_TASK_TURN_TIME,seconds,angle);
        tasks.addTask(a);
        return this;
    }

    public DeviceBase DriveTaskTurnAngle(double seconds, double angle){
        Task a = new Task(DRIVE_TASK_TURN_ANGLE,seconds,angle);
        tasks.addTask(a);
        return this;
    }


    public DeviceBase DriveTaskForwardDist(double seconds, double distY){
        Task a = new Task(DRIVE_TASK_GO_DISTANCE,0,distY);
        tasks.addTask(a);
        TaskWait(seconds);
        return this;
    }

    public DeviceBase DriveTaskResetPos(double seconds){
        Task a = new Task(DRIVE_TASK_RESET_POS,0);
        tasks.addTask(a);
        return this;
    }

    // line---------
    public DeviceBase DriveTaskLineTo(double seconds, double x,double y){
        Task a = new Task(DRIVE_TASK_LINE_TO,0,x,y);
        tasks.addTask(a);
        TaskWait(seconds);
        return this;
    }
    public DeviceBase DriveTaskLineToConstantHeading(double seconds, double x,double y){
        Task a = new Task(DRIVE_TASK_LINE_TO_CONSTANT_HEADING,0,x,y);
        tasks.addTask(a);
        TaskWait(seconds);
        return this;
    }

    public DeviceBase DriveTaskLineToLinearHeading(double seconds, double x,double y, double heading){
        Task a = new Task(DRIVE_TASK_LINE_TO_LINEAR_HEADING,0,x,y, heading);
        tasks.addTask(a);
        TaskWait(seconds);
        return this;
    }


    public DeviceBase DriveTaskLineToSplineHeading(double seconds, double x,double y, double heading){
        Task a = new Task(DRIVE_TASK_LINE_TO_SPLINE_HEADING,0,x,y, heading);
        tasks.addTask(a);
        TaskWait(seconds);
        return this;
    }
    //spline--------------------
    public DeviceBase DriveTaskSplineTo(double seconds, double x,double y){
        Task a = new Task(DRIVE_TASK_SPLINE_TO,0,x,y);
        tasks.addTask(a);
        TaskWait(seconds);
        return this;
    }

    public DeviceBase DriveTaskSplineToConstantHeading(double seconds, double x,double y){
        Task a = new Task(DRIVE_TASK_SPLINE_TO_CONSTANT_HEADING,0,x,y);
        tasks.addTask(a);
        TaskWait(seconds);
        return this;
    }

    public DeviceBase DriveTaskSplineToLinearHeading(double seconds, double x,double y, double heading){
        Task a = new Task(DRIVE_TASK_SPLINE_TO_LINEAR_HEADING,0,x,y, heading);
        tasks.addTask(a);
        TaskWait(seconds);
        return this;
    }

    public DeviceBase DriveTaskSplineToSplineHeading(double seconds, double x,double y, double heading){
        Task a = new Task(DRIVE_TASK_SPLINE_TO_SPLINE_HEADING,0,x,y, heading);
        tasks.addTask(a);
        TaskWait(seconds);
        return this;
    }

    public DeviceBase DriveTaskStrafeDist(double seconds, double dist){
        Task a = new Task(DRIVE_TASK_STRAFE_DISTANCE,0.02,dist);
        tasks.addTask(a);
        TaskWait(seconds);
        return this;
    }

    public DeviceBase DriveTaskSensorUp(double seconds){
        Task a = new Task(DRIVE_TASK_SENSOR_UP,0.02);
        tasks.addTask(a);
        return this;
    }

    public DeviceBase DriveTaskSensorGround(double seconds){
        Task a = new Task(DRIVE_TASK_SENSOR_GROUND,0.02);
        tasks.addTask(a);
        return this;
    }


    /* *******************
     *  Tasks for CAROUSEL
     * ********************/
    public static final String TASK_CAROUSEL_BEZIER_ACC = "TASK_CAROUSEL_BEZIER_ACC";
    public static final String TASK_CAROUSEL_STILL_ACC = "TASK_CAROUSEL_STILL_ACC";
    public static final String TASK_CAROUSEL_STILL = "TASK_CAROUSEL_STILL";

    public DeviceBase CarouselTaskBezierAcc(double seconds){
        Task a = new Task(TASK_CAROUSEL_BEZIER_ACC,seconds);
        tasks.addTask(a);
        return this;
    }

    public DeviceBase CarouselTaskStillAcc(double seconds){
        Task a = new Task(TASK_CAROUSEL_STILL_ACC,seconds);
        tasks.addTask(a);
        return this;
    }

    public DeviceBase CarouselTaskStill(double seconds, double speed){
        Task a = new Task(TASK_CAROUSEL_STILL,seconds,speed);
        tasks.addTask(a);
        return this;
    }


    /* *******************
     *  Tasks for Head
     * ********************/
    public static final String TASK_HEAD_POS_TO = "TASK_HEAD_POS_TO";
    public static final String TASK_HEAD_POS_TO_TARGET = "TASK_HEAD_POS_TO_TARGET";
    public static final String TASK_HEAD_POS_INIT = "TASK_HEAD_POS_INIT";
    public static final String TASK_HEAD_POS_INTAKE = "TASK_HEAD_POS_INTAKE";
    public static final String TASK_HEAD_POS_LEVEL1 = "TASK_HEAD_POS_LEVEL1";
    public static final String TASK_HEAD_POS_LEVEL2 = "TASK_HEAD_POS_LEVEL2";
    public static final String TASK_HEAD_POS_LEVEL3 = "TASK_HEAD_POS_LEVEL3";
    public static final String TASK_HEAD_OPEN = "TASK_HEAD_OPEN";
    public static final String TASK_HEAD_CLOSE = "TASK_HEAD_CLOSE";
    public static final String TASK_HEAD_UP = "TASK_HEAD_UP";
    public static final String TASK_HEAD_DOWN = "TASK_HEAD_DOWN";


    public DeviceBase HeadTaskPosTo(int pos,double seconds){
        Task a = new Task(TASK_HEAD_POS_TO,seconds ,pos);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase HeadTaskPosToTarget(double seconds){
        Task a = new Task(TASK_HEAD_POS_TO_TARGET,seconds );
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase HeadTaskPosInit(double seconds){
        Task a = new Task(TASK_HEAD_POS_INIT ,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase HeadTaskPosIntake(double seconds){
        Task a = new Task(TASK_HEAD_POS_INTAKE ,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase HeadTaskPosLevel1(double seconds){
        Task a = new Task(TASK_HEAD_POS_LEVEL1 ,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase HeadTaskPosLevel2(double seconds){
        Task a = new Task(TASK_HEAD_POS_LEVEL2 ,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase HeadTaskPosLevel3(double seconds){
        Task a = new Task(TASK_HEAD_POS_LEVEL3 ,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase HeadTaskOpen(double seconds){
        Task a = new Task(TASK_HEAD_OPEN,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase HeadTaskClose(double seconds){
        Task a = new Task(TASK_HEAD_CLOSE,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase HeadTaskUp(double seconds){
        Task a = new Task(TASK_HEAD_UP,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase HeadTaskDown(double seconds){
        Task a = new Task(TASK_HEAD_DOWN,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }


    /* *******************
     *  Tasks for arm
     * ********************/
    public static final String TASK_ARM_POS_TO = "TASK_ARM_POS_TO";
    public static final String TASK_ARM_POS_INIT = "TASK_ARM_POS_INIT";
    public static final String TASK_ARM_POS_INTAKE = "TASK_ARM_POS_INTAKE";
    public static final String TASK_ARM_POS_LEVEL1 = "TASK_ARM_POS_LEVEL1";
    public static final String TASK_ARM_POS_LEVEL2 = "TASK_ARM_POS_LEVEL2";
    public static final String TASK_ARM_POS_LEVEL3 = "TASK_ARM_POS_LEVEL3";
    public static final String TASK_ARM_OPEN = "TASK_ARM_OPEN";
    public static final String TASK_ARM_CLOSE = "TASK_ARM_CLOSE";
    public static final String TASK_ARM_UP = "TASK_ARM_UP";
    public static final String TASK_ARM_DOWN = "TASK_ARM_DOWN";

    public DeviceBase ArmTaskPosTo(int pos,double seconds){
        Task a = new Task(TASK_ARM_POS_TO,2 ,pos);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase ArmTaskPosInit(double seconds){
        Task a = new Task(TASK_ARM_POS_INIT ,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase ArmTaskPosIntake(double seconds){
        Task a = new Task(TASK_ARM_POS_INTAKE ,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase ArmTaskPosLevel1(double seconds){
        Task a = new Task(TASK_ARM_POS_LEVEL1 ,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase ArmTaskPosLevel2(double seconds){
        Task a = new Task(TASK_ARM_POS_LEVEL2 ,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase ArmTaskPosLevel3(double seconds){
        Task a = new Task(TASK_ARM_POS_LEVEL3 ,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase ArmTaskOpen(double seconds){
        Task a = new Task(TASK_ARM_OPEN,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase ArmTaskClose(double seconds){
        Task a = new Task(TASK_ARM_CLOSE,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase ArmTaskUp(double seconds){
        Task a = new Task(TASK_ARM_UP,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase ArmTaskDown(double seconds){
        Task a = new Task(TASK_ARM_DOWN,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public DeviceBase ArmTaskArmStop(double seconds){
        Task a = new Task(TASK_STOP,seconds);
        tasks.addTask(a);
        return this;
    }


    /* *******************
     *  Tasks for intake
     * ********************/
    public static final String TASK_INTAKE_IN = "TASK_INTAKE_IN";
    public static final String TASK_INTAKE_OUT = "TASK_INTAKE_OUT";

    public DeviceBase InTakeIn(double seconds){
        Task a = new Task(TASK_INTAKE_IN,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }
    public DeviceBase InTakeOut(double seconds){
        Task a = new Task(TASK_INTAKE_OUT,seconds);
        tasks.addTask(a);
        return this;
    }

    /* *******************
     *  Tasks for ring detect
     * ********************/
    public static final String TASK_DETECT_START = "TASK_DETECT_START";
    public static final String TASK_DETECT_PAUSE = "TASK_DETECT_PAUSE";

    public DeviceBase RingDetectStart(double seconds){
        Task a = new Task(TASK_DETECT_START,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }
    public DeviceBase RingDetectPause(double seconds){
        Task a = new Task(TASK_DETECT_PAUSE,seconds);
        tasks.addTask(a);

        TaskWait(seconds);
        return this;
    }

    public static final String TASK_WAIT = "TASK_WAIT";
    public static final String TASK_STOP_WAIT = "TASK_STOP_WAIT";
    public static final String TASK_STOP = "TASK_STOP";


    /* *******************
     *  Tasks for all
     * ********************/

    public DeviceBase TaskWait(double seconds){
        Task a = new Task(TASK_WAIT,seconds);
        tasks.addTask(a);
        return this;
    }

    public DeviceBase TaskStopAndWait(double seconds){
        Task a = new Task(TASK_STOP_WAIT,seconds);
        tasks.addTask(a);
        return this;
    }

    public DeviceBase TaskStop(){
        Task a = new Task(TASK_STOP,300);
        tasks.addTask(a);
        return this;
    }

}
