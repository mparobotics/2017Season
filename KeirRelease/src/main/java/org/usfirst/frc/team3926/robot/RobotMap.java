package org.usfirst.frc.team3926.robot;

import org.usfirst.frc.team3926.robot.subsystems.DriveControl;

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
 *      <p>
 *      XBox Controllor POV (DPad) Configuration (in degrees):
 *          000
 *       270  090
 *         180
 *
 *      If it is not being clicked, it is -1
 *      All values in between are also available
 *      </p>
 *
 *      TODO make gear placement values less static during teleop
 **********************************************************************************************************************/
@SuppressWarnings("WeakerAccess")
public class RobotMap {

    ///////////////////////////////////////////// Enable/Disable Features //////////////////////////////////////////////
    /** Use code specifically made for debugging the robot */
    public final static boolean  DEBUG                               = true;

    ///////////////////////////////////////////// Autonomous Configuration /////////////////////////////////////////////
    /** Distance to drive forward for the autonomous DriveForward command (whe used by itself) */
    public final static double   AUTONOMOUS_DRIVE_FORWARD_DISTANCE   = 10; //TODO find this value
    /** Distance to drive forward for gear placement from the left start position */
    //public final static double AUTONOMOUS_
    /** Distance between the ultrasonic sensor and the gear placement device when placing gears */
    public final static double   GEAR_PLACEMENT_DISTANCE             = 0; //TODO find this value
    /** Time (in milliseconds) that it takes to put the gear motor up */
    public final static double   GEAR_MOTOR_UP_TIME                  = 1000;
    /** Time (in milliseconds) that it takes to put the gear motor down */
    public final static double  GEAR_MOTOR_DOWN_TIME                = 1000;
    /** Gear backup direction */
    public final static double  GEAR_BACKUP_DISTANCE                = 10; //TODO figure out this distance
    /** Distance for the left side of the robot to travel when turning from the gear target */
    public final static double  GEAR_TURN_LEFT_DISTANCE             = 4; //TODO figure out what this should be
    /** Distance for the right side of the robot to travel when turning from the gear target */
    public final static double  GEAR_TURN_RIGHT_DISTANCE            = 4; //TODO figure out what this should be

