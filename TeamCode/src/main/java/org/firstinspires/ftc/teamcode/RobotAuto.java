package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;
import org.firstinspires.ftc.teamcode.subsystems.CarouselSystem;
import org.firstinspires.ftc.teamcode.subsystems.DeviceBase;
import org.firstinspires.ftc.teamcode.subsystems.HeadSystem;
import org.firstinspires.ftc.teamcode.subsystems.IMU;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSystem;
import org.firstinspires.ftc.teamcode.subsystems.LaunchSystem;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDriveWrapper;
import org.firstinspires.ftc.teamcode.subsystems.Navigation;
import org.firstinspires.ftc.teamcode.subsystems.Ranger;
import org.firstinspires.ftc.teamcode.subsystems.TensorFlowObjectDetection;

import java.util.ArrayList;

public class RobotAuto {

    /*
     * control hub
     * M0       lr 左后
     * M1       lf 左前。中编码器
     * M2       rf 右前。左编码器
     * M3       rr 右后。右编码器
     * 舵机1      sm  中编码器
     * 舵机3      sl  左编码器
     * 舵机5      sr  右编码器
     * 数字0.1   arm_lim_team  队伍标记限位开关
     * 数字6.7   arm_lim_mouse 盒子限位开关
     *
     * 拓展盒
     * M0       carousel 转盘电机
     * M1       head    翻转盒子，带编码器
     * M2       arm     队伍标记手臂，带编码器
     * M3       intake  吸取电机
     * 舵机0      mouth    盒子挡板
     * 舵机2      arm_hold    队伍标记架子
     * 舵机4      arm_angle   队伍标记角度
     * */

    public MecanumDriveWrapper drive;
    public HeadSystem head;
    public CarouselSystem carousel;
    public IntakeSystem intake;
    public ArmSystem arm;
    public TensorFlowObjectDetection detector;
    public IMU imu;

    public int detectResult = 0;

    ArrayList<DeviceBase> devices = new ArrayList<>();

    public RobotAuto() {

        drive = new MecanumDriveWrapper();
        head = new HeadSystem();
        carousel = new CarouselSystem();
        intake = new IntakeSystem();
        arm = new ArmSystem();
        detector = new TensorFlowObjectDetection();
        imu = new IMU();
        devices.add(drive);
        devices.add(head);
        devices.add(carousel);
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
}
