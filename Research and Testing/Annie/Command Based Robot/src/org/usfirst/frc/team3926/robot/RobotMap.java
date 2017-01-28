package org.usfirst.frc.team3926.robot;

import java.beans.Encoder;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    public static int    SHOOTING_MOTOR           = 1;
    public static int    CLIMBING_MOTOR           = 8;
    /**right joystick*/
    public static int    RIGHT_STICK              = 0;
    /**left joystick*/
    public static int    LEFT_STICK               = 1;
    /**motors for tank drive*/
    public static int    FR_MOTOR                 = 2;
    public static int    FL_MOTOR                 = 3;
    public static int    BR_MOTOR                 = 4;
    public static int    BL_MOTOR                 = 5;
    /**encoder ports*/
    public static int    A_CHANNEL_ENC            = 6;
    public static int    B_CHANNEL_ENC            = 7;
    /**speed wanted for encoder*/
    public static int    DESRIRED_RPM             = 200;
    /**amount added to speed for encoder*/
    public static double ADDED_AMOUNT             = .01;
    /**limit switch for climbing*/
    public static int    CLIMBING_LIMIT_SWITCH    = 9;
    /**contour keys for vision tracking*/
    public static String X_KEY                    = "x";
    public static String Y_KEY                    = "y";
    public static String NETWORK_TABLE_KEY        = "GRIP";
    public static String WIDTH_KEY                = "width";
    public static String HIGHT_KEY                = "hight";
    /**screen coordinates*/
    public static int    X_SCREEN                 = 60;
    public static int    Y_SCREEN                 = 30;
    public static int    CENTER_OF_SCREEN_X       = X_SCREEN / 2;
    public static int    CENTER_OF_SCREEN_Y       = Y_SCREEN / 2;
    /**set speed for turning with vision tracking*/
    public static double VISION_DRIVING_SET_SPEED = 1;
}
