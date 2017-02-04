package org.usfirst.frc.team3926.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    /////////////////////////////////////////////Other motors//////////////////////////////////////////////////////////
    /** motor for shooting */
    public static int     SHOOTING_MOTOR           = 1;
    /** motor for climbing */
    public static int     CLIMBING_MOTOR           = 8;
    /////////////////////////////////////////////Tank drive////////////////////////////////////////////////////////////
    /** right joystick */
    public static int     RIGHT_STICK              = 0;
    /** left joystick */
    public static int     LEFT_STICK               = 1;
    /** front right motor */
    public static int     FR_MOTOR                 = 2;
    /** front left motor */
    public static int     FL_MOTOR                 = 3;
    /** back right motor */
    public static int     BR_MOTOR                 = 4;
    /** back left motor */
    public static int     BL_MOTOR                 = 5;
    /////////////////////////////////////////////Sensors////////////////////////////////////////////////////////////
    /** encoder port A */
    public static int     A_CHANNEL_ENC            = 6;
    /** encoder port B */
    public static int     B_CHANNEL_ENC            = 7;
    /** speed wanted for encoder */
    public static int     DESRIRED_RPM             = 200;
    /** amount added to speed for encoder */
    public static double  ADDED_AMOUNT             = .01;
    /** limit switch for climbing */
    public static int     CLIMBING_LIMIT_SWITCH    = 9;
    /////////////////////////////////////////////Vision tracking///////////////////////////////////////////////////////
    /** contour X key */
    public static String  X_KEY                    = "x";
    /** contour Y key */
    public static String  Y_KEY                    = "y";
    /** network table key */
    public static String  NETWORK_TABLE_KEY        = "GRIP";
    /** contour width key */
    public static String  WIDTH_KEY                = "width";
    /** contour height key */
    public static String  HIGHT_KEY                = "hight";
    /** screen X coordinate */
    public static int     X_SCREEN                 = 60;
    /** screen Y coordinate */
    public static int     Y_SCREEN                 = 30;
    /** X center of the screen */
    public static double  CENTER_OF_SCREEN_X       = X_SCREEN / 2;
    /** Y center of the screen */
    public static double  CENTER_OF_SCREEN_Y       = Y_SCREEN / 2;
    /** set speed for turning with vision tracking */
    public static double  VISION_DRIVING_SET_SPEED = 1;
    /** turns debugging on or off */
    public static boolean DEBUGGING                = true;
    /** illegal return value (for vision tracking) */
    public static int     ILLEGAL_INT              = -1000000000;
    /** amount vision tracking can be off */
    public static double  ALLOWABLE_ERROR          = .5;
    /** speed of climbing motor */
    public static double  CLIMBING_MOTOR_SPEED     = 1;
    /////////////////////////////////////////////PID encoder///////////////////////////////////////////////////////
    /** speed of test motor */
    public static double  SPEED                      = 1;
    /** encoder A channel */
    public static int     A_CHANNEL                  = 0;
    /** encoder B channel */
    public static int     B_CHANNEL                  = 1;
    /** encoder reverse direction */
    public static boolean REVERSE_DIRECTION          = false;
    /** test motor */
    public static int     TALON_CHANNEL              = 2;
    /** P value */
    public static double  P                          = .1;
    /** I value */
    public static double  I                          = 0;
    /** D value */
    public static double  D                          = 0;
    /** name for PID loop */
    public static String  NAME                       = "encoder";
    /** encoder distance per pulse */
    public static int     ENCODER_DISTANCE_PER_PULSE = 1;
}
