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

    /** The number to multiply times the speed of the robot when the driver enables saftey mode */
    public static double DRIVE_SAFTEY_FACTOR = 0.50;

    /** USB port numbers for joysticks */
    public static int RIGHT_STICK_PORT = 0,
                      LEFT_STICK_PORT  = 1;

    /** Whether or not to use an XBox controller configuration for driving instead of tank drive */
    public static boolean XBOX_CONTROLLER = true;

}
