package org.usfirst.frc.team3926.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    /** Determines if debugging code should be used */
    public final static boolean  DEBUG                 = true;

    /* Motor IDs */
    /** PWM ports for motors */
    public final static int      FRONT_RIGHT_MOTOR_PWM = 0;
    public final static int      BACK_RIGHT_MOTOR_PWM  = 1;
    public final static int      FRONT_LEFT_MOTOR_PWM  = 2;
    public final static int      BACK_LEFT_MOTOR_PWM   = 3;
    /** Motor for testing shooter */
    public final static int      TEST_MOTOR_PWM        = 4;

    /* Button/Joystick Mapping */
    /** XBox controller mapping */
    public final static int      XBOX_LEFT_TRIGGER     = 2;
    public final static int      XBOX_RIGHT_TRIGGER    = 3;
    /** The axis to use for testing the test motor */
    public final static int      TEST_MOTOR_FORWARD    = XBOX_RIGHT_TRIGGER;
    public final static int      TEST_MOTOR_REVERSE    = XBOX_LEFT_TRIGGER;
    /** USB port numbers for joysticks */
    public final static int      RIGHT_STICK_PORT      = 0;
    public final static int      LEFT_STICK_PORT       = 1;
    /** Whether or not to use an XBox controller configuration for driving instead of tank drive */
    public final static boolean  XBOX_CONTROLLER       = true;

    /* Vision Tracking */
    /** Illegal array to return from NetworkVisionProcessing if an value could not be gotten */
    public final static double[] ILLEGAL_VALUE         = {-404, -404};
    /** Illegal double to return from NetworkVisionProcessing if a value could not be gotten */
    public final static double   ILLEGAL_DOUBLE        = -404;
    /** Default value to use in when getting values from NetworkTables */
    public final static double[] DEFAULT_VALUE         = new double[0];
    /** Map key for the speed of the robot */
    public final static String   SPEED_KEY             = "speed";
    /** Map key for center x of a contour */
    public final static String   CONTOUR_X_KEY         = "contourX";
    /** Map key for center y of a contour */
    public final static String   CONTOUR_Y_KEY         = "contourY";
    /** The max speed for the robot to travel during autonomous */
    public final static double   AUTONOMOUS_SPEED      = 1;
    /** The number to multiply times the speed of the robot when the driver enables saftey mode */
    public final static double   DRIVE_SAFTEY_FACTOR   = 0.50;

    /* Variables for configuring command generation */
    /** Track the delays in human commands */
    public final static boolean  TRACK_TIME_DELAYS     = false;
    /** Cut the time delays (requires @see TRACK_TIME_DELAYS to be true) */
    public final static boolean  CUT_TIME_DELAYS       = false;
    /** The amount to cut time delays (requires @see CUT_TIME_DELAYS to be true) */
    public final static double   CUT_FACTOR            = 0.50;
    /** Use the values from joystick inputs instead of encoder values */
    public final static boolean  USE_JOYSTICK_VALUES   = false;

}