    ////////////////////////////////////////////// Shooter Configuration ///////////////////////////////////////////////
    ///// Shooter Motor Configuration /////
    /** Enable/Disable using CAN based talons for the shooter */
    public final static boolean SHOOTER_USE_CAN_TALON               = true;
    /** CAN ID for the the shooter's motor controller (used if {@link #SHOOTER_USE_CAN_TALON} is true) */
    public final static int     SHOOTER_CAN_ID                      = 3;
    /** PWM port for the shooter's motor controller (used if {@link #SHOOTER_USE_CAN_TALON} is false) */
    public final static int     SHOOTER_PWM_ID                      = 4;
    /** DIO port for the shooter's encoder's A channel */
    public final static int     SHOOTER_ENCODER_A_CHANNEL           = 0;
    /** DIO port for the shooter's encoder's B channel */
    public final static int     SHOOTER_ENCODER_B_CHANNEL           = 1;
    /**  */
    public final static double  SHOOTER_ENCODER_DISTANCE_PER_PULSE  = 1 / 20;
    /** Setpoint for the shooter's PID loop */
    public final static double  SHOOTER_SETPOINT                    = 600; //TODO figure out what this should be
    /** Proportional multiplier for the shooter's PID loop */
    public final static double  SHOOTER_PROPORTIONAL                = 0.001; //TODO figure out what this should be
    /** Integral multiplier for the shooter's PID loop */
    public final static double  SHOOTER_INTEGRAL                    = 0; //TODO figure out what this should be
    /** Derivative multiplier for the shooter's */
    public final static double  SHOOTER_DERIVATIVE                  = 0; //TODO figure out what this should be
    /** Absolute tolerance (allowable error from set point) from the shooter's set point */
    public final static double  SHOOTER_ABSOLUTE_TOLERANCE          = 0.1; //TODO figure out what this should be
    ///// Ball Agitator Motor Configuration /////
    /** Enable/Disable using CAN based talons for the shooter's ball agitator */
    public final static boolean AGITATOR_USE_CAN_TALON              = true;
    /** CAN ID for the ball agitator's motor controller (used if {@link #AGITATOR_USE_CAN_TALON} is true) */
    public final static int     AGITATOR_CAN_ID                     = 1;
    /** PWM ID for the ball agitator's motor controller (used if {@link #AGITATOR_USE_CAN_TALON} is false) */
    public final static int     AGITATOR_PWM_PORT                   = 5;
    /** DIO port for the agitator's encoder's A channel */
    public final static int     AGITATOR_ENCODER_A_CHANNEL          = 2;
    /** DIO port for the agitator's encoder's B channel */
    public final static int     AGITATOR_ENCODER_B_CHANNEL          = 3;
    /** The agitator's encoder has 7 pulses per revolution, this makes the encoder measure in rotations */
    public final static double  AGITATOR_ENCODER_DISTANCE_PER_PULSE = 1 / 7;
    /** Setpoint for the agitator's PID loop when it is feeding to the ball loader */
    public final static double  AGITATOR_FEED_SETPOINT              = 400; //TODO figure out what this should be
    /** Setpoint for the agitator's PID loop when it is sitting idle */
    public final static double  AGITATOR_IDLE_SETPOINT              = 100; //TODO add actual value
    /** Proportional multiplier for the agitator's PID loop */
    public final static double  AGITATOR_PROPORTIONAL               = 0.001; //TODO figure out what this should be
    /** Integral multiplier for the agitator's PID loop */
    public final static double  AGITATOR_INTEGRAL                   = 0; //TODO figure out what this should be
    /** Derivative multiplier for the agitator's */
    public final static double  AGITATOR_DERIVATIVE                 = 0; //TODO figure out what this should be
    /** Absolute tolerance (allowable error from set point) from the agitator's set point */
    public final static double  AGITATOR_ABSOLUTE_TOLERANCE         = 0.1; //TODO figure out what this should be

    /////////////////////////////////////////////// Climber Configuration //////////////////////////////////////////////
    /** Enable/Disable using CAN based talons for the climbing mechanism */
    public final static boolean CLIMBER_USE_CAN_TALON               = true;
    /** CAN ID for the climbing system's motor controller (used if {@link #CLIMBER_USE_CAN_TALON} is true) */
    public final static int     CLIMBER_CAN_ID                      = 6;
    /** CAN ID for the climbing system's second motor controller (used if {@link #CLIMBER_USE_CAN_TALON} is true) */
    public final static int     CLIMBER_SECOND_CAN_ID               = 10;
    /** PWM port for the climbing system's motor controller (used if {@link #CLIMBER_USE_CAN_TALON} is false) */
    public final static int     CLIMBER_PWM_PORT                    = 7;
    /** PWM port for the climbing system's motor controller (used if {@link #CLIMBER_USE_CAN_TALON} is false) */
    public final static int     CLIMBER_SECOND_PWM_PORT             = 8;
    /** DIO port for the climbing system's limit switch */
    //public final static int      CLIMBER_LIMIT_SWITCH_PORT           = 4; Taken out of design
    /** Speed to climb at */
    public final static double  CLIMBER_SPEED                       = 1;

    /////////////////////////////////////////// Ball Collection Configuration //////////////////////////////////////////
    /** Enable/Disable using CAN based talons for the ball feeding mechanism */
    public final static boolean BALL_COLLECTION_USE_CAN_TALON       = true;
    /** CAN ID for ball collection system's motor controller (used if {@link #BALL_COLLECTION_USE_CAN_TALON} is true) */
    public final static int     BALL_COLLECTION_CAN_ID              = 2;
    /** PWM port for ball collection motor controlled (used if {@link #BALL_COLLECTION_USE_CAN_TALON} is true */
    public final static int     BALL_COLLECTION_PWM_PORT            = 6;
    /** Speed to set the ball collector motor to */
    public final static double  BALL_COLLECTION_SPEED               = 1;

