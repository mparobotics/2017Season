package org.usfirst.frc.team3926.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    /** PWM ports for motors */
    public static int FRONT_RIGHT_MOTOR_PWM = 0,
                      BACK_RIGHT_MOTOR_PWM  = 1,
                      FRONT_LEFT_MOTOR_PWM  = 2,
                      BACK_LEFT_MOTOR_PWM   = 3;

    /** Motor for testing shooter */
    public static int TEST_MOTOR_PWM = 4;

    /** XBox controller mapping */
    public static int XBOX_LEFT_TRIGGER = 2,
                      XBOX_RIGHT_TRIGGER = 3;

    /** The axis to use for testing the test motor */
    public static int TEST_MOTOR_FORWARD = XBOX_RIGHT_TRIGGER,
                      TEST_MOTOR_REVERSE = XBOX_LEFT_TRIGGER;

    /** The number to multiply times the speed of the robot when the driver enables saftey mode */
    public static double DRIVE_SAFTEY_FACTOR = 0.50;

    /** USB port numbers for joysticks */
    public static int RIGHT_STICK_PORT = 0,
                      LEFT_STICK_PORT  = 1;

    /** The illegal array to return from NetworkVisionProcessing if an value could not be gotten */
    public static double[] ILLEGAL_VALUE = {-404, -404};
    /** The illegal double to return from NetworkVisionProcessing if a value could not be gotten */
    public static double ILLEGAL_DOUBLE = -1111;
    /** The default value to use in  */
    public static double[] DEFAULT_VALUE = new double[0];

    /** Whether or not to use an XBox controller configuration for driving instead of tank drive */
    public static boolean XBOX_CONTROLLER = true;

    /** The max speed for the robot to travel during autonomous */
    public static double AUTONOMOUS_SPEED = 1;

    /** The amount of time (in milliseconds) to wait and make sure a vision tracking target has been lost/fond before
     * starting/stopping the robot (the more precise the vision algorithm the smaller the number)  */
    public static double VISION_TRACKING_CHECK_DELAY = 50;

    /* Variables for configuring command generation */
    /** Track the delays in human commands */
    public static boolean TRACK_TIME_DELAYS = false;
    /** Cut the time delays (requires @see TRACK_TIME_DELAYS to be true) */
    public static boolean CUT_TIME_DELAYS   = false;
    /** The amount to cut time delays (requires @see CUT_TIME_DELAYS to be true) */
    public static double CUT_FACTOR = 0.50;
    /** Use the values from joystick inputs instead of encoder values */
    public static boolean USE_JOYSTICK_VALUES = false;


}
