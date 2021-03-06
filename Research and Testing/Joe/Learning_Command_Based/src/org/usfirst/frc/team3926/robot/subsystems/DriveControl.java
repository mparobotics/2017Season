package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 *
 */
public class DriveControl extends Subsystem {

    private double rightSide = 0;
    private double leftSide = 0;

    private RobotDrive driveSystem;


    public void DriveControl() {
        driveSystem = new RobotDrive(RobotMap.FRONT_RIGHT_MOTOR_PWM,
                RobotMap.BACK_RIGHT_MOTOR_PWM, RobotMap.FRONT_LEFT_MOTOR_PWM, RobotMap.BACK_LEFT_MOTOR_PWM);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public void driveTank(double rightSpeed, double leftSpeed, boolean straight, boolean safe) {
        setSpeed(rightSpeed, leftSpeed);

        if (safe)
            safeMode();

        if (straight)
            straightDrive();


        driveSystem.tankDrive(leftSide, rightSide);
    }


    private void setSpeed(double rightSpeed, double leftSpeed) {
        rightSide = rightSpeed;
        leftSide = leftSpeed;
    }

    private void safeMode(){
        rightSide *= RobotMap.SAFETY_FACTOR;
        leftSide *= RobotMap.SAFETY_FACTOR;
    }

    private void straightDrive() {
        leftSide = rightSide;
    }

    public void reset(){
        rightSide = 0;
        leftSide  = 0;

    }
}

