package org.usfirst.frc.team3926.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.OI;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.UserDriveTank;

import java.util.Map;

/***********************************************************************************************************************
 * Enables driving of the robot with a tank drive scheme. This class contains anything relevant to actuating the drive
 * motors. This includes autonomous handling of the robot.
 * @author William Kluge
 * <p>
 * Contact: klugewilliam@gmail.com
 * </p>
 **********************************************************************************************************************/
public class DriveControl extends Subsystem {

    /** Holds the speed for the right motor */
    private double rightSide = 0;
    /** Holds the speed for the left motor */
    private double leftSide  = 0;
    /** Object to handle actual driving of motors */
    private RobotDrive   driveSystem;
    /** Specifies if {@link OI#contourError} was pressed last iteration of the code */
    private boolean      contourErrorPress;
    /** Records which time the user is specifying an error (increments each unique press of {@link OI#contourError} */
    private int          contourErrorGroup;
    /** Network table for vision processing of the high goal target */
    private NetworkTable highGoalTable;
    /**  */
    private boolean      moveLeft, moveRight, contoursFound, centered;

    ////////////////////////////////////////// Initializers and Constructors ///////////////////////////////////////////

    /**
     * Constructor for drive control
     */
    public DriveControl() {

        if (RobotMap.DRIVE_USE_CAN_TALON)
            driveSystem = new RobotDrive(new CANTalon(RobotMap.FRONT_LEFT_MOTOR_CAN),
                                         new CANTalon(RobotMap.BACK_LEFT_MOTOR_CAN),
                                         new CANTalon(RobotMap.FRONT_RIGHT_MOTOR_CAN),
                                         new CANTalon(RobotMap.BACK_RIGHT_MOTOR_CAN));
        else
            driveSystem = new RobotDrive(RobotMap.FRONT_LEFT_MOTOR_PWM, RobotMap.BACK_LEFT_MOTOR_PWM,
                                         RobotMap.FRONT_RIGHT_MOTOR_PWM, RobotMap.BACK_RIGHT_MOTOR_PWM);
        contourErrorPress = false;
        contourErrorGroup = 0;
    }

    /**
     * Initializes the default command for this SubSystem
     * This sets the default command to be user-controlled tank drive with the {@link UserDriveTank} command
     */
    public void initDefaultCommand() {

        setDefaultCommand(new UserDriveTank());
    }

    /**
     * Resets the data for the drive system
     */
    public void reset() {

        rightSide = 0;
        leftSide = 0;
    }

    //////////////////////////////////////////////// Robot Driving /////////////////////////////////////////////////////

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
     * Drives the robot towards a target autonomously
     * TODO make this work for the gear targets too
     */
    public void autonomousTank() {

        int checkIndex = 0; //TODO add minor filtering

        double[] contourCenter = highGoalTable.getNumberArray(RobotMap.CONTOUR_X_KEY, RobotMap.DEFAULT_VALUE);

        if (contourCenter[checkIndex] != RobotMap.ILLEGAL_DOUBLE) {

            contoursFound = true;

            if (contourCenter[checkIndex] > RobotMap.SCREEN_CENTER[0] * (1 + RobotMap.ALLOWABLE_ERROR)) {
                setSpeed(RobotMap.AUTONOMOUS_SPEED, ((contourCenter[checkIndex] - RobotMap.SCREEN_CENTER[0]) /
                                                     RobotMap.SCREEN_CENTER[0]) * RobotMap.AUTONOMOUS_SPEED);
                moveLeft = false;
                moveRight = true;
                centered = false;
            } else if (contourCenter[checkIndex] < RobotMap.SCREEN_CENTER[0] * (1 - RobotMap.ALLOWABLE_ERROR)) {
                setSpeed((contourCenter[checkIndex] / RobotMap.SCREEN_CENTER[0]) * RobotMap.AUTONOMOUS_SPEED, 0);
                moveRight = false;
                centered = false;
                moveLeft = true;
            } else {
                moveLeft = false;
                moveRight = false;
                centered = true;
            }

        } else {
            setSpeed(0, 0);
            contoursFound = false;
        }

        SmartDashboard.putBoolean("Move Left", moveLeft);
        SmartDashboard.putBoolean("Move Right", moveRight);
        SmartDashboard.putBoolean("Centered", centered);
        SmartDashboard.putBoolean("Contours Found", contoursFound);
        SmartDashboard.putNumber("Driving Based on Contour: ", checkIndex);

    }

    /**
     * Centers the robot on a target based on vision tracking data
     */
    public void center() {

        autonomousTank();

        if (moveLeft)
            rightSide *= -1;
        else if (moveLeft)
            leftSide *= -1;

    }

    //////////////////////////////////////////// Drive Behavior Modification ///////////////////////////////////////////

    /**
     * Set the speed to drive the robot
     *
     * @param leftSpeed  Speed to drive the left side
     * @param rightSpeed Speed to drive the right side
     */
    private void setSpeed(double leftSpeed, double rightSpeed) {

        if (leftSpeed != RobotMap.ILLEGAL_DOUBLE) {
            rightSide = rightSpeed;
            leftSide = leftSpeed;
        } else
            rightSide = leftSide = 0;

        if (RobotMap.INVERT_RIGHT_DRIVE_MOTOR_DIRECTION)
            rightSpeed *= -1;

        if (RobotMap.INVERT_LEFT_DRIVE_MOTOR_DIRECTION)
            leftSpeed *= -1;

        if (rightSpeed != RobotMap.ILLEGAL_DOUBLE)
            SmartDashboard.putNumber("Right Speed: ", rightSide);

        if (leftSpeed != RobotMap.ILLEGAL_DOUBLE)
            SmartDashboard.putNumber("Left Speed: ", leftSide);

    }

    /**
     * Drive the robot straight (based on the speed of the right side)
     * <p>
     * This is activated by pressing {@link OI#straightMode}
     * </p>
     */
    private void straightDrive() {

        if (RobotMap.XBOX_DRIVE_CONTROLLER)
            rightSide = leftSide;
        else
            leftSide = rightSide;
    }

    /**
     * Drive the robot in safety mode (reduces the speed by {@link RobotMap#DRIVE_SAFETY_FACTOR}
     * <p>
     * This is activated by pressing {@link OI#safetyMode}
     * </p>
     */
    private void safeMode() {

        rightSide *= RobotMap.DRIVE_SAFETY_FACTOR;
        leftSide *= RobotMap.DRIVE_SAFETY_FACTOR;
    }

    /**
     * Prints part of the data for a contour
     *
     * @param key  Key from {@link RobotMap} to identify a part of the contour
     * @param data Data on the contour to print
     */
    private void contourDataPrint(String key, Map<String, Double> data) {

        System.out.println('\t' + key + ": " + data.get(key));

    }

}

