package org.usfirst.frc.team3926.robot;

import org.usfirst.frc.team3926.subsystems.DriveControl;
import org.usfirst.frc.team3926.subsystems.NetworkVisionProcessing;

/***********************************************************************************************************************
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * <p>
 * All options with the prefix XBOX are only used if {@link #XBOX_DRIVE_CONTROLLER} is true. If that is false, ignore
 * them
 * </p>
 **********************************************************************************************************************/
public class RobotMap {

    ///////////////////////////////////////////// Enable/Disable Features //////////////////////////////////////////////
    /** Use code specifically made for debugging the robot */
    public final static boolean  DEBUG                              = true;
    /** Use an XBox controller for {@link OI#driverPrimaryStick} */
    public final static boolean  XBOX_DRIVE_CONTROLLER              = true;
    /** Use a speed buffer to prevent sudden jumps or drops in speed TODO working, needs improvement */
    public final static boolean  USE_SPEED_BUFFER                   = false;
    /** Filter contours based on their features. This uses {@link NetworkVisionProcessing#smartFilterContours(int)} */
    public final static boolean  USE_SMART_FILTER                   = true;
    /** Use CANTalons for the drive base instead of PWM Talons */
    public final static boolean  USE_CAN_TALON                      = true;
    /** Invert motor direction for the drive train's right side */
    public final static boolean  INVERT_RIGHT_DRIVE_MOTOR_DIRECTION = true;
    /** Invert motor direction for the drive train's left side */
    public final static boolean  INVERT_LEFT_DRIVE_MOTOR_DIRECTION  = true;

    /////////////////////////////////////////////////// Motor PWM IDs //////////////////////////////////////////////////
    /** PWM port for front right motor */
    public final static int      FRONT_RIGHT_MOTOR_PWM              = 0;
    /** PWM port for back right motor */
    public final static int      BACK_RIGHT_MOTOR_PWM               = 1;
    /** PWM port for front left motor */
    public final static int      FRONT_LEFT_MOTOR_PWM               = 2;
    /** PWM port for back left motor */
    public final static int      BACK_LEFT_MOTOR_PWM                = 3;

    /////////////////////////////////////////////////// Motor CAN IDs //////////////////////////////////////////////////
    /** CAN ID for front right motor */
    public final static int      FRONT_RIGHT_MOTOR_CAN              = 1;
    /** CAN ID for back right motor */
    public final static int      BACK_RIGHT_MOTOR_CAN               = 4;
    /** CAN ID for front left motor */
    public final static int      FRONT_LEFT_MOTOR_CAN               = 3;
    /** CAN ID for back left motor */
    public final static int      BACK_LEFT_MOTOR_CAN                = 2;

    ///////////////////////////////////////// XBox configuration (for driving) /////////////////////////////////////////
    /** USB port number for the XBOX controller */
    public final static int      XBOX_PORT                          = 0;
    /** Axis ID to use for the left speed in tank drive */
    public final static int      XBOX_LEFT_SPEED_AXIS               = 1;
    /** Axis ID to use for the right speed in tank drive */
    public final static int      XBOX_RIGHT_SPEED_AXIS              = 5;
    /** Button ID to enter safety mode */
    public final static int      XBOX_SAFTEY_MODE_BUTTON            = 1;
    /** Button ID to enter straight mode */
    public final static int      XBOX_STRAIGHT_MODE_BUTTON          = 2;
    /** Button ID to signify that an action taken by the robot in {@link DriveControl#autonomousTank()} is incorrect */
    public final static int      XBOX_CONTOUR_ERROR_BUTTON          = 5;
    /** Button ID to center the robot on the vision target */
    public final static int      XBOX_CENTER_BUTTON                 = 4;
    /** Button ID to drive towards the center of the vision target */
    public final static int      XBOX_DRIVE_TO_CENTER_BUTTON        = 3;

    ///////////////////////////////////// Joystick Configuration (for tank drive) //////////////////////////////////////
    ///// USB Port Configuration /////
    /** USB port number for right joystick */
    public final static int      RIGHT_STICK_PORT                   = 0;
    /** USB port number for left joystick */
    public final static int      LEFT_STICK_PORT                    = 1;
    ///// Configuration for Driver's Primary Stick /////
    /** Button ID on {@link OI#driverPrimaryStick} to enter safety mode */
    public final static int      SAFTEY_MODE_BUTTON                 = 1;
    /** Button ID on {@link OI#driverPrimaryStick} to signify that an autonomous action is incorrect */
    public final static int      CONTOUR_ERROR_BUTTON               = 5;
    /** Button ID on {@link OI#driverPrimaryStick} to center the robot on the vision target */
    public final static int      CENTER_BUTTON                      = 4;
    /** Button ID on {@link OI#driverPrimaryStick} to drive towards the center of the vision target */
    public final static int      DRIVE_TO_CENTER_BUTTON             = 3;
    ///// Configuration for Driver's Secondary Stick /////
    /** Button ID on {@link OI#driverSecondaryStick} to enter straight mode */
    public final static int      STRAIGHT_MODE_BUTTON               = 1;

