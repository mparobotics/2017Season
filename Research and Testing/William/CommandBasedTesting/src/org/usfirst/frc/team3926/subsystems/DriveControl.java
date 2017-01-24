package org.usfirst.frc.team3926.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.OI;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

import java.util.Map;

/***********************************************************************************************************************
 * Enables driving of the robot with a tank drive scheme
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

        driveSystem = new RobotDrive(RobotMap.FRONT_RIGHT_MOTOR_PWM, RobotMap.BACK_RIGHT_MOTOR_PWM,
                                     RobotMap.FRONT_LEFT_MOTOR_PWM, RobotMap.BACK_LEFT_MOTOR_PWM);
        contourErrorPress = false;
        contourErrorGroup = 0;
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
     * <p>
     * If {@link RobotMap#DEBUG} is true, this will also allow the driver to log when an incorrect action was taken by
     * pressing {@link RobotMap#XBOX_CONTOUR_ERROR_BUTTON} if {@link RobotMap#XBOX_DRIVE_CONTROLLER} or
     * {@link RobotMap#CONTOUR_ERROR_BUTTON}
     * </p>
     */
    public void autonomousTank() {

        Map<String, Double> data = Robot.visionProcessing.moveToCenter(0);

        setSpeed(data.get(RobotMap.SPEED_RIGHT_KEY), data.get(RobotMap.SPEED_LEFT_KEY));

        SmartDashboard.putNumber("Right Speed: ", rightSide);
        SmartDashboard.putNumber("Left Speed: ", leftSide);

        if (leftSide != RobotMap.ILLEGAL_DOUBLE)
            driveSystem.tankDrive(rightSide, leftSide);
        else
            driveSystem.tankDrive(0, 0);

        driveSystem.tankDrive(leftSide, rightSide);

        /* This will likely go by so quickly that the user will not be able to click based on an error right away,
         * so try to look for the value in the middle of a group of this error */
        if (RobotMap.DEBUG && Robot.oi.contourError.get()) {

            System.out.println("USER DESIGNATED ERROR WHEN DRIVING BASED ON CONTOURS");
            System.out.println('\t' + RobotMap.CONTOUR_X_KEY + data.get(RobotMap.CONTOUR_X_KEY));
            System.out.println('\t' + RobotMap.CONTOUR_Y_KEY + data.get(RobotMap.CONTOUR_Y_KEY));
            System.out.println('\t' + RobotMap.CONTOUR_WIDTH_KEY + data.get(RobotMap.CONTOUR_WIDTH_KEY));
            System.out.println('\t' + RobotMap.CONTOUR_HEIGHT_KEY + data.get(RobotMap.CONTOUR_HEIGHT_KEY));
            System.out.println('\t' + RobotMap.SPEED_RIGHT_KEY + data.get(RobotMap.SPEED_RIGHT_KEY));
            System.out.println('\t' + RobotMap.SPEED_LEFT_KEY + data.get(RobotMap.SPEED_LEFT_KEY));

        }

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