    //////////////////////////////////////// Gear Placement Configuration //////////////////////////////////////////////
    /** Enable/Disable using CAN based talons for the gear placement mechanism's motor */
    public final static boolean GEAR_PLACEMENT_USE_CAN_TALON        = true;
    /** CAN ID for the gear placement system's motor (used if {@link #GEAR_PLACEMENT_USE_CAN_TALON} is true */
    public final static int     GEAR_PLACEMENT_CAN_ID               = 9;
    /** PWM port for the gear placement system (used if {@link #GEAR_PLACEMENT_USE_CAN_TALON} is false) */
    public final static int     GEAR_PLACEMENT_PWM_PORT             = 9;
    /** Speed to set the gear placement motor to */
    public final static double  GEAR_PLACEMENT_SPEED                = 0.5;

    ///////////////////////////////////////////// Rangefinder Configuration ////////////////////////////////////////////
    /** DIO port for the rangefinder's echo pulse output */
    public final static int     RANGEFINDER_ANALOG_IN_PORT          = 0;
    /** Amount of volts per 5 millimeters */
    public final static double  RANGEFINDER_V5MM                    = 5 / 4096;
    /** Whether or not to use millimeters for the rangefinder instead of inches */
    public final static boolean RANGEFINDER_USE_MILLIMETERS         = false;

    /////////////////////////////////////////////// Drive Configuration ////////////////////////////////////////////////
    /** Use CANTalons for the drive base instead of PWM Talons */
    public final static boolean DRIVE_USE_CAN_TALON                 = true;
    /** Invert motor direction for the drive train's right side */
    public final static boolean INVERT_RIGHT_DRIVE_MOTOR_DIRECTION  = true;
    /** Invert motor direction for the drive train's left side */
    public final static boolean INVERT_LEFT_DRIVE_MOTOR_DIRECTION   = true;
    /** Number to multiply times the speed of the robot when the driver enables safety mode */
    public final static double  DRIVE_SAFETY_FACTOR                 = 0.50;
    ///// Encoder Values /////
    /** DIO port for the drive train's right encoder A channel */
    public final static int     DRIVE_RIGHT_ENCODER_A_CHANNEL       = 8;
    /** DIO port for the drive train's right encoder B channel */
    public final static int     DRIVE_RIGHT_ENCODER_B_CHANNEL       = 7;
    /** DIO port for the drive train's left encoder A channel */
    public final static int     DRIVE_LEFT_ENCODER_A_CHANNEL        = 5;
    /** DIO port for the drive train's left encoder B channel */
    public final static int     DRIVE_LEFT_ENCODER_B_CHANNEL        = 6;
    /**  */
    public final static double  DRIVE_ENCODER_DISTANCE_PER_PULSE    = 6 * Math.PI / 20;
    ///// Motor CAN IDs /////
    /** CAN ID for front right motor */
    public final static int     FRONT_RIGHT_MOTOR_CAN               = 4;
    /** CAN ID for back right motor */
    public final static int     BACK_RIGHT_MOTOR_CAN                = 5;
    /** CAN ID for front left motor */
    public final static int     FRONT_LEFT_MOTOR_CAN                = 7;
    /** CAN ID for back left motor */
    public final static int     BACK_LEFT_MOTOR_CAN                 = 8;
    ///// Motor PWM IDs /////
    /** PWM port for front right motor */
    public final static int     FRONT_RIGHT_MOTOR_PWM               = 0;
    /** PWM port for back right motor */
    public final static int     BACK_RIGHT_MOTOR_PWM                = 1;
    /** PWM port for front left motor */
    public final static int     FRONT_LEFT_MOTOR_PWM                = 2;
    /** PWM port for back left motor */
    public final static int     BACK_LEFT_MOTOR_PWM                 = 3;
    ///// Autonomous Configuration /////
    /** Max speed for the robot to travel during autonomous */
    public final static double  AUTONOMOUS_SPEED                    = 0.5;
    /** Multiplier for turning speed with autonomous driving */
    public final static double  TURNING_SPEED_MULTIPLIER            = 0.5;

