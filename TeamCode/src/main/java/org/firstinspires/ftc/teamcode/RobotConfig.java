package org.firstinspires.ftc.teamcode;

public class RobotConfig {
    /*
     * control hub
     * M0       lr 左后
     * M1       lf 左前。右编码器
     * M2       rf 右前。中编码器
     * M3       rr 右后。左编码器
     * 舵机0      desm  中编码器
     * 舵机2      desl  左编码器
     * 舵机1      desr  右编码器
     * 数字0.1   alt  队伍标记限位开关
     * 数字6.7   hlm 盒子限位开关
     *
     * 拓展盒
     * M3       cm 转盘电机
     * M2       hm    翻转盒子，带编码器
     * M1       am     队伍标记手臂，带编码器
     * M0       im  吸取电机
     * 舵机5      hsm    盒子挡板
     * 舵机4      asw    队伍标记架子
     * 舵机3      asc   队伍标记角度
     * */
    public static final String DRIVE_LEFT_FRONT = "lf";
    public static final String DRIVE_LEFT_REAR = "lr";
    public static final String DRIVE_RIGHT_FRONT = "rf";
    public static final String DRIVE_RIGHT_REAR = "rr";

    public static final String DRIVE_ENCODER_LEFT = "rf";
    public static final String DRIVE_ENCODER_MID = "lf";
    public static final String DRIVE_ENCODER_RIGHT = "rr";

    public static final String DRIVE_ENCODER_SERVO_LEFT = "desl";
    public static final String DRIVE_ENCODER_SERVO_MID = "desm";
    public static final String DRIVE_ENCODER_SERVO_RIGHT = "desr";

    public static final String ARM_LIMIT_TEAM = "alt";
    public static final String ARM_MOTOR = "am";
    public static final String ARM_SERVO_CLAW = "asc";
    public static final String ARM_SERVO_WRIST = "asw";

    public static final String HEAD_LIMIT_MOUSE = "hlm";
    public static final String HEAD_MOTOR = "hm";
    public static final String HEAD_SERVO_MOUSE = "hsm";

    public static final String CAROUSEL_MOTOR = "cm";

    public static final String INTAKE_MOTOR = "im";

    public static final String IMU = "imu";


}
