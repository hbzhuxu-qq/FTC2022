## FTC2022 RobotController

本程序应用于2022年第一版硬件
内容包括底盘、intake、运货、转盘、机械臂等结构的控制


## RobotConfig

配置机器人各项硬件名称：RobotConfig.java


## 模块组成
## 底盘
底盘控制程序

集成了RoadRunner相关功能控制。
teamcode/subsystems/MecanumDriveWrapper.java
. 对底盘的实际控制由内部的SampleMecanumDrive对象来实现。
. 对底盘3个编码器对应的伺服电机的控制(sensor系列方法)
. 底盘相关的所有自动Task的支持(drive方法/line系列方法/spline系列方法)
. 底盘自动驾驶相关的坐标系可按机器人本身坐标或场地坐标灵活设置(resetPos方法)


### intake
Intake货物收发控制

teamcode/subsystems/IntakeSystem.java
参数：
SPEED_ROUND 按圈/s描述的速度

简单的控制intake电机方向
. 吸入(intake方法)
. 吐出(out方法)
. 吸入，专用于手动控制的，支持开关控制(intakeManual)
. 吐出，专用于手动控制的，支持开关控制(outManual)

### 运货(Head)
货物运送控制
teamcode/subsystems/HeadSystem.java
命名：neck代表控制装运货物容器的电机、mouse代表控制门的伺服

自动程序控制主要使用RunToPosition模式
手动程序控制混合使用RunToPosition模式和RunUsingEncoder模式

前置条件：
需要估算/测试几个主要点位的tick数值

TICK_PER_REV：电机输出端每圈tick（编码器数值）
NECK_GEAR_RATIO：RobotConfig中没有60:1比例的电机设置，需要在此处记录一下实际比例和配置比例的误差；如需要使用setVelocity方法做控制需要使用本数据
NECK_SPEED_MANUAL：手动控制需要使用setVelocity

posInit：比赛时初始化状态的位置，通常等于posIntake;(neck编码器位置数据、mouse伺服位置数据)
posIntake：接受intake货物的位置，需要和限位开关结合使用；目前限位开关程序未完成;(neck编码器位置数据、mouse伺服位置数据)
posLevel1：hub1层高度对应的(neck编码器位置数据、mouse伺服位置数据)
posLevel2：hub2层高度对应的(neck编码器位置数据、mouse伺服位置数据)
posLevel3：hub3层高度对应的(neck编码器位置数据、mouse伺服位置数据)

### 转盘(Carousel)
转盘控制
用于控制鸭子转盘的程序
teamcode/subsystems/CarouselSystem.java

前置条件：
转鸭子的起始速度、最高速度、旋转时间三个参数
参考teamcode/test/CarouselTest系列

提供三种运行状态：
. 匀速旋转(goByDegree方法)
. 匀加速旋转(goStill方法，多线程模式，默认时长、自定义时长)
. 变加速旋转(goBerzer方法，多线程模式，默认时长、自定义时长)

可以考虑运用后两种方法实现手动EndingGame阶段转鸭子完全自动化


### 机械臂(Arm)
抓取放置标志物的机械臂，仅用于手动Capping阶段任务阶段
teamcode/subsystems/ArmSystem.java

命名：
arm代表控制装机械臂的电机
wrist代表安装在机械臂中部的伺服电机
claw代表安装在顶部爪子位置的伺服电机

自动程序控制主要使用RunToPosition模式
手动程序控制混合使用RunToPosition模式和RunUsingEncoder模式

前置条件：
需要估算/测试arm几个主要点位的电机编码器tick数值
需要测试wrist、claw的position数值

TICK_PER_REV：电机输出端每圈tick（编码器数值）
ARM_SPEED_MANUAL：手动控制需要使用setVelocity

posInit：比赛时初始化状态的位置，通常等于posIntake;(arm编码器位置数据、wrist伺服位置数据、claw伺服位置数据)
posLevelTop：Capping得分点比2层高度更高的余量位置对应的(arm编码器位置数据、wrist伺服位置数据、claw伺服位置数据)
posLevelMid：Capping得分点2层高度对应的(arm编码器位置数据、wrist伺服位置数据、claw伺服位置数据)
posLevelBottom：Capping得分点1层高度对应的(arm编码器位置数据、wrist伺服位置数据、claw伺服位置数据)
posIntake：抓取标志的位置，需要和限位开关结合使用；目前限位开关程序未完成;(arm编码器位置数据、wrist伺服位置数据、claw伺服位置数据)

## 手动阶段程序
teamcode/Manual.java


## 自动动阶段程序
自动程序的所有方案存储在AutoPlan.java文件
所有自动程序结构基本完全一样，仅需在init方法load不同的自动方案即可

因此，当需要新建一个自动路线或者方案时，需要完成2步操作：
1、AutoPlan中增加一个新的方案，其中确定每个设备模块的任务链
2、复制Auto_Sample；粘贴到一个新名字的文件中；打开并在init方法中修改自动方案名称为步骤1中设计的方法名字即可。


案例程序：
teamcode/Auto_Sample.java
包含主要功能演示，不具有实际运行效果。
可以直接复制粘贴此文件来创建新的自动路线

案例程序：蓝色下路
teamcode/Auto_Blue_Storage.java

案例程序：蓝色上路
teamcode/Auto_Blue_Wearhouse.java

案例程序：红色下路
teamcode/Auto_Red_Storage.java

自动路线规划：
teamcode/AutoPlan.java
所有的自动路线规划尽在此文件中。
能够完成对机器人的各个子系统的控制。