    ///////////////////////////////////////// Drive Joystick Configuration /////////////////////////////////////////////
    /** Use an XBox controller for {@link OI#driverPrimaryStick} */
    public final static boolean XBOX_DRIVE_CONTROLLER               = false;
    ///// XBox configuration (for driving) /////
    /** USB port number for the XBOX controller */
    public final static int      XBOX_PORT                           = 0;
    /** Axis ID to use for the left speed in tank drive */
    public final static int      XBOX_LEFT_SPEED_AXIS                = 1; //(Left joystick y)
    /** Axis ID to use for the right speed in tank drive */
    public final static int      XBOX_RIGHT_SPEED_AXIS               = 5; //(Right joystick y)
    /** Button ID to enter safety mode */
    public final static int      XBOX_SAFETY_MODE_BUTTON             = 1; //(A)
    /** Button ID to enter straight mode */
    public final static int      XBOX_STRAIGHT_MODE_BUTTON           = 2; //(B)
    /** Button ID to signify that an action taken by the robot in (removed) is incorrect */
    public final static int      XBOX_CONTOUR_ERROR_BUTTON           = 9; //(Left Stick Click)
    /** Button ID to center the robot on the vision target */
    public final static int      XBOX_CENTER_ON_HIGH_GOAL_BUTTON     = 4; //(X)
    /** Button ID to drive towards the center of the vision target */
    public final static int      XBOX_DRIVE_TO_HIGH_GOAL_BUTTON      = 3; //(Y)
    /** Button ID to drive towards the center of the gear's vision target */
    public final static int      XBOX_CENTER_ON_GEAR_BUTTON          = 6; //(Right Bumper)
    /** Button ID to center the robot on the gear's vision target */
    public final static int      XBOX_DRIVE_TO_GEAR_BUTTON           = 5; //(Left Bumper)
    /** Button ID to turn on the shooter */
    public final static int      XBOX_SHOOT_BUTTON                   = 7; //(Back)
    /** Button ID to activate the climber */
    public final static int      XBOX_CLIMB_BUTTON                   = 8; //(Start)
    /** Button ID to collect balls */
    public final static int      XBOX_COLLECT_BUTTON                 = 10; //(Right Stick Click)

    ///// Joystick Configuration (for tank drive) /////
    ///// USB Port Configuration /////
    /** USB port number for right joystick */
    public final static int      RIGHT_STICK_PORT                    = 0;
    /** USB port number for left joystick */
    public final static int      LEFT_STICK_PORT                     = 1;
    ///// Configuration for Driver's Primary Stick /////
    /** Button ID on {@link OI#driverPrimaryStick} to enter safety mode */
    public final static int      SAFTEY_MODE_BUTTON                  = 1;
    /** Button ID on {@link OI#driverPrimaryStick} to signify that an autonomous action is incorrect */
    public final static int      CONTOUR_ERROR_BUTTON                = 5;
    /** Button ID on {@link OI#driverPrimaryStick} to center the robot on the vision target */
    public final static int      CENTER_ON_HIGH_BUTTON_BUTTON        = 4;
    /** Button ID on {@link OI#driverPrimaryStick} to drive towards the center of the vision target */
    public final static int      DRIVE_TO_HIGH_GOAL_BUTTON           = 3;
    ///// Configuration for Driver's Secondary Stick /////
    /** Button ID on {@link OI#driverSecondaryStick} to enter straight mode */
    public final static int      STRAIGHT_MODE_BUTTON                = 1;
    /** Button ID on {@link OI#driverSecondaryStick} to center on the gear's vision target */
    public final static int     CENTER_ON_GEAR_BUTTON               = 3;
    /** Button ID on {@link OI#driverSecondaryStick} to drive toward's the center of gear's vision target */
    public final static int     DRIVE_TO_GEAR_BUTTON                = 4;
    /** Button ID on {@link OI#driverSecondaryStick} to shoot the ball and enable the agitator to feed mode */
    public final static int     SHOOT_BUTTON                        = 5;
    /** Button ID on (?) to climb the rope */
    public final static int     CLIMB_BUTTON                        = 6;
    /** Button ID on (?) to collect balls */
    public final static int     BALL_COLLECT_BUTTON                 = 7;

