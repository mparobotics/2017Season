package org.usfirst.frc.team3926.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.OI;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.UserDriveTank;

/***********************************************************************************************************************
 * Enables driving of the robot with a tank drive scheme. This class contains anything relevant to actuating the drive
 * motors. This includes autonomous handling of the robot.
 * @author William Kluge
 *      <p>
 *      Contact: klugewilliam@gmail.com
 *      </p>
 * TODO autonomous vision driving
 * TODO add offset for driving to gears
 **********************************************************************************************************************/
public class DriveControl extends Subsystem {

    /** Holds the speed for the right motor */
    private double rightSide = 0;
    /** Holds the speed for the left motor */
    private double leftSide  = 0;
    /** Object to handle actual driving of motors */
    private RobotDrive driveSystem;
    /** Network table for vision processing of the high goal target */
    private NetworkTable visionTable = null;
    /** Vision processing booleans */
    private boolean moveLeft, moveRight, contoursFound, centered;
    /** Encoder for the robot's left side */
    public  Encoder     leftEncoder;
    /** Encoder for the robot's right side */
    public  Encoder     rightEncoder;
    /** Rangefinder for autonomous distance finding */
    private AnalogInput rangefinder;

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

        leftEncoder = new Encoder(RobotMap.DRIVE_LEFT_ENCODER_A_CHANNEL, RobotMap.DRIVE_LEFT_ENCODER_B_CHANNEL);
        leftEncoder.setDistancePerPulse(RobotMap.DRIVE_ENCODER_DISTANCE_PER_PULSE);
        rightEncoder = new Encoder(RobotMap.DRIVE_RIGHT_ENCODER_A_CHANNEL, RobotMap.DRIVE_RIGHT_ENCODER_B_CHANNEL);
        rightEncoder.setDistancePerPulse(RobotMap.DRIVE_ENCODER_DISTANCE_PER_PULSE);
        rangefinder = new AnalogInput(RobotMap.RANGEFINDER_ANALOG_IN_PORT);

    }

    /**
     * Initializes the default command for this SubSystem
     * This sets the default command to be user-controlled tank drive with the {@link UserDriveTank} command
     */
    public void initDefaultCommand() {

        setDefaultCommand(new UserDriveTank());
    }

    /**
     * Initialises the network table for vision processing
     * <p>
     * Note: When this is put in the constructor it seems to always fail
     * </p>
     *
     * @param networkTableName Name of the network table to initialize
     */
    public void initNetworkTables(String networkTableName) {

        visionTable = NetworkTable.getTable(networkTableName);

    }

    /**
     * Resets the data for the drive system
     */
    public void reset() {

        rightSide = 0;
        leftSide = 0;
        rightEncoder.reset();
        leftEncoder.reset();
    }

    //////////////////////////////////////////////// Robot Driving /////////////////////////////////////////////////////

    /**
     * Drives the robot in a tank configuration
     *
     * @param rightSpeed Speed to set the right motor
     * @param leftSpeed  Speed to set the left motor
     * @param straight   Whether or not the robot should drive straight
     * @param safe       Whether or not to reduce robot speed
     * @param invert     Whether or not to invert the drive direction of the robot
     */
    public void driveTank(double rightSpeed, double leftSpeed, boolean straight, boolean safe, boolean invert) {

        setSpeed(rightSpeed, leftSpeed);

        if (straight)
            straightDrive();

        if (safe)
            safeMode();

        if (invert) {
            leftSide *= -1;
            rightSide *= -1;
        }

        SmartDashboard.putBoolean("Straight mode", straight);
        SmartDashboard.putBoolean("Safety mode", safe);
        SmartDashboard.putBoolean("Invert Drive", invert);

        driveSystem.tankDrive(leftSide, rightSide);

    }

    /**
     * Drives the robot in tank configuration
     *
     * @param rightSpeed Speed to set the right side of the drive train
     * @param leftSpeed  Speed to set the left side of the drive train
     */
    public void driveTank(double rightSpeed, double leftSpeed) {

        setSpeed(leftSpeed, rightSpeed);

    }

    /**
     * Drives the robot towards a target autonomously
     *
     * @param targetGears Set this to true if vision tracking should work to center itself on gears not the high goal
     *                    target, which it will do if this is true
     */
    public void autonomousTank(boolean targetGears) {

        int checkIndex = 0; //TODO add minor filtering

        double[] contourCenter = visionTable.getNumberArray(RobotMap.CONTOUR_X_KEY, RobotMap.DEFAULT_VALUE);

        if (contourCenter.length != 0) {

            double target = (targetGears) ? (contourCenter[0] + contourCenter[1]) / 2 : contourCenter[0];

            if (target == RobotMap.ILLEGAL_DOUBLE)
                return;

            contoursFound = true;

            if (target > RobotMap.SCREEN_CENTER[0] * (1 + RobotMap.ALLOWABLE_ERROR)) {
                setSpeed(RobotMap.AUTONOMOUS_SPEED, ((target - RobotMap.SCREEN_CENTER[0]) /
                                                     RobotMap.SCREEN_CENTER[0]) * RobotMap.AUTONOMOUS_SPEED);
                moveLeft = false;
                moveRight = true;
                centered = false;
            } else if (target < RobotMap.SCREEN_CENTER[0] * (1 - RobotMap.ALLOWABLE_ERROR)) {
                setSpeed((target / RobotMap.SCREEN_CENTER[0]) * RobotMap.AUTONOMOUS_SPEED, 0);
                moveRight = false;
                centered = false;
                moveLeft = true;
            } else {
                setSpeed(RobotMap.AUTONOMOUS_SPEED, RobotMap.AUTONOMOUS_SPEED);
                moveLeft = false;
                moveRight = false;
                centered = true;
            }

        } else {
            setSpeed(0, 0);
            contoursFound = false;
        }

        driveSystem.tankDrive(leftSide, rightSide);

        SmartDashboard.putBoolean("Move Left", moveLeft);
        SmartDashboard.putBoolean("Move Right", moveRight);
        SmartDashboard.putBoolean("Centered", centered);
        SmartDashboard.putBoolean("Contours Found", contoursFound);
        SmartDashboard.putNumber("Driving Based on Contour: ", checkIndex);

    }

    /**
     * Centers the robot on a target based on vision tracking data
     * <p>
     * To actually get data this calls {@link #autonomousTank(boolean)} and than inverts the speed on the opposite of
     * the side to move to, this causes the robot to turn.
     * </p>
     *
     * @param targetGears Whether or not this is being run to focus on the gear targets
     */
    public void center(boolean targetGears) {

        autonomousTank(targetGears);

        if (moveLeft)
            setSpeed(leftSide * RobotMap.TURNING_SPEED_MULTIPLIER, rightSide * -RobotMap.TURNING_SPEED_MULTIPLIER);
        else if (moveRight)
            setSpeed(leftSide * -RobotMap.TURNING_SPEED_MULTIPLIER, rightSide * RobotMap.TURNING_SPEED_MULTIPLIER);
        else
            setSpeed(0, 0);

        driveSystem.tankDrive(leftSide, rightSide);

    }

    /**
     * Keeps the robot driving based on it's current left and right side speed values
     */
    public void continueDriving() {

        driveSystem.tankDrive(leftSide, rightSide);

    }

    /**
     * Checks if the robot is at or closer than desiredDistance
     *
     * @param desiredDistance Distance to check if the robot is closer to an object than
     * @return If the rangefinder's measured distance is less than or equal to the desired distance
     */
    public boolean withinDistance(double desiredDistance) {

        return getRangeMM() <= desiredDistance;

    }

    /**
     * Checks if the robot has traveled past a value on the right side
     *
     * @param rightEncoderValue Value to check if the right encoder has passed
     * @return If the right encoder on the drive train has traveled past the given distance
     */
    public boolean rightEncoderCheck(double rightEncoderValue) {

        return Math.abs(rightEncoder.getDistance()) >= Math.abs(rightEncoderValue);

    }

    /**
     * Checks if the robot has traveled past a value on the left side
     *
     * @param leftEncoderValue Value to check if the left encoder has passed
     * @return If the left encoder's distance value if past the given distance
     */
    public boolean leftEncoderCheck(double leftEncoderValue) {

        return Math.abs(leftEncoder.getDistance()) >= Math.abs(leftEncoderValue);

    }

    /**
     * TODO finish
     *
     * @param desiredAngle Angle to turn to
     * @return Whether or not the robot has reached the desired angle
     */
    public boolean turnToAngle(double desiredAngle) {

        return true;

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
            rightSide *= -1;

        if (RobotMap.INVERT_LEFT_DRIVE_MOTOR_DIRECTION)
            leftSide *= -1;

        if (rightSpeed != RobotMap.ILLEGAL_DOUBLE)
            SmartDashboard.putNumber("Right Speed: ", rightSide);

        if (leftSpeed != RobotMap.ILLEGAL_DOUBLE)
            SmartDashboard.putNumber("Left Speed: ", leftSide);

        SmartDashboard.putNumber("Rangefinder Voltage: ", rangefinder.getVoltage());
        SmartDashboard
                .putNumber("Rangefinder Range (cm): ", rangefinder.getVoltage() * RobotMap.RANGEFINDER_VOLTAGE_RATIO);

    }

    /**
     * Drive the robot straight (based on the speed of the right side)
     * <p>
     * This is activated by pressing {@link OI#straightMode}
     * </p>
     */
    private void straightDrive() {

        if (RobotMap.XBOX_DRIVE_CONTROLLER)
            leftSide = rightSide;
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

    ////////////////////////////////////////////// Drive Sensor Recording //////////////////////////////////////////////

    /**
     * Prints the value of the drive train's right encoder and than resets the encoder
     */
    public void printResetRightEncoder() {

        System.out.println("Right Encoder: " + rightEncoder.get());
        rightEncoder.reset();

    }

    /**
     * Prints the value of the drive train's left encoder and than resets the encoder
     */
    public void printResetLeftEncoder() {

        System.out.println("Left Encoder: " + leftEncoder.get());
        leftEncoder.reset();

    }

    /**
     * Prints the value of the rangefinder
     */
    public void printRangefinder() {

        System.out.println("Range: " + getRangeMM() + " mm");

    }

    /**
     * Gets the range value from the rangefinder in millimeters
     * TODO fix
     *
     * @return Range detected with the rangefinder in millimeters
     */
    private double getRangeMM() {

        return rangefinder.getVoltage() / RobotMap.RANGEFINDER_V5MM;

    }

}

