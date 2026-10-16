package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp

public class Op_mode extends LinearOpMode {
    private DcMotor motorFrontLeft;
    private DcMotor motorFrontRight;
    private DcMotor motorBackLeft;
    private DcMotor motorBackRight;
    double pos_x = 0;
    double pos_y = 0;
    double pos_zr = 0;
    double old_motFL = 0;
    double old_motFR = 0;
    double old_motBL = 0;
    double old_motBR = 0;

    void drive(double x, double y, double zr) {
        // Drive the motors in a way that would make the robot move in the right directions for a mecanum drive train.
        motorFrontLeft.setPower(y+x+zr);
        motorFrontRight.setPower(y-x-zr);
        motorBackLeft.setPower(y+x+zr);
        motorBackRight.setPower(y-x-zr);
    }
    void position() {
        // First calculate the difference between now and a period ago.
        double motFL = motorFrontLeft.getCurrentPosition() - old_motFL;
        double motFR = motorFrontRight.getCurrentPosition() - old_motFR;
        double motBL = motorFrontLeft.getCurrentPosition() - old_motBL;
        double motBR = motorFrontRight.getCurrentPosition() - old_motBR;
        // Then do some scary math that I hope works to see where the robot has gone in this time.
        pos_x = pos_x + motFL - motFR - motBL + motBR;
        pos_y = pos_y + motFL + motFR + motBL + motBR;
        pos_zr = pos_zr + motFL - motFR + motBL - motBR;
        // Now set the old motor position to what they are now for the next cycle.
        old_motFL = motorFrontRight.getCurrentPosition();
        old_motFL = motorFrontRight.getCurrentPosition();
        old_motFL = motorFrontRight.getCurrentPosition();
        old_motFL = motorFrontRight.getCurrentPosition();
    }

    @Override
    public void runOpMode() {
        motorFrontLeft = hardwareMap.get(DcMotor.class, "motorFrontLeft");
        motorFrontRight = hardwareMap.get(DcMotor.class, "motorFrontRight");
        motorBackLeft = hardwareMap.get(DcMotor.class, "motorBackLeft");
        motorBackRight = hardwareMap.get(DcMotor.class, "motorBackRight");

        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            position();
            // See where the sticks are on the gamepad to see where we need to move.
            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double zr = gamepad1.right_stick_x;
            // Then forward these requests to the drive logic that actually makes it move.
            drive(x, y, zr);
            telemetry.addData("pos x", pos_x);
            telemetry.addData("pos y", pos_y);
            telemetry.addData("rotation", pos_zr);
            telemetry.update();
        }
    }
}
