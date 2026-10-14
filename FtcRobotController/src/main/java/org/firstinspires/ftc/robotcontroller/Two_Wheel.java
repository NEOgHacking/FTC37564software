package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp

public class Two_Wheel extends LinearOpMode {
    private DcMotor motorBackLeft;
    private DcMotor motorBackRight;
    private CRServo servo1;

    void drive(double x, double y, double zr) {
        motorBackLeft.setPower(y+zr);
        motorBackRight.setPower(y-zr);
    }
    @Override
    public void runOpMode() {
        motorBackLeft = hardwareMap.get(DcMotor.class, "motorBackLeft");
        motorBackRight = hardwareMap.get(DcMotor.class, "motorBackRight");
        servo1 = hardwareMap.get(CRServo.class, "servo");

        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double zr = gamepad1.right_stick_x;
            drive(x, y, zr);
            servo1.setPower(gamepad1.right_stick_y);
            telemetry.update();
        }
    }
}
