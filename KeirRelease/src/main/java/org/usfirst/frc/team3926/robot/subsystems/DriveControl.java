package org.usfirst.frc.team3926.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.OI;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.UserDriveTank;

import java.util.Arrays;

/***********************************************************************************************************************
 * Enables driving of the robot with a tank drive scheme. This class contains anything relevant to actuating the drive
 * motors. This includes autonomous handling of the robot.
 * @author William Kluge
 *      <p>
 *      Contact: klugewilliam@gmail.com
 *      </p>
 **********************************************************************************************************************/
public class DriveControl extends Subsystem {

    /** Holds the speed for the right motor */
    private double rightSide = 0;
    /** Holds the speed for the left motor */
    private double leftSide  = 0;
    /** Object to handle actual driving of motors */
    private RobotDrive driveSystem;
    /** Network table for vision processing of the high goal target */
    private NetworkTable highGoalTable = null;
    /** Network table for vision processing of the gear target */
    private NetworkTable gearTable     = null;
    /** Vision processing booleans */
    private boolean moveLeft, moveRight, contoursFound, centered;
    /** Encoder for the robot's left side */
    public Encoder     leftEncoder;
    /** Encoder for the robot's right side */
    public Encoder     rightEncoder;
    /** Rangefinder for autonomous distance finding */
    public AnalogInput rangefinder;

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

        leftEncoder = new Encoder(RobotMap.DRIVE_LEFT_ENCODER_A_CHANNEL, RobotMap.DRIVE_LEFT_ENCODER_B_CHANNEL,
                                  true, CounterBase.EncodingType.k4X);
        leftEncoder.setDistancePerPulse(RobotMap.DRIVE_ENCODER_DISTANCE_PER_PULSE);
        rightEncoder = new Encoder(RobotMap.DRIVE_RIGHT_ENCODER_A_CHANNEL, RobotMap.DRIVE_RIGHT_ENCODER_B_CHANNEL,
                                   false, CounterBase.EncodingType.k4X);
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
     */
    public void initNetworkTables() {

        highGoalTable = NetworkTable.getTable(RobotMap.TABLE_HIGH_GOAL_NAME);
        gearTable = NetworkTable.getTable(RobotMap.TABLE_GEAR_NAME);

    }

    /**
     * Resets the data for the drive system
     */
    public void reset() {

        rightSide = 0;
        leftSide = 0;

    }

    /**
     * Resets the drivetrain encoders
     */
    public void resetEncoders() {

        leftEncoder.reset();
        rightEncoder.reset();
    }

    public String getContourAreas() {

        return Arrays.toString(highGoalTable.getNumberArray(RobotMap.CONTOUR_AREA_KEY, RobotMap.DEFAULT_VALUE));

    }

    //////////////////////////////////////////////// Robot Driving /////////////////////////////////////////////////////

    /**
     * Drives the robot in a tank configuration
     * <p>
     * Note: Input values are inverted here to account for joystick inversions
     * </p>
     *
     * @param rightSpeed Speed to set the right motor
     * @param leftSpeed  Speed to set the left motor
     * @param straight   Whether or not the robot should drive straight
     * @param safe       Whether or not to reduce robot speed
     * @param invert     Whether or not to invert the drive direction of the robot
     */
    public void driveTank(double rightSpeed, double leftSpeed, boolean straight, boolean safe, boolean invert) {

        setSpeed(rightSpeed * -1, leftSpeed * -1, false); //This inverts joystick values

        if (straight)
            straightDrive();

        if (safe)
            safeMode();

        if (invert) {
            double leftSave = leftSide;
            leftSide = -rightSide;
            rightSide = -leftSave;
        }

        SmartDashboard.putBoolean("Straight mode", straight);
        SmartDashboard.putBoolean("Safety mode", safe);
        SmartDashboard.putBoolean("Invert Drive", invert);

        driveSystem.tankDrive(leftSide, rightSide);

    }

    /**
     * Drives the robot in tank configuration
     * <p>
     * Note: Joystick values ARE NOT inverted when using this method. This is intended mostly for driving in autonomous.
     * </p>
     *
     * @param rightSpeed Speed to set the right side of the drive train
     * @param leftSpeed  Speed to set the left side of the drive train
     */
    public void driveTank(double rightSpeed, double leftSpeed) {

        setSpeed(leftSpeed, rightSpeed, true);

    }

