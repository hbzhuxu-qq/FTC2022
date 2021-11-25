package org.firstinspires.ftc.teamcode;

/**
 * 本文件中保存了所有自动程序的路线方案
 *
 * 设计新的路线步骤：
 *      1. 拷贝sample方法，保存到一个新的方法中，保证是public static的
 *      2. 逐一修改各个组件的任务序列
 *      3. 路线设计完工后，请参照Auto_Sample程序内的说明创建新的自动程序，即可使用新建的方案
 *
 * 各任务序列的功能参照sample中注释
 *
 * */
public class AutoPlan {

    /***
     * 每个设备的可用方法的展示
     * */
    public static void sample(RobotAuto robot) {

        // 探测鸭子或者标志物
        robot.detector
                .TaskStop()
        ;

        // 底盘自动化
        robot.drive
                .TaskWait(0)
                //直线向目标点坐标行驶，头部角度跟随路径
                .DriveTaskLineTo(2, 20,20)
                //直线向目标点坐标行驶，保持头部角度不变
                .DriveTaskLineToConstantHeading(2, 20,20)
                //直线行驶到目标坐标点，头部匀速转到目标角度
                .DriveTaskLineToLinearHeading(2,30,30,-90)
                //直线行驶到目标坐标点，头部曲线速度转到目标角度
                .DriveTaskLineToSplineHeading(2,10,10, 90)

                //自定义曲线行驶到目标坐标点，头部角度跟随路径
                .DriveTaskSplineTo(2,1,1)
                //自定义曲线平移到目标坐标点，保持头部角度不变
                .DriveTaskSplineToConstantHeading(2,1,1)
                //自定义曲线平移到目标坐标点，头部匀速转到目标角度
                .DriveTaskSplineToLinearHeading(5, 80,0, 180)
                //自定义曲线平移到目标坐标点，头部曲线速度转到目标角度
                .DriveTaskSplineToSplineHeading(5, 80,0, -180)
                //抬起底盘编码器伺服
                .DriveTaskSensorUp(0)
                //放下底盘编码器伺服
                .DriveTaskSensorGround(0)
                //倒退5英寸
                .DriveTaskForwardDist(0,-5)
                //向右平移5英寸
                .DriveTaskStrafeDist(0,-5)

                .TaskStop()
        ;
        // 头
        robot.head
                .HeadTaskPosInit(2)     //去初始化位置
                .HeadTaskPosIntake(2)   //去接收货物的位置
                .HeadTaskPosLevel1(2)   //去放1层位置
                .HeadTaskPosLevel2(2)   //去放2层位置
                .HeadTaskPosLevel3(2)   //去放3层位置
                .HeadTaskPosTo(0,2) //去指定位置：init:-1,intake:0,level1:1,level2:2,level3:3
                .HeadTaskUp(2)          //向上一层位置
                .HeadTaskDown(2)        //向下一层位置
                .HeadTaskOpen(1)        //张开嘴巴（放掉那个货物）
                .HeadTaskClose(1)       //关闭嘴巴（等待接收货物）
                .TaskStop()
        ;
        // 转盘
        robot.carousel
                .TaskWait(10)
                .CarouselTaskBezierAcc(2)      //贝塞尔曲线动态调整旋转速度
                .CarouselTaskStillAcc(2)       //匀加速调整旋转速度
                .TaskStop()
        ;

        // 吸取
        robot.intake
                .InTakeIn(10)       //吸入
                .TaskStopAndWait(5)
                .InTakeOut(10)      //吐出
                .TaskStop()
        ;
        // 机械臂（2022自动任务不需要动
        robot.arm
                .ArmTaskPosInit(2)     //去初始化位置
                .ArmTaskPosIntake(2)   //去地面抓取标志
                .ArmTaskPosLevel1(2)   //去放第1层标志位置
                .ArmTaskPosLevel2(2)   //去放第2层标志位置
                .ArmTaskPosTo(0,2) //去指定位置：init:-1,intake:0,level1:1,level2:2,level3:3
                .ArmTaskUp(2)          //向上一层位置
                .ArmTaskDown(2)        //向下一层位置
                .ArmTaskOpen(1)        //打开爪子
                .ArmTaskClose(1)       //关闭爪子
                .TaskWait(25)
                .TaskStop()
        ;
    }


