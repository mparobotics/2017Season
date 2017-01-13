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

    /** Whether or not to use an XBox controller configuration for driving instead of tank drive */
    public static boolean XBOX_CONTROLLER = true;

    public static double AUTONOMOUS_SPEED = 0.1;

}
