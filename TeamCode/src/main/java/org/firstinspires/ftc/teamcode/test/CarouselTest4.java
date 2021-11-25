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


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop      for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

// 自动加速模式。按2秒钟间隔从基础速度线性加速到最大值

@TeleOp(group="Linear Opmode")
public class CarouselTest4 extends LinearOpMode {

    // Declare OpMode members.
    DcMotorEx motor = null;
    private ElapsedTime runtime = new ElapsedTime();
    double speed = 0.0;
    double speedPlusMax = 360.0;
    @Override
    public void runOpMode() {

        motor = hardwareMap.get(DcMotorEx.class,"c");

        waitForStart();
        runtime.reset();

        double t = 0;
        double autoStart = 0;
        boolean autoA = false;
        while (opModeIsActive()) {
            // 增减基础转速的数值
            if(gamepad1.dpad_up && runtime.milliseconds()-t>750){
                speed +=90;
                if(speed >=3000) speed = 0;
                t = runtime.milliseconds();
            }
            if(gamepad1.dpad_down && runtime.milliseconds()-t>750){
                speed-=90;
                if(speed <0)   speed = 0;
                t = runtime.milliseconds();
            }
            // 增减额外加速最大值的数值
            if(gamepad1.y && runtime.milliseconds()-t>750){
                speedPlusMax +=90;
                if(speedPlusMax >=3000) speedPlusMax = 360;
                t = runtime.milliseconds();
            }
            if(gamepad1.a && runtime.milliseconds()-t>750){
                speedPlusMax-=90;
                if(speedPlusMax <0) speedPlusMax = 360;
                t = runtime.milliseconds();
            }

            if(gamepad1.b){
                autoStart = runtime.milliseconds();
                autoA = true;
            }

            double speedPlus = 0;
            if(autoA) {
                speedPlus = (runtime.milliseconds() - autoStart) * speedPlusMax / 2000;
                if(runtime.milliseconds()-autoStart>2000){
                    autoA = false;
                }
            }

            motor.setVelocity(speed+speedPlus, AngleUnit.DEGREES);


            telemetry.addData("change speed:",":dpad_up, dpad_down");
            telemetry.addData("flywheel:",motor.getVelocity());
            telemetry.addData("target speed:",speed);
            telemetry.addData("target speedPlus:",speedPlus);
            telemetry.update();
        }
    }


}
