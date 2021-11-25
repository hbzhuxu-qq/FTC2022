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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

/**
 * This file illustrates the concept of driving a path based on encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forwards, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backwards for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This methods assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(group="Pushbot")
public class ETest extends LinearOpMode {

    /* Declare OpMode members. */
    DcMotorEx motor   = null;
    //设置几个点的编码器数值
    int v = 90;
    int startPos = 0;
    int transPos = 560 *(90/360);
    int highPos = 560 *(105/360);
    int midPos = 560 *(170/360);
    int lowPos = 560 *(240/360);

    @Override
    public void runOpMode() {
        //取得电机控制权
        motor = hardwareMap.get(DcMotorEx.class,"m");
        //编码器数值清零
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //默认设置为startPos位置
        motor.setTargetPosition(startPos);
        //设置为运行到指定点的模式
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //上电，开始跑
        motor.setPower(1);

        // 想办法区分头部下降和抬升的状态
        // 下降
        motor.setPositionPIDFCoefficients(5);

        // 抬升
        motor.setPositionPIDFCoefficients(10);

        waitForStart();
        //
        while (opModeIsActive() ) {
            if(gamepad1.a){
                motor.setTargetPosition(transPos);
            }
            if(gamepad1.b){
                motor.setTargetPosition(highPos);
            }
            if(gamepad1.x){
                motor.setTargetPosition(midPos);
            }
            if(gamepad1.y){
                motor.setTargetPosition(lowPos);
            }
            // 如果向着90度方向旋转，认定为上升；如果离开90度方向，认定为下降
            // 如果上升，则p值设置较大；如果下降，则p值设置较小

            telemetry.addData("Pos:",  " %7d", motor.getCurrentPosition());
            telemetry.update();
        }

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

}
