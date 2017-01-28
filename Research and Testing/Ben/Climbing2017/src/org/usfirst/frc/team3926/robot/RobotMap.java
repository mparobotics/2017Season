package org.usfirst.frc.team3926.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    /** ints that store the port number of each joystick */
    public static int RIGHT_STICK_PORT = 0;
    public static int LEFT_STICK_PORT = 1;

    /** can ports for the drivesystem talons */
    public static int TALON_BR_PORT = 0;
    public static int TALON_FR_PORT = 1;
    public static int TALON_BL_PORT = 2;
    public static int TALON_FL_PORT = 3;

    public static String NETWORK_TABLE_KEY = "xValue";

    public static double MAX_VISION_TRACKING_SPEED = 1;

    /**
     * analog port for gyro
     */
    public static int GYRO_PORT = 1;

    public static int VISION_MAX_HEIGHT = 2;
    public static int VISION_MAX_LENGTH = 6;
    public static int VISION_SCREEN_CENTER = VISION_MAX_LENGTH/2;

    /**
     * the default speed for the start of the deceleration function
     */
    public static double DEFAULT_DECELERATION_SPEED = 0.5;

    /** sets the increment which the motor setting will be changed by to achieve it in shooterSpeedIncrement */
    public static double SHOOTER_SPEED_INCREMENT = 0.1;

    /** sets the ideal RPM for the shooter in shooterRPMTarget */
    public static int SHOOTER_RPM_TARGET = 5;

    /** sets the ideal RPM for the climber in climberRPMTarget */
    public static double CLIMBER_SPEED_INCREMENT = 0.1;
    /** and the increment which the motor setting will be changed by to achieve it in climberSpeedIncrement */
    public static int CLIMBER_RPM_TARGET = 5;
    /** can port for the climber */
    public static int CLIMBER_PORT = 1;

    /** sets an increment which can adjust the speed of the robot so it it does not speed up to fast */
    public static double DRIVE_FORWARD_SPEED_INCREMENT = 0.1;
    /** can Port for shooter */
    public static int SHOOTER_PORT = 2;


}
