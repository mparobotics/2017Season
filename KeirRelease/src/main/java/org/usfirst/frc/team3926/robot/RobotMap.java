package org.usfirst.frc.team3926.robot;

/***********************************************************************************************************************
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * @author William Kluge
 *      <p>
 *      Contact: klugewilliam@gmail.com
 *      </p>
 *      <p>
 *      All options with the prefix XBOX are only used if {@link #XBOX_DRIVE_CONTROLLER} is true. If that is false,
 *      ignore them
 *      </p>
 **********************************************************************************************************************/
@SuppressWarnings("WeakerAccess")
public class RobotMap {

    ///////////////////////////////////////////// Enable/Disable Features //////////////////////////////////////////////
    /** Use code specifically made for debugging the robot */
    public final static boolean  DEBUG                              = true;
    /** Use a speed buffer to prevent sudden jumps or drops in speed TODO working, needs improvement */
    public final static boolean  USE_SPEED_BUFFER                   = false;
    /** Filter contours based on their features */
    public final static boolean  USE_SMART_FILTER                   = true;

    ////////////////////////////////////////////// Shooter Configuration ///////////////////////////////////////////////
    ///// Shooter Motor Configuration /////
    /** Enable/Disable using CAN based talons for the shooter */
    public final static boolean  SHOOTER_USE_CAN_TALON              = true;
    /** CAN ID for the the shooter's motor controller (used if {@link #SHOOTER_USE_CAN_TALON} is true) */
    public final static int      SHOOTER_CAN_ID                     = 100;
    /** PWM port for the shooter's motor controller (used if {@link #SHOOTER_USE_CAN_TALON} is false) */
    public final static int      SHOOTER_PWM_ID                     = 4;
    /** DIO port for the shooter's encoder's A channel */
    public final static int      SHOOTER_ENCODER_A_CHANNEL          = 0;
    /** DIO port for the shooter's encoder's B channel */
    public final static int      SHOOTER_ENCODER_B_CHANNEL          = 1;
    /** Setpoint for the shooter's PID loop */
    public final static double   SHOOTER_SETPOINT                   = 200; //TODO figure out what this should be
    /** Proportional multiplier for the shooter's PID loop */
    public final static double   SHOOTER_PROPORTIONAL               = 0.001; //TODO figure out what this should be
    /** Integral multiplier for the shooter's PID loop */
    public final static double   SHOOTER_INTEGRAL                   = 0; //TODO figure out what this should be
    /** Derivative multiplier for the shooter's */
    public final static double   SHOOTER_DERIVATIVE                 = 0; //TODO figure out what this should be
    /** Absolute tolerance (allowable error from set point) from the shooter's set point */
    public final static double   SHOOTER_ABSOLUTE_TOLERANCE         = 0; //TODO figure out what this should be
    ///// Ball Agitator Motor Configuration /////
    /** Enable/Disable using CAN based talons for the shooter's ball agitator */
    public final static boolean  AGITATOR_USE_CAN_TALON             = true;
    /** CAN ID for the ball agitator's motor controller (used if {@link #AGITATOR_USE_CAN_TALON} is true) */
    public final static int      AGITATOR_CAN_ID                    = 101;
    /** PWM ID for the ball agitator's motor controller (used if {@link #AGITATOR_USE_CAN_TALON} is false) */
    public final static int      AGITATOR_PWM_PORT                  = 5;
    /** DIO port for the agitator's encoder's A channel */
    public final static int      AGITATOR_ENCODER_A_CHANNEL         = 2;
    /** DIO port for the agitator's encoder's B channel */
    public final static int      AGITATOR_ENCODER_B_CHANNEL         = 3;
    /** Setpoint for the agitator's PID loop when it is feeding to the ball loader */
    public final static double   AGITATOR_FEED_SETPOINT             = 200; //TODO figure out what this should be
    /** Setpoint for the agitator's PID loop when it is sitting idle */
    public final static double   AGITATOR_IDLE_SETPOINT             = 100;
    /** Proportional multiplier for the agitator's PID loop */
    public final static double   AGITATOR_PROPORTIONAL              = 0.001; //TODO figure out what this should be
    /** Integral multiplier for the agitator's PID loop */
    public final static double   AGITATOR_INTEGRAL                  = 0; //TODO figure out what this should be
    /** Derivative multiplier for the agitator's */
    public final static double   AGITATOR_DERIVATIVE                = 0; //TODO figure out what this should be
    /** Absolute tolerance (allowable error from set point) from the agitator's set point */
    public final static double   AGITATOR_ABSOLUTE_TOLERANCE        = 0; //TODO figure out what this should be