    ///// Debugging Buttons /////
    /** Button ID on {@link OI#driverPrimaryStick} to trigger {@link DriveControl#rightEncoderCheck(double)} */
    public final static int     RIGHT_DRIVE_ENCODER_CHECK           = 11;
    /** Button ID on {@link OI#driverPrimaryStick} to trigger {@link DriveControl#leftEncoderCheck(double)} */
    public final static int     LEFT_DRIVE_ENCODER_CHECK            = 8;
    /** Button ID on {@link OI#driverPrimaryStick} to trigger {@link DriveControl#printRangefinder()} */
    public final static int     RANGEFINDER_CHECK                   = 10;

    //////////////////////////////////////////////////// Vision Tracking ///////////////////////////////////////////////
    ///// Table Names and Keys /////
    /** Name of the network table for NetworkVisionProcessing to read from */
    public final static String  TABLE_HIGH_GOAL_NAME                = "vision/high_goal";
    /** Map key for the speed of the right side of the robot */
    public final static String  SPEED_RIGHT_KEY                     = "rightSpeed";
    /** Map key for the speed of the left side of the robot */
    public final static String  SPEED_LEFT_KEY                      = "leftSpeed";
    /** Map key for center x of a contour */
    public final static String  CONTOUR_X_KEY                       = "center_x";
    /** Map key for center y of a contour */
    public final static String  CONTOUR_Y_KEY                       = "center_y";
    /** Map key for contour height */
    public final static String   CONTOUR_HEIGHT_KEY                  = "height";
    /** Map key for contour width */
    public final static String   CONTOUR_WIDTH_KEY                   = "width";
    /** Map key for SmartFilter pass status */
    public final static String   SMARTFILTER_PASS_KEY                = "smartFilter";
    ///// Smart Filter Configuration /////
    /** How off the value is allowed to be from what it should be for vision tracking algorithms */
    public final static double   ALLOWABLE_ERROR                     = 0.05;
    ///// Image size configuration /////
    /** Size of the vision tracking image's X axis */
    public final static int      IMAGE_X                             = 320;
    /** Size of the vision tracking image's Y axis */
    public final static int      IMAGE_Y                             = 240;
    /** Center point on the screen */
    public final static int[]    SCREEN_CENTER                       = {IMAGE_X / 2, IMAGE_Y / 2};

    ////////////////////////////////////////// Code Quality of Life Variables //////////////////////////////////////////
    /** Default value to use in when getting values from NetworkTables */
    public final static double[] DEFAULT_VALUE                       = new double[0];

    //////////////////////////////////////// Illegal Values (to signify errors) ////////////////////////////////////////
    /** Illegal array to return from NetworkVisionProcessing if an value could not be gotten */
    public final static double[] ILLEGAL_VALUE                       = {-404, -404};
    /** Illegal double to return from NetworkVisionProcessing if a value could not be gotten */
    public final static double   ILLEGAL_DOUBLE                      = -404;
    /** Illegal int to return if data could not be validated */
    public final static int      ILLEGAL_INT                         = -404;

}