    public static void testDrive(RobotAuto robot){

        robot.drive
                .DriveTaskForwardDist(2,50)
                .DriveTaskForwardDist(2,-30)
                .TaskStopAndWait(3)
                .DriveTaskStrafeDist(2,30)
                .DriveTaskStrafeDist(2,-30)
                .TaskStopAndWait(3)
                .TaskStop()
        ;


    }

    // run Carousel, freight in hub, park storage unit
    public static void blueBottomSU(RobotAuto robot){

        // 探测鸭子或者标志物
        robot.detector
                .TaskStop()
        ;

        // 底盘自动化
        robot.drive
                .TaskWait(0)
                .DriveTaskSplineToSplineHeading(2, 20,10, -160)
                .TaskWait(4)
                .DriveTaskSplineToSplineHeading(3, -30,35, 135)
                .TaskWait(5)
                .DriveTaskSplineToSplineHeading(2, 22,22, 90)

                .TaskStop()
        ;
        // 头
        robot.head
                .TaskWait(7)
                .HeadTaskPosToTarget(3) //去指定位置：init:-1,intake:0,level1:1,level2:2,level3:3
                .HeadTaskOpen(2)          //打开，放货物
                .HeadTaskClose(1)       //关闭嘴巴（等待接收货物）
                .HeadTaskPosIntake(2)   //回到intake状态
                .TaskStop()
        ;
        // 转盘
        robot.carousel
                .TaskWait(5)
                .CarouselTaskBezierAcc(2)      //贝塞尔曲线动态调整旋转速度
                .TaskStop()
        ;

        // 吸取
        robot.intake
                .TaskStop()
        ;
        // 机械臂（2022自动任务不需要动
        robot.arm
                .TaskStop()
        ;


    }

    // run Carousel, freight in hub, park warehouse
    public static void blueTopW(RobotAuto robot){

        // 探测鸭子或者标志物
        robot.detector
                .TaskStop()
        ;

        // 底盘自动化
        robot.drive
                .TaskWait(0)
                .DriveTaskSplineToSplineHeading(3, 25,30, -45)
                .TaskWait(4)//wait for freight into hub
                .DriveTaskSplineToSplineHeading(3, 0,25, 90)//to barrier
                .DriveTaskSensorUp(0.2)
                .DriveTaskSplineToSplineHeading(3, -50,25, 90)//cross barrier
                .DriveTaskSensorGround(0.5)
                .TaskStop()
        ;
        // 头
        robot.head
                .HeadTaskPosToTarget(3) //去指定位置：init:-1,intake:0,level1:1,level2:2,level3:3
                .HeadTaskOpen(2)          //打开，放货物
                .HeadTaskClose(1)       //关闭嘴巴（等待接收货物）
                .HeadTaskPosIntake(2)   //回到intake状态
                .TaskStop()
        ;
        // 转盘
        robot.carousel
                .TaskStop()
        ;

        // 吸取
        robot.intake
                .TaskStop()
        ;
        // 机械臂（2022自动任务不需要动
        robot.arm
                .TaskStop()
        ;

    }


    // run Carousel, freight in hub, park storage unit
    public static void redBottomSU(RobotAuto robot){

        // 探测鸭子或者标志物
        robot.detector
                .TaskStop()
        ;

        // 底盘自动化
        robot.drive
                .TaskWait(0)
                .DriveTaskSplineToSplineHeading(2, -20,10, 45)
                .TaskWait(4)
                .DriveTaskSplineToSplineHeading(3, 30,35, -135)
                .TaskWait(5)
                .DriveTaskSplineToSplineHeading(2, -22,22, -90)

                .TaskStop()
        ;
        // 头
        robot.head
                .TaskWait(7)
                .HeadTaskPosToTarget(3) //去指定位置：init:-1,intake:0,level1:1,level2:2,level3:3
                .HeadTaskOpen(2)          //打开，放货物
                .HeadTaskClose(1)       //关闭嘴巴（等待接收货物）
                .HeadTaskPosIntake(2)   //回到intake状态
                .TaskStop()
        ;
        // 转盘
        robot.carousel
                .TaskWait(5)
                .CarouselTaskBezierAcc(2)      //贝塞尔曲线动态调整旋转速度
                .TaskStop()
        ;

        // 吸取
        robot.intake
                .TaskStop()
        ;
        // 机械臂（2022自动任务不需要动
        robot.arm
                .TaskStop()
        ;
    }


}
