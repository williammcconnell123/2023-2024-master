
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOpTired extends LinearOpMode {

    DcMotorEx leftlow;
    DcMotorEx leftup;
    DcMotorEx rightlow;
    DcMotorEx rightup;
    DcMotorEx armextend;
    Servo armlift;
    Servo dustpan;

    private double leftRightThreshold = 0.3;


    @Override

    public void runOpMode(){
        initialize();
        double curPos = 0.5;
        double armPos = 0.5;

        waitForStart();

        while(opModeIsActive()){


            //Movement of the wheels
            //Cryptic, not actually sure how this works
            double r = Math.hypot(gamepad1.left_stick_x - gamepad1.right_stick_x, gamepad1.left_stick_y - gamepad1.right_stick_y);
            double robotAngle = Math.atan2(gamepad1.left_stick_y - gamepad1.right_stick_y, -gamepad1.left_stick_x + gamepad1.right_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_trigger - gamepad1.left_trigger;
            final double v1 = r * Math.cos(robotAngle) + rightX;
            final double v2 = r * Math.sin(robotAngle) - rightX;
            final double v3 = r * Math.sin(robotAngle) + rightX;
            final double v4 = r * Math.cos(robotAngle) - rightX;
            leftup.setPower(v1);
            rightup.setPower(-v2);
            leftlow.setPower(v3);
            rightlow.setPower(-v4);

            //Extends arm
            double extendPower = (gamepad2.right_trigger - gamepad2.left_trigger);
            double modifier = .75;
            armextend.setPower(extendPower * modifier);

            //Back servo for lifting arm
            double liftMax = .75;
            if(gamepad1.dpad_down){
                armPos += .0006;
                armlift.setPosition(armPos);
                if(armlift.getPosition()>liftMax){
                    break;
                }
            }
            if(gamepad1.dpad_up){
                armPos -= .0006;
                armlift.setPosition(armPos);
            }
            /*
            while(gamepad2.right_bumper){
                armPos += .001  ;
                armlift.setPosition(armPos);
            }
            while(gamepad2.left_bumper){
                armPos -= .001;
                armlift.setPosition(armPos);
            }
            */

            //Front servo for dustpan
            if(gamepad2.dpad_down){
                curPos += .0006;
                dustpan.setPosition(curPos);
            }
            if(gamepad2.dpad_up){
                curPos -= .0006;
                dustpan.setPosition(curPos);
            }



          /*
            tired,, if statements might be redundant/harmful


            * If the other one is so so evil and refuses to work
            * Will only fully extend or retract
            * evil
            * may also break

            double extensionIncrement = Servo.MAX_POSITION/20;
            if(gamepad2.right_bumper){
                while(gamepad2.right_bumper) {
                    armlift.setPosition(armlift.getPosition() + extensionIncrement);
                }
            }else if(gamepad2.left_bumper){
                while(gamepad2.right_bumper){
                    armlift.setPosition(armlift.getPosition() - extensionIncrement);
                }
            }

             */

            telemetry.addData("Status", "Initialized");
            telemetry.update();

            /*
            *Emergency stop, obsolete
            if(gamepad2.square){
                leftlow.setPower(0);
                leftup.setPower(0);
                rightlow.setPower(0);
                rightup.setPower(0);
            }

            * Old movement code
            if(gamepad1.right_stick_x > leftRightThreshold){
                leftlow.setPower(gamepad1.right_stick_x);
                leftup.setPower(gamepad1.right_stick_x);
                rightlow.setPower(-gamepad1.right_stick_x);
                rightup.setPower(gamepad1.right_stick_x);
            }else if(gamepad1.right_stick_x < -leftRightThreshold){
                leftlow.setPower(gamepad1.right_stick_x);
                leftup.setPower(gamepad1.right_stick_x);
                rightlow.setPower(-gamepad1.right_stick_x);
                rightup.setPower(gamepad1.right_stick_x);
            }else{
                leftlow.setPower(gamepad1.right_stick_y);
                leftup.setPower(gamepad1.right_stick_y);
                rightlow.setPower(-gamepad1.right_stick_y);
                rightup.setPower(gamepad1.right_stick_y);
            }
             */
        }

    }

    public void initialize(){
        leftlow = hardwareMap.get(DcMotorEx.class, "lLow");
        leftup = hardwareMap.get(DcMotorEx.class, "lUp");
        rightlow = hardwareMap.get(DcMotorEx.class, "rLow");
        rightup = hardwareMap.get(DcMotorEx.class, "rUp");
        armextend = hardwareMap.get(DcMotorEx.class, "extend");
        armlift = hardwareMap.get(Servo.class,"lift");
        dustpan = hardwareMap.get(Servo.class, "dust");

        rightup.setDirection(DcMotorSimple.Direction.REVERSE);
        armlift.setDirection(Servo.Direction.REVERSE);

        leftlow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftup.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightlow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightup.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
