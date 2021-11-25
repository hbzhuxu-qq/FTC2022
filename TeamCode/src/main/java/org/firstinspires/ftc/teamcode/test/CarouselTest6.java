/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop      for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

// 多线程自动加速模式。按2秒钟间隔从基础速度加速到最大值

@TeleOp(group = "Linear Opmode")
public class CarouselTest6 extends LinearOpMode {

    // Declare OpMode members.
    DcMotorEx motor = null;
    private ElapsedTime runtime = new ElapsedTime();
    double speed = 0.0;
    double speedPlusMax = 360.0;
    double speedPlus = 0;
    double speedIncressIntervel = 2000;

    Timer timer = new Timer();

    // 用于多线程控制
    class CarouselSpeedControl extends TimerTask {
        ElapsedTime speedTimer = new ElapsedTime();

        @Override
        public void run() {
            speedTimer.reset();
            while (speedTimer.milliseconds() < speedIncressIntervel) {
                double bezierRate = getBezier(0.8,0.4,speedTimer.milliseconds() / speedIncressIntervel);
                speedPlus = bezierRate * speedPlusMax;
                motor.setVelocity(speed + speedPlus, AngleUnit.DEGREES);
            }
        }
        // 三阶贝塞尔曲线  2个数据点和2个控制点
        // 公式：B(t) = P0 * (1-t)^3 + 3 * P1 * t * (1-t)^2 + 3 * P2 * t^2 * (1-t) + P3 * t^3
        double getBezier(double p1, double p2, double t) {
            return 0 * Math.pow((1 - t), 3)
                    + 3 * p1 * t * Math.pow((1 - t), 2)
                    + 3 * p2 * Math.pow(t, 2) * (1 - t)
                    + 1 * Math.pow(t, 3);
        }
    }




    @Override
    public void runOpMode() {

        motor = hardwareMap.get(DcMotorEx.class, "c");

        waitForStart();
        runtime.reset();

        double t = 0;
        while (opModeIsActive()) {
            // 增减基础转速的数值
            if (gamepad1.dpad_up && runtime.milliseconds() - t > 750) {
                speed += 90;
                if (speed >= 3000) speed = 0;
                t = runtime.milliseconds();
            }
            if (gamepad1.dpad_down && runtime.milliseconds() - t > 750) {
                speed -= 90;
                if (speed < 0) speed = 0;
                t = runtime.milliseconds();
            }
            // 增减额外加速最大值的数值
            if (gamepad1.y && runtime.milliseconds() - t > 750) {
                speedPlusMax += 90;
                if (speedPlusMax >= 3000) speedPlusMax = 360;
                t = runtime.milliseconds();
            }
            if (gamepad1.a && runtime.milliseconds() - t > 750) {
                speedPlusMax -= 90;
                if (speedPlusMax < 0) speedPlusMax = 360;
                t = runtime.milliseconds();
            }

            if (gamepad1.b && gamepad1.a && runtime.milliseconds() - t > 2000) {
                CarouselSpeedControl task = new CarouselSpeedControl();
                timer.schedule(task, 0);
            }
            if (gamepad1.x) {
                motor.setVelocity(0);
            }


            telemetry.addData("change speed:", ":dpad_up, dpad_down");
            telemetry.addData("flywheel:", motor.getVelocity());
            telemetry.addData("target speed:", speed);
            telemetry.addData("target speedPlus:", speedPlus);
            telemetry.update();
        }
    }


}