    //////////////////////////////////////////////////// Vision Tracking ///////////////////////////////////////////////
    ///// Table Names and Keys /////
    /** Name of the network table for NetworkVisionProcessing to read from */
    public final static String   TABLE_HIGH_GOAL_NAME               = "vision/high_goal";
    /** Map key for the speed of the right side of the robot */
    public final static String   SPEED_RIGHT_KEY                    = "rightSpeed";
    /** Map key for the speed of the left side of the robot */
    public final static String   SPEED_LEFT_KEY                     = "leftSpeed";
    /** Map key for center x of a contour */
    public final static String   CONTOUR_X_KEY                      = "x";
    /** Map key for center y of a contour */
    public final static String   CONTOUR_Y_KEY                      = "y";
    /** Map key for contour height */
    public final static String   CONTOUR_HEIGHT_KEY                 = "height";
    /** Map key for contour width */
    public final static String   CONTOUR_WIDTH_KEY                  = "width";
    /** Map key for SmartFilter pass status */
    public final static String   SMARTFILTER_PASS_KEY               = "smartFilter";
    ///// Speed buffer for vision tracking /////
    /** Amount of previous speed values to store for the speed buffer */
    public final static int      SPEED_BUFFER_SIZE                  = 5;
    /** Allowable difference in buffered values. If the difference is greater than this the speed is changed */
    public final static double   MAX_BUFFER_DIFFERENCE              = 0.1;
    /** Time (in milliseconds) between buffer updates */
    public final static int      BUFFER_UPDATE_TIME                 = 100;
    /** Value to use for accelerating towards the target speed if {@link #BUFFER_ACCELERATION} is true */
    public final static double   BUFFER_ACCELERATION_AMOUNT         = MAX_BUFFER_DIFFERENCE;
    ///// Image size configuration /////
    /** Size of the vision tracking image's X axis */
    public final static int      IMAGE_X                            = 160;
    /** Size of the vision tracking image's Y axis */
    public final static int      IMAGE_Y                            = 120;
    /** Center point on the screen */
    public final static int[]    SCREEN_CENTER                      = {IMAGE_X / 2, IMAGE_Y / 2};
    ///// Smart Filter Configuration /////
    /** How off the value is allowed to be from what it should be for vision tracking algorithms */
    public final static double   ALLOWABLE_ERROR                    = 0.05;
    /** Enable/Disable relative area check (a contour must have an area double/half the size of another contour) */
    public final static boolean  USE_RELATIVE_AREA                  = true;
    /** This option is mainly for debugging */
    public final static boolean  USE_MAX_CONTOUR_AREA               = true;
    /** Maximum area of a contour NOTE: This can be done with GRIP. I have it here for convenience */
    public final static double   MAX_CONTOUR_AREA                   =14000;
    /** Whether or not to determine if a contour is valid based on its position relative to other contours */
    public final static boolean  USE_RELATIVE_POSITION_CHECK        = true;
    /** Whether or not to find the BEST contours, not just contours that match the criteria */
    public final static boolean CHECK_FOR_BEST_CONTOUR = true;
    /** Y axis offset between vision targets for the high goal */
    public final static double   HIGH_GOAL_Y_OFFSET_RATIO           = 1;
    /** X axis offset between vision targets for the high goal */
    public final static double   HIGH_GOAL_X_OFFSET_RATIO           = 0;
    /** Y axis offset between vision targets for gear placement */
    public final static double   GEAR_TARGET_Y_OFFSET_RATIO         = 0;
    /** X axis offset between vision targets for gear placement */
    public final static double   GEAR_TARGET_X_OFFSET_RATIO         = 0;
    ///// Speed Buffer Configuration /////
    /** Accelerate/decelerate if a speed is outside of the buffer range (requires {@link #USE_SPEED_BUFFER} be true */
    public final static boolean  BUFFER_ACCELERATION                = false;

    /////////////////////////////////////////////// Drive Configuration ////////////////////////////////////////////////
    /** Max speed for the robot to travel during autonomous */
    public final static double   AUTONOMOUS_SPEED                   = 0.50;
    /** TODO Maximum difference between the two sides speeds when driving autonomously (set this to 2 to disable) */
    public final static double   MAX_AUTONOMOUS_SPEED_DIFFERENCE    = 0.1;
    /** Number to multiply times the speed of the robot when the driver enables saftey mode */
    public final static double   DRIVE_SAFETY_FACTOR                = 0.50;

    /////////////////////////////////// Variables for configuring command generation ///////////////////////////////////
    /** Track the delays in human commands */
    public final static boolean  TRACK_TIME_DELAYS                  = false;
    /** Cut the time delays (requires @see TRACK_TIME_DELAYS to be true) */
    public final static boolean  CUT_TIME_DELAYS                    = false;
    /** The amount to cut time delays (requires @see CUT_TIME_DELAYS to be true) */
    public final static double   CUT_FACTOR                         = 0.50;
    /** Use the values from joystick inputs instead of encoder values */
    public final static boolean  USE_JOYSTICK_VALUES                = false;

    ////////////////////////////////////////// Code Quality of Life Variables //////////////////////////////////////////
    /** Index to always use for accessing the left speed in an array */
    public final static int      LEFT_INDEX                         = 0;
    /** Index to always use for accessing the right speed in an array */
    public final static int      RIGHT_INDEX                        = 1;
    /** Default value to use in when getting values from NetworkTables */
    public final static double[] DEFAULT_VALUE                      = new double[0];

    //////////////////////////////////////// Illegal Values (to signify errors) ////////////////////////////////////////
    /** Illegal array to return from NetworkVisionProcessing if an value could not be gotten */
    public final static double[] ILLEGAL_VALUE                      = {-404, -404};
    /** Illegal double to return from NetworkVisionProcessing if a value could not be gotten */
    public final static double   ILLEGAL_DOUBLE                     = -404;
    /** Illegal int to return if data could not be validated */
    public final static int      ILLEGAL_INT                        = -404;

}
