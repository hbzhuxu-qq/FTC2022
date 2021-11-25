package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.DeviceBase;
import org.firstinspires.ftc.teamcode.subsystems.IMU;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSystem;
import org.firstinspires.ftc.teamcode.subsystems.LaunchSystem;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Navigation;
import org.firstinspires.ftc.teamcode.subsystems.Ranger;
import org.firstinspires.ftc.teamcode.subsystems.TensorFlowObjectDetection;

import java.util.ArrayList;

public class Robot {
    /*
    * control hub
    * M0 lr 左后
    * M1 lf 左前。中编码器
    * M2 rf 右前。左编码器
    * M3 rr 右后。右编码器
    * 舵机1  中编码器
    * 舵机3  左编码器
    * 舵机5  右编码器
    * 数字0.1 队伍标记限位开关
    * 数字6.7 盒子限位开关
    *
    * 拓展盒
    * M0 转盘电机
    * M1 翻转盒子，带编码器
    * M2 队伍标记手臂，带编码器
    * M3 吸取电机
    * 舵机0 盒子挡板
    * 舵机2 队伍标记架子
    * 舵机4 队伍标记角度
    * */

    public MecanumDrive drive;
    public LaunchSystem launch;
    public Navigation navigation;
    public IntakeSystem intake;
    public Arm arm;
    public TensorFlowObjectDetection detector;
    public IMU imu;
    public Ranger ranger;

    ArrayList<DeviceBase> devices = new ArrayList<>();

    public Robot() {

        drive = new MecanumDrive();
        launch = new LaunchSystem();
//        navigation = new Navigation();
        intake = new IntakeSystem();
        arm = new Arm();
        detector = new TensorFlowObjectDetection();
        imu = new IMU();
//        ranger = new Ranger();
        devices.add(drive);
        devices.add(launch);
        devices.add(intake);
        devices.add(detector);
        devices.add(arm);

    }

    public void init(HardwareMap hardwareMap) {

        for (DeviceBase d : devices) {
            d.init(hardwareMap);
        }
    }
    public void loop(double time){
        for (DeviceBase d : devices) {
            d.loop(time);
        }
    }
    public void stop(){
        for (DeviceBase d : devices) {
            d.stop();
        }
    }
    public void autoLoop(double time) {
        for (DeviceBase d : devices) {
            d.autoLoop(time);
        }
    }
    public void resetTask() {
        for (DeviceBase d : devices) {
            d.tasks.reset();
        }
    }
}
