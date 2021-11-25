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
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;


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

@TeleOp(name="Shoot test", group="Linear Opmode")
public class ShootTest extends LinearOpMode {

    // Declare OpMode members.
    Robot robot = null;
    private ElapsedTime runtime = new ElapsedTime();

    double speeds[] = {0,1600,1700,1800,1900,2000,2100,2150,2200,2250, 2300,2350,2400,2450,2500};
    int speedIndex = 0;



    @Override
    public void runOpMode() {

        robot = new Robot();
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        robot.intake.start();
        double t = 0;
        double speedRate = 0.5;

        while (opModeIsActive()) {


            double y = 1 * -gamepad1.left_stick_y; //前进
            double x = 1 * gamepad1.left_stick_x; //平移
            double yaw = 1 * gamepad1.right_stick_x; //转弯
            if(gamepad1.right_stick_button){
                y*=speedRate;
                yaw*=speedRate;
            }
            if(gamepad1.left_stick_button){
                x*=speedRate;
            }
            robot.drive.drive(y, x, yaw); //
            //robot.launch.leftFlywheel.

            if (gamepad1.left_trigger > 0) {
                robot.intake.spit(20.0 * 360);
            } else if (gamepad1.right_trigger > 0) {
                robot.intake.intake(20.0 * 360);
            } else {
                robot.intake.stop();
            }
            // 底盘控制
            if(gamepad1.dpad_up && runtime.milliseconds()-t>750){
                speedIndex++;
                if(speedIndex>=speeds.length){
                    speedIndex = 0;
                }
                t = runtime.milliseconds();
            }
            if(gamepad1.dpad_down && runtime.milliseconds()-t>750){
                speedIndex--;
                if(speedIndex<0)
                    speedIndex = speeds.length-1;
                t = runtime.milliseconds();
            }

            if(gamepad1.left_bumper || gamepad1.right_bumper){
                double s = speeds[speedIndex];
                if(Math.abs(robot.launch.leftFlywheel.getVelocity()-s)<50){
                    robot.launch.push();
                }
            }
            robot.launch.launch(speeds[speedIndex]);


            telemetry.addData("change speed:",":dpad_up, dpad_down");
            telemetry.addData("shoot:",": hold any bumper, trig by speed");
            telemetry.addData("flywheel:",robot.launch.leftFlywheel.getVelocity());
            telemetry.addData("target speed:",speeds[speedIndex]);
            telemetry.update();
        }
    }


}
