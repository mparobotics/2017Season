package org.usfirst.frc.team3926.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.OI;
import org.usfirst.frc.team3926.robot.Robot;
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
    private RobotDrive driveSystem;
    /** Specifies if {@link OI#contourError} was pressed last iteration of the code */
    private boolean    contourErrorPress;
    /** Records which time the user is specifying an error (increments each unique press of {@link OI#contourError} */
    private int        contourErrorGroup;

    /**
     * Initializer for drive control
     */
    public DriveControl() {

        if (RobotMap.USE_CAN_TALON)
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
     */
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new UserDriveTank());
    }

    /**
     * Resets the data for the drive system
     */
    public void reset() {

        rightSide = 0;
        leftSide = 0;
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
     * Set the speed to drive the robot
     *
     * @param rightSpeed Speed to drive the right side
     * @param leftSpeed  Speed to drive the left side
     */
    private void setSpeed(double rightSpeed, double leftSpeed) {

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
     * Drives the robot towards a target autonomously
     * <p>
     * If {@link RobotMap#DEBUG} is true, this will also allow the driver to log when an incorrect action was taken by
     * pressing {@link RobotMap#XBOX_CONTOUR_ERROR_BUTTON} if {@link RobotMap#XBOX_DRIVE_CONTROLLER} or
     * {@link RobotMap#CONTOUR_ERROR_BUTTON}
     * </p>
     */
    public void autonomousTank() {
        //Gets data on where to drive an the contour that it is using
        //Map<String, Double> data = Robot.visionProcessing.moveToCenter(0);

        //handleDriveData("autonomousTank", data);

    }

    /**
     * Takes data from one of the vision processing methods (removed) called from {@link this#center()} or
     * {@link this#autonomousTank()} and uses it to drive the robot
     *
     * @param callingMethod Name of the method that is calling this (used for debugging)
     * @param data          Data from one of the vision processing methods to use for driving the robot
     */
    private void handleDriveData(String callingMethod, Map<String, Double> data) {

        //Sets the speed of the robot to the values from vision tracking
        setSpeed(data.get(RobotMap.SPEED_RIGHT_KEY), data.get(RobotMap.SPEED_LEFT_KEY));

        /* This will likely go by so quickly that the user will not be able to click based on an error right away,
         * so try to look for the value in the middle of a group of this error */
        if (RobotMap.DEBUG && Robot.oi.contourError.get() &&
            data.get(RobotMap.SPEED_LEFT_KEY) != RobotMap.ILLEGAL_INT) {

            contourErrorPress = true;

            System.out.println("ERROR IN DriveControl." + callingMethod + ": Group #" +
                               contourErrorGroup);
            System.out.println("\tPassed SmartFilter: " +
                               ((data.get(RobotMap.SMARTFILTER_PASS_KEY) != RobotMap.ILLEGAL_DOUBLE) ? "true" :
                                "false"));
            contourDataPrint(RobotMap.SMARTFILTER_PASS_KEY, data);
            contourDataPrint(RobotMap.CONTOUR_X_KEY, data);
            contourDataPrint(RobotMap.CONTOUR_Y_KEY, data);
            contourDataPrint(RobotMap.CONTOUR_WIDTH_KEY, data);
            contourDataPrint(RobotMap.CONTOUR_HEIGHT_KEY, data);
            contourDataPrint(RobotMap.SPEED_LEFT_KEY, data);
            contourDataPrint(RobotMap.SPEED_RIGHT_KEY, data);
            System.out.println('\t' + "area: " + data.get(RobotMap.CONTOUR_X_KEY) * data.get(RobotMap.CONTOUR_Y_KEY));
            //Robot.visionProcessing.smartFilterData.printInformation();

        } else if (contourErrorPress) {

            contourErrorPress = false;
            ++contourErrorGroup;

        }

        driveSystem.tankDrive(leftSide, rightSide);

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

    /**
     * Centers the robot on a target based on vision tracking data
     */
    public void center() {

        //Map<String, Double> data = Robot.visionProcessing.turnToCenter(0);

        //handleDriveData("center", data);

    }

}

