package org.usfirst.frc.team3926.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

import java.util.Map;

/***********************************************************************************************************************
 * Enables driving of the robot with a tank drive scheme
 **********************************************************************************************************************/
public class DriveControl extends Subsystem {

    /* Holds the speed for the right motor*/
    private double rightSide = 0;
    /* Holds the speed for the left motor */
    private double leftSide = 0;

    /* The drive class */
    private RobotDrive driveSystem;

    /**
     * Initializer for drive control
     */
    public DriveControl() {

        driveSystem = new RobotDrive(RobotMap.FRONT_RIGHT_MOTOR_PWM, RobotMap.BACK_RIGHT_MOTOR_PWM,
                                     RobotMap.FRONT_LEFT_MOTOR_PWM, RobotMap.BACK_LEFT_MOTOR_PWM);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    /**
     * Drives the robot in a tank configuration
     *
     * @param rightSpeed The speed to set the right motor
     * @param leftSpeed  The speed to set the left motor
     * @param straight   Whether or not the robot should drive straight
     * @param safe       Whether or not to reduce robot speed
     */
    public void driveTank(double rightSpeed, double leftSpeed, boolean straight, boolean safe) {

        setSpeed(rightSpeed, leftSpeed);

        if (straight)
            straightDrive();

        if (safe)
            safeMode();

        driveSystem.tankDrive(leftSide, rightSide);

    }

    /**
     * Drives the robot in tank drive during the autonomous period
     * @throws IllegalArgumentException This happens if {@link RobotMap#SPEED_KEY} was not associated with a double[]
     */
    public void autonomousTank() {

        Map<String, Object> data = Robot.visionProcessing.moveToCenter(0);

        Robot.visionProcessing.printTableInfo();

        Object speeds = data.get(RobotMap.SPEED_KEY);

        if (speeds instanceof double[]) {

            double[] confirmedSpeeds = ((double[]) speeds).clone();

            SmartDashboard.putNumber("Right Speed: ", confirmedSpeeds[0]);
            SmartDashboard.putNumber("Left Speed: ", confirmedSpeeds[1]);

            if (confirmedSpeeds != RobotMap.ILLEGAL_VALUE)
                driveSystem.tankDrive(confirmedSpeeds[0], confirmedSpeeds[1]);
            else
                driveSystem.tankDrive(0, 0);

        } else {
            throw new IllegalArgumentException("The speeds key was not associated with a double[]");
        }

        if (!RobotMap.DEBUG)
            return;



    }

    /**
     * Set the speed to drive the robot
     *
     * @param rightSpeed Speed to drive the right side
     * @param leftSpeed  Speed to drive the left side
     */
    private void setSpeed(double rightSpeed, double leftSpeed) {

        rightSide = rightSpeed;
        leftSide = leftSpeed;
    }

    /**
     * Drive the robot straight (based on the speed of the right side)
     */
    private void straightDrive() {

        leftSide = rightSide;
    }

    /**
     * Drive the robot in safety mode (reduces the speed
     */
    private void safeMode() {

        rightSide *= RobotMap.DRIVE_SAFTEY_FACTOR;
        leftSide *= RobotMap.DRIVE_SAFTEY_FACTOR;
    }

    /**
     * Resets the data for the drive system
     */
    public void reset() {

        rightSide = 0;
        leftSide = 0;
    }

}

