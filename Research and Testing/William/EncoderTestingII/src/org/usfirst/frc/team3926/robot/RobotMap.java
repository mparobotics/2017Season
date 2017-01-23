package org.usfirst.frc.team3926.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    /** PWM ports for motors */
    public final static int     FRONT_RIGHT_MOTOR_PWM         = 0;
    public final static int     BACK_RIGHT_MOTOR_PWM          = 1;
    public final static int     FRONT_LEFT_MOTOR_PWM          = 2;
    public final static int     BACK_LEFT_MOTOR_PWM           = 3;
    public final static int     TEST_MOTOR_PWM                = 4;
    /** The number to multiply times the speed of the robot when the driver enables saftey mode */
    public final static double  DRIVE_SAFTEY_FACTOR           = 0.50;
    /** USB port numbers for joysticks */
    public final static int     RIGHT_STICK_PORT              = 0;
    public final static int     LEFT_STICK_PORT               = 1;
    /** Whether or not to use an XBox controller configuration for driving instead of tank drive */
    public final static boolean XBOX_CONTROLLER               = true;

    /* Encoder Values */
    /** Speed to set the motor */
    public final static double  MOTOR_SPEED                   = 0.1;
    /** A channel for the encoder */
    public final static int     ENCODER_A_CHANNEL             = 0;
    /** B channel for the encoder */
    public final static int     ENCODER_B_CHANNEL             = 1;
    /** Target rate for the motor to achieve */
    public final static double  TARGET_RATE                   = 200;
    /** Amount of times that an encoder pules in one revolution (find on encoder data-sheet) */
    public final static int     ENCODER_PULSES_PER_REVOLUTION = 2048;
    /** Diameter of the wheel that the encoder is recording for */
    public final static int     WHEEL_DIAMETER                = 4;
    /** Name of the PID subsystem for the encoder */
    public final static String  ENCODER_PID_NAME              = "PIDEncoderTesting";
    /** Proportional value for encoder PID loop */
    public final static double  PROPORTIONAL_VALUE            = 0.05;
    /** Integral value for the encoder PID loop */
    public final static double  INTEGRAL_VALUE                = 0;
    /** Derivative value for the encoder PID loop */
    public final static double  DERIVATIVE_VALUE              = 0;
    /** Absolute Tolerance for PID loop */
    public final static double  ABSOLUTE_TOLERANCE            = 0.02;

}