    /////////////////////////////////////////////// Climber Configuration //////////////////////////////////////////////
    /** Enable/Disable using CAN based talons for the climbing mechanism */
    public final static boolean  CLIMBER_USE_CAN_TALON              = true;
    /** CAN ID for the climbing system's motor controller (used if {@link #CLIMBER_USE_CAN_TALON} is true) */
    public final static int      CLIMBER_CAN_ID                     = 103;
    /** CAN ID for the climbing system's second motor controller (used if {@link #CLIMBER_USE_CAN_TALON} is true) */
    public final static int      CLIMBER_SECOND_CAN_ID              = 104;
    /** PWM port for the climbing system's motor controller (used if {@link #CLIMBER_USE_CAN_TALON} is false) */
    public final static int      CLIMBER_PWM_PORT                   = 7;
    /** PWM port for the climbing system's motor controller (used if {@link #CLIMBER_USE_CAN_TALON} is false) */
    public final static int      CLIMBER_SECOND_PWM_PORT            = 8;
    /** DIO port for the climbing system's limit switch */
    public final static int      CLIMBER_LIMIT_SWITCH_PORT          = 4;
    /** Speed to climb at */
    public final static double   CLIMBER_SPEED                      = 1;

    /////////////////////////////////////////// Ball Collection Configuration //////////////////////////////////////////
    /** Enable/Disable using CAN based talons for the ball feeding mechanism */
    public final static boolean  BALL_COLLECTION_USE_CAN_TALON      = true;
    /** CAN ID for ball collection system's motor controller (used if {@link #BALL_COLLECTION_USE_CAN_TALON} is true) */
    public final static int      BALL_COLLECTION_CAN_ID             = 102;
    /** PWM port for ball collection motor controlled (used if {@link #BALL_COLLECTION_USE_CAN_TALON} is true */
    public final static int      BALL_COLLECTION_PWM_PORT           = 6;
    /** Speed to set the ball collector motor to */
    public final static double   BALL_COLLECTION_SPEED              = 1;

    //////////////////////////////////////// Gear Placement Configuration //////////////////////////////////////////////
    /** Enable/Disable using CAN based talons for the gear placement mechanism's motor */
    public final static boolean GEAR_PLACEMENT_USE_CAN_TALON       = true;
    /** CAN ID for the gear placement system's motor (used if {@link #GEAR_PLACEMENT_USE_CAN_TALON} is true */
    public final static int     GEAR_PLACEMENT_CAN_ID              = 121;
    /** PWM port for the gear placement system (used if {@link #GEAR_PLACEMENT_USE_CAN_TALON} is false) */
    public final static int     GEAR_PLACEMENT_PWM_PORT            = 9;
    /** Speed to set the gear placement motor to */
    public final static double  GEAR_PLACEMENT_SPEED               = 0.5;

    ///////////////////////////////////////////// Rangefinder Configuration ////////////////////////////////////////////
    /** DIO port for the rangefinder's echo pulse output */
    public final static int     RANGEFINDER_ECHO_PULSE_PORT        = 8;
    /** DIO port for the rangefinder's trigger pulse input */
    public final static int     RANGEFINDER_TRIGGER_PULSE_PORT     = 9;
    /** Whether or not to use millimeters for the rangefinder instead of inches */
    public final static boolean RANGEFINDER_USE_MILLIMETERS        = false;

    /////////////////////////////////////////////// Drive Configuration ////////////////////////////////////////////////
    /** Use CANTalons for the drive base instead of PWM Talons */
    public final static boolean DRIVE_USE_CAN_TALON                = true;
    /** Invert motor direction for the drive train's right side */
    public final static boolean INVERT_RIGHT_DRIVE_MOTOR_DIRECTION = true;
    /** Invert motor direction for the drive train's left side */
    public final static boolean INVERT_LEFT_DRIVE_MOTOR_DIRECTION  = true;
    /** Number to multiply times the speed of the robot when the driver enables saftey mode */
    public final static double   DRIVE_SAFETY_FACTOR                = 0.50;
    ///// Encoder Values /////
    /** DIO port for the drive train's right encoder A channel */
    public final static int      DRIVE_RIGHT_ENCODER_A_CHANNEL      = 5;
    /** DIO port for the drive train's right encoder B channel */
    public final static int      DRIVE_RIGHT_ENCODER_B_CHANNEL      = 6;
    /** DIO port for the drive train's left encoder A channel */
    public final static int      DRIVE_LEFT_ENCODER_A_CHANNEL       = 7;
    /** DIO port for the drive train's left encoder B channel */
    public final static int      DRIVE_LEFT_ENCODER_B_CHANNEL       = 8;
    ///// Motor CAN IDs /////
    /** CAN ID for front right motor */
    public final static int      FRONT_RIGHT_MOTOR_CAN              = 1;
    /** CAN ID for back right motor */
    public final static int      BACK_RIGHT_MOTOR_CAN               = 4;
    /** CAN ID for front left motor */
    public final static int      FRONT_LEFT_MOTOR_CAN               = 3;
    /** CAN ID for back left motor */
    public final static int      BACK_LEFT_MOTOR_CAN                = 2;
    ///// Motor PWM IDs /////
    /** PWM port for front right motor */
    public final static int      FRONT_RIGHT_MOTOR_PWM              = 0;
    /** PWM port for back right motor */
    public final static int      BACK_RIGHT_MOTOR_PWM               = 1;
    /** PWM port for front left motor */
    public final static int      FRONT_LEFT_MOTOR_PWM               = 2;
    /** PWM port for back left motor */
    public final static int      BACK_LEFT_MOTOR_PWM                = 3;
    ///// Autonomous Configuration /////
    /** Max speed for the robot to travel during autonomous */
    public final static double   AUTONOMOUS_SPEED                   = 1;
    /** TODO Maximum difference between the two sides speeds when driving autonomously (set this to 2 to disable) */
    public final static double   MAX_AUTONOMOUS_SPEED_DIFFERENCE    = 0.1;

