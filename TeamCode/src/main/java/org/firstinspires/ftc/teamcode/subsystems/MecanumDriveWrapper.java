package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;

import java.util.ArrayList;

public class MecanumDriveWrapper extends DeviceBase {
    public SampleMecanumDrive drive = null;
    public Pose2d pos = new Pose2d();
    public Trajectory currTrajectory = null;

    public Servo esLeft = null;
    public Servo esMid = null;
    public Servo esRight = null;
    public ArrayList<Servo> servos = new ArrayList<>();

    double[] posUp ={0.0, 0.0, 0.0};//left, mid, right
    double[] posGround ={0.0, 0.0, 0.0};//left, mid, right

    public double estimateX = 0.0;
    public double estimateY = 0.0;
    public double estimateHeading = 0.0;


    public double driftModifier = 1;

    @Override
    public void init(HardwareMap hardwareMap){
        super.init(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setLocalizer(new StandardTrackingWheelLocalizer(hardwareMap));

        esLeft = hardwareMap.get(Servo.class, RobotConfig.DRIVE_ENCODER_SERVO_LEFT);
        esMid = hardwareMap.get(Servo.class, RobotConfig.DRIVE_ENCODER_SERVO_MID);
        esRight = hardwareMap.get(Servo.class, RobotConfig.DRIVE_ENCODER_SERVO_RIGHT);
        servos.add(esLeft);
        servos.add(esMid);
        servos.add(esRight);
    }

    @Override
    public void stop() {
        drive(0,0,0);
    }

    public void sensorUp(){
        sensorTo(posUp);
    }
    public void sensorGround(){
        sensorTo(posGround);
    }
    public void sensorTo(double[] pos){
        for(int i=0;i<3;i++){
            servos.get(i).setPosition(pos[i]);
        }
    }

    public void drive(double y, double x,double heading){
        drive.setDrivePower(new Pose2d(x,y,heading));
    }

    public void turnAuto(double angle){
//        drive.turn(Math.toRadians(angle));
        currTrajectory = drive.trajectoryBuilder(pos)
                .lineToLinearHeading(new Pose2d(pos.getX(), pos.getY(), Math.toRadians(angle)))
                .build();
        pos = currTrajectory.end();
        drive.followTrajectoryAsync(currTrajectory);
    }

    public void driftAuto(double distance){
        if (distance > 0) {
            currTrajectory = drive.trajectoryBuilder(pos)
                    .strafeRight(driftModifier * distance)
                    .build();
            pos = currTrajectory.end();
            drive.followTrajectoryAsync(currTrajectory);
        } else {
            currTrajectory = drive.trajectoryBuilder(pos)
                    .strafeLeft(driftModifier * -distance)
                    .build();
            pos = currTrajectory.end();
            drive.followTrajectoryAsync(currTrajectory);
        }

    }

    public void goAuto(double distance){
        if(distance>0) {
            currTrajectory = drive.trajectoryBuilder(pos)
                    .forward(distance)
                    .build();
            pos = currTrajectory.end();
            drive.followTrajectoryAsync(currTrajectory);
        }else{
            currTrajectory = drive.trajectoryBuilder(pos)
                    .back(distance)
                    .build();
            pos = currTrajectory.end();
            drive.followTrajectoryAsync(currTrajectory);
        }
    }

    public void splineTo(double x,double y){
        currTrajectory = drive.trajectoryBuilder(pos)
                .splineTo(new Vector2d(x, y), Math.toRadians(0))
                .build();
        pos = currTrajectory.end();
        drive.followTrajectoryAsync(currTrajectory);

    }

    public void splineToConstantHeading(double x,double y){
        currTrajectory = drive.trajectoryBuilder(pos)
                .splineToConstantHeading(new Vector2d(x, y), Math.toRadians(0))
                .build();
        pos = currTrajectory.end();
        drive.followTrajectoryAsync(currTrajectory);

    }

    public void splineToLinearHeading(double x,double y, double heading){
        currTrajectory = drive.trajectoryBuilder(pos)
                .splineToLinearHeading(new Pose2d(x, y, Math.toRadians(heading)), Math.toRadians(0))
                .build();
        pos = currTrajectory.end();
        drive.followTrajectoryAsync(currTrajectory);

    }

    public void splineToSplineHeading(double x,double y, double heading){
        currTrajectory = drive.trajectoryBuilder(pos)
                .splineToSplineHeading(new Pose2d(x, y, Math.toRadians(heading)), Math.toRadians(0))
                .build();
        pos = currTrajectory.end();
        drive.followTrajectoryAsync(currTrajectory);

    }

    public void lineTo(double x, double y){
        currTrajectory = drive.trajectoryBuilder(pos)
                .lineTo(new Vector2d(x, y))
                .build();
        pos = currTrajectory.end();
        drive.followTrajectoryAsync(currTrajectory);
    }

    public void lineToConstantHeading(double x, double y){
        currTrajectory = drive.trajectoryBuilder(pos)
                .lineToConstantHeading(new Vector2d(x, y))
                .build();
        pos = currTrajectory.end();
        drive.followTrajectoryAsync(currTrajectory);
    }

    public void lineToLinearHeading(double x, double y, double heading){
        currTrajectory = drive.trajectoryBuilder(pos)
                .lineToLinearHeading(new Pose2d(x, y, Math.toRadians(heading)))
                .build();
        pos = currTrajectory.end();
        drive.followTrajectoryAsync(currTrajectory);
    }

    public void lineToSplineHeading(double x, double y, double heading){
        currTrajectory = drive.trajectoryBuilder(pos)
                .lineToSplineHeading(new Pose2d(x, y, Math.toRadians(heading)))
                .build();
        pos = currTrajectory.end();
        drive.followTrajectoryAsync(currTrajectory);
    }

    public void resetPos(double x, double y, double heading){
        pos = new Pose2d(x,y,heading);
    }

    public void trajectoryWait(double secs){

    }

    @Override
    public void autoLoop(double time){
        super.autoLoop(time);

        if(drive.isBusy()){
            drive.update();
        }

        switch(tasks.getName()){
            case DRIVE_TASK_LINE_TO:
                lineTo(tasks.getD(0), tasks.getD(1));
                break;
            case DRIVE_TASK_LINE_TO_CONSTANT_HEADING:
                lineToConstantHeading(tasks.getD(0), tasks.getD(1));
                break;
            case DRIVE_TASK_LINE_TO_LINEAR_HEADING:
                lineToLinearHeading(tasks.getD(0), tasks.getD(1), tasks.getD(2));
                break;
            case DRIVE_TASK_LINE_TO_SPLINE_HEADING:
                lineToSplineHeading(tasks.getD(0), tasks.getD(1), tasks.getD(2));
                break;

            case DRIVE_TASK_SPLINE_TO:
                splineTo(tasks.getD(0),tasks.getD(1));
                break;
            case DRIVE_TASK_SPLINE_TO_CONSTANT_HEADING:
                splineToConstantHeading(tasks.getD(0),tasks.getD(1));
                break;
            case DRIVE_TASK_SPLINE_TO_LINEAR_HEADING:
                splineToLinearHeading(tasks.getD(0),tasks.getD(1), tasks.getD(2));
                break;
            case DRIVE_TASK_SPLINE_TO_SPLINE_HEADING:
                splineToSplineHeading(tasks.getD(0),tasks.getD(1), tasks.getD(2));
                break;

            case DRIVE_TASK_GO_TIME:
                drive(tasks.getD(0),0,0);
                break;
            case DRIVE_TASK_STRAFE_TIME:
                drive(0,tasks.getD(0),0);
                break;
            case DRIVE_TASK_TURN_TIME:
                turnAuto(tasks.getD(0));
                break;

            case DRIVE_TASK_GO_DISTANCE:
                goAuto(tasks.getD(0));
                break;
            case DRIVE_TASK_STRAFE_DISTANCE:
                driftAuto(tasks.getD(0));
                break;
            case DRIVE_TASK_TURN_ANGLE:
                turnAuto(tasks.getD(0));
                break;

            case DRIVE_TASK_RESET_POS:
                resetPos(estimateX, estimateY, estimateHeading);
                break;

            case DRIVE_TASK_SENSOR_UP:
                sensorUp();
                break;
            case DRIVE_TASK_SENSOR_GROUND:
                sensorGround();
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