    /**
     * Drives the robot towards a target autonomously
     *
     * @param targetGears Set this to true if vision tracking should work to center itself on gears not the high goal
     *                    target, which it will do if this is true
     */
    public void autonomousTank(boolean targetGears) {

        if ((!targetGears && highGoalTable.getNumberArray(RobotMap.CONTOUR_X_KEY, RobotMap.DEFAULT_VALUE).length != 0)
            || (targetGears && gearTable.getNumberArray(RobotMap.CONTOUR_X_KEY, RobotMap.DEFAULT_VALUE).length != 0)) {

            double target;

            if (targetGears) {
                double[] gearTargetCenters = gearTable.getNumberArray(RobotMap.CONTOUR_X_KEY, RobotMap.DEFAULT_VALUE);
                target = ((gearTargetCenters[0] + gearTargetCenters[1]) / 2) +
                         (gearTable.getNumberArray(RobotMap.CONTOUR_WIDTH_KEY, RobotMap.DEFAULT_VALUE)[0] *
                          RobotMap.GEAR_VISION_OFFSET_RATIO);
            } else { //target for the high goal
                target = highGoalTable.getNumberArray(RobotMap.CONTOUR_X_KEY, RobotMap.DEFAULT_VALUE)[0];
            }

            if (target == RobotMap.ILLEGAL_DOUBLE)
                return;

            contoursFound = true;

            if (target > RobotMap.SCREEN_CENTER[0] * (1 + RobotMap.ALLOWABLE_ERROR)) {
                setSpeed(RobotMap.AUTONOMOUS_SPEED, ((target - RobotMap.SCREEN_CENTER[0]) /
                                                     RobotMap.SCREEN_CENTER[0]) * RobotMap.AUTONOMOUS_SPEED, true);
                moveLeft = false;
                moveRight = true;
                centered = false;
            } else if (target < RobotMap.SCREEN_CENTER[0] * (1 - RobotMap.ALLOWABLE_ERROR)) {
                setSpeed((target / RobotMap.SCREEN_CENTER[0]) * RobotMap.AUTONOMOUS_SPEED, RobotMap.AUTONOMOUS_SPEED,
                         true);
                moveRight = false;
                centered = false;
                moveLeft = true;
            } else {
                setSpeed(RobotMap.AUTONOMOUS_SPEED, RobotMap.AUTONOMOUS_SPEED, true);
                moveLeft = false;
                moveRight = false;
                centered = true;
            }

        } else {
            setSpeed(0, 0, true);
            contoursFound = false;
        }

        setSpeed(leftSide, rightSide, false);

        driveSystem.tankDrive(leftSide, rightSide);

        SmartDashboard.putBoolean("Move Left", moveLeft);
        SmartDashboard.putBoolean("Move Right", moveRight);
        SmartDashboard.putBoolean("Centered", centered);
        SmartDashboard.putBoolean("Contours Found", contoursFound);

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
            setSpeed(leftSide * RobotMap.TURNING_SPEED_MULTIPLIER, rightSide * -RobotMap.TURNING_SPEED_MULTIPLIER,
                     true);
        else if (moveRight)
            setSpeed(leftSide * -RobotMap.TURNING_SPEED_MULTIPLIER, rightSide * RobotMap.TURNING_SPEED_MULTIPLIER,
                     true);
        else
            setSpeed(0, 0, true);

        driveSystem.tankDrive(leftSide, rightSide);

    }

    /**
     * Tells if the robot is centered on the vision target
     *
     * @return If both sides of the robot at driving at the same speed (this will only happen after running the
     * autonomous driving commands if the robot is centered)
     */
    public boolean isCentered() {

        return leftSide == RobotMap.AUTONOMOUS_SPEED && rightSide == RobotMap.AUTONOMOUS_SPEED ||
               leftSide == 0 && rightSide == 0;

    }

    /**
     * Finds if the arrays for vision processing are the correct size
     *
     * @param targetGears Whether or not this needs to check if there are gear targets or high goal targets
     * @return If there is a vision target to lock onto to drive towards
     */
    public boolean lostTarget(boolean targetGears) {

        return (targetGears) ? gearTable.getNumberArray(RobotMap.CONTOUR_X_KEY, RobotMap.DEFAULT_VALUE).length == 2 :
               highGoalTable.getNumberArray(RobotMap.CONTOUR_X_KEY, RobotMap.DEFAULT_VALUE).length >= 1;

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
     * Checks if the robot is at or closer than the range that targetVoltage corresponds to
     *
     * @param targetVoltage Voltage given by the rangefinder at a specific range
     * @return If the rangefinder's current voltage is less than or equal to the targetVoltage
     */
    public boolean withinRangefinderVoltage(double targetVoltage) {

        return rangefinder.getVoltage() <= targetVoltage;

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
     * @param autonomous Whether or not his command is being called from an autonomous method (to
     *                   reverse one side's drive
     */
    private void setSpeed(double leftSpeed, double rightSpeed, boolean autonomous) {

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

        if (autonomous)
            //rightSide *= -1; //TODO find out which side this is on

            SmartDashboard.putNumber("Rangefinder Voltage:", rangefinder.getVoltage());
        SmartDashboard.putNumber("Left Rate:", leftEncoder.getRate());
        SmartDashboard.putNumber("Right Rate:", rightEncoder.getRate());

        driveSystem.tankDrive(leftSide, rightSide);

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

        System.out.println("Right Encoder: " + rightEncoder.getDistance());
        rightEncoder.reset();

    }

    /**
     * Prints the value of the drive train's left encoder and than resets the encoder
     */
    public void printResetLeftEncoder() {

        System.out.println("Left Encoder: " + leftEncoder.getDistance());
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

        SmartDashboard.putNumber("Calculated V5mm", rangefinder.getVoltage() / RobotMap.RANGEFINDER_BITS);
        SmartDashboard.putNumber("Used V5mm", RobotMap.RANGEFINDER_V5MM);
        SmartDashboard.putNumber("39.37 times voltage", rangefinder.getVoltage() * 39.37);

        return (rangefinder.getVoltage() / RobotMap.RANGEFINDER_V5MM) * 5;

    }

}