    ///////////////////////////////////////// Drive Joystick Configuration /////////////////////////////////////////////
    /** Use an XBox controller for {@link OI#driverPrimaryStick} */
    public final static boolean  XBOX_DRIVE_CONTROLLER              = true;
    ///// XBox configuration (for driving) /////
    /** USB port number for the XBOX controller */
    public final static int      XBOX_PORT                          = 0;
    /** Axis ID to use for the left speed in tank drive */
    public final static int      XBOX_LEFT_SPEED_AXIS               = 1; //(Left joystick y)
    /** Axis ID to use for the right speed in tank drive */
    public final static int      XBOX_RIGHT_SPEED_AXIS              = 5; //(Right joystick y)
    /** Button ID to enter safety mode */
    public final static int      XBOX_SAFTEY_MODE_BUTTON            = 1; //(A)
    /** Button ID to enter straight mode */
    public final static int      XBOX_STRAIGHT_MODE_BUTTON          = 2; //(B)
    /** Button ID to signify that an action taken by the robot in (removed) is incorrect */
    public final static int      XBOX_CONTOUR_ERROR_BUTTON          = 10; //(Left Stick Click)
    /** Button ID to center the robot on the vision target */
    public final static int      XBOX_CENTER_ON_HIGH_GOAL_BUTTON    = 4; //(X)
    /** Button ID to drive towards the center of the vision target */
    public final static int      XBOX_DRIVE_TO_HIGH_GOAL_BUTTON     = 3; //(Y)
    /** Button ID to drive towards the center of the gear's vision target */
    public final static int      XBOX_CENTER_ON_GEAR_BUTTON         = 6; //(Right Bumper)
    /** Button ID to center the robot on the gear's vision target */
    public final static int      XBOX_DRIVE_TO_GEAR_BUTTON          = 5; //(Left Bumper)
    /** Button ID to turn on the shooter */
    public final static int      XBOX_SHOOT_BUTTON                  = 7; //(Back)
    /** Button ID to activate the climber */
    public final static int      XBOX_CLIMB_BUTTON                  = 9; //(Start)
    /** Button ID to collect balls */
    public final static int      XBOX_COLLECT_BUTTON                = 11; //(Right Stick Click)

    ///// Joystick Configuration (for tank drive) /////
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
    public final static int      CENTER_ON_HIGH_BUTTON_BUTTON       = 4;
    /** Button ID on {@link OI#driverPrimaryStick} to drive towards the center of the vision target */
    public final static int      DRIVE_TO_HIGH_GOAL_BUTTON          = 3;
    ///// Configuration for Driver's Secondary Stick /////
    /** Button ID on {@link OI#driverSecondaryStick} to enter straight mode */
    public final static int      STRAIGHT_MODE_BUTTON               = 1;
    /** Button ID on {@link OI#driverSecondaryStick} to center on the gear's vision target */
    public final static int      CENTER_ON_GEAR_BUTTON              = 3;
    /** Button ID on {@link OI#driverSecondaryStick} to drive toward's the center of gear's vision target */
    public final static int      DRIVE_TO_GEAR_BUTTON               = 4;
    /** Button ID on {@link OI#driverSecondaryStick} to shoot the ball and enable the agitator to feed mode */
    public final static int      SHOOT_BUTTON                       = 5;
    /** Button ID on (?) to climb the rope */
    public final static int      CLIMB_BUTTON                       = 6;
    /** Button ID on (?) to collect balls */
    public final static int      BALL_COLLECT_BUTTON                = 7;

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
    ///// Smart Filter Configuration /////
    /** How off the value is allowed to be from what it should be for vision tracking algorithms */
    public final static double   ALLOWABLE_ERROR                    = 0.05;
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
    public final static int      IMAGE_X                            = 320;
    /** Size of the vision tracking image's Y axis */
    public final static int      IMAGE_Y                            = 240;
    /** Center point on the screen */
    public final static int[]    SCREEN_CENTER                      = {IMAGE_X / 2, IMAGE_Y / 2};
    ///// Speed Buffer Configuration /////
    /** Accelerate/decelerate if a speed is outside of the buffer range (requires {@link #USE_SPEED_BUFFER} be true */
    public final static boolean  BUFFER_ACCELERATION                = false;

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
