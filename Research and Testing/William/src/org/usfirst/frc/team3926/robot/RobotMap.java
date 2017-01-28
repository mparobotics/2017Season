package org.usfirst.frc.team3926.robot;

import org.usfirst.frc.team3926.subsystems.DriveControl;
import org.usfirst.frc.team3926.subsystems.NetworkVisionProcessing;

/***********************************************************************************************************************
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 **********************************************************************************************************************/
public class RobotMap {

    /** Determines if debugging code should be used */
    public final static boolean  DEBUG                       = true;

    /* Motor IDs */
    /** PWM port for front right motor */
    public final static int      FRONT_RIGHT_MOTOR_PWM       = 0;
    /** PWM port for back right motor */
    public final static int      BACK_RIGHT_MOTOR_PWM        = 1;
    /** PWM port for front left motor */
    public final static int      FRONT_LEFT_MOTOR_PWM        = 2;
    /** PWM port for back left motor */
    public final static int      BACK_LEFT_MOTOR_PWM         = 3;
    /** Motor for testing shooter */
    public final static int      TEST_MOTOR_PWM              = 4;

    /* Button/Joystick Mapping */
    /** Whether or not to use an XBox controller for {@link OI#driverPrimaryStick} and not use {@link OI#driverSecondaryStick} */
    public final static boolean  XBOX_DRIVE_CONTROLLER       = true;
    /** ID for the left trigger on the XBox controller */
    public final static int      XBOX_LEFT_TRIGGER           = 2;
    /** ID for the right trigger on the joystick */
    public final static int      XBOX_RIGHT_TRIGGER          = 3;
    /** Axis to use for setting the test motor forward */
    public final static int      TEST_MOTOR_FORWARD          = XBOX_RIGHT_TRIGGER;
    /** Axis to use for setting the test motor in reverse */
    public final static int      TEST_MOTOR_REVERSE          = XBOX_LEFT_TRIGGER;
    /** Axis ID to use for the left speed in tank drive */
    public final static int      XBOX_LEFT_SPEED_AXIS        = 1;
    /** Axis ID to use for the right speed in tank drive */
    public final static int      XBOX_RIGHT_SPEED_AXIS       = 5;
    /** Button ID to enter safety mode when {@link #XBOX_DRIVE_CONTROLLER} is true */
    public final static int      XBOX_SAFTEY_MODE_BUTTON     = 1;
    /** Button ID to enter straight mode when {@link #XBOX_DRIVE_CONTROLLER} is true */
    public final static int      XBOX_STRAIGHT_MODE_BUTTON   = 2;
    /** Button ID to signify that an action taken by the robot in {@link DriveControl#autonomousTank()} is incorrect */
    public final static int      XBOX_CONTOUR_ERROR_BUTTON   = 5;
    /** Button ID to center the robot on the vision target */
    public final static int      XBOX_CENTER_BUTTON          = 4;
    /** Button ID to drive towards the center of the vision target */
    public final static int      XBOX_DRIVE_TO_CENTER_BUTTON = 3;
    /** Button ID to specify an error with contour detection when {@link #XBOX_DRIVE_CONTROLLER} is false */
    public final static int      CONTOUR_ERROR_BUTTON        = 3;
    /** USB port number for right joystick */
    public final static int      RIGHT_STICK_PORT            = 0;
    /** USB port number for left joystick */
    public final static int      LEFT_STICK_PORT             = 1;

    /* Vision Tracking */
    /** Name of the network table for NetworkVisionProcessing to read from */
    public final static String   TABLE_NAME                  = "vision/high_goal";
    /** Size of the vision tracking image's X axis */
    public final static int      IMAGE_X                     = 160;
    /** Size of the vision tracking image's Y axis */
    public final static int      IMAGE_Y                     = 120;
    /** Center point on the screen */
    public final static int[]    SCREEN_CENTER               = {IMAGE_X / 2, IMAGE_Y / 2};
    /** How off the value is allowed to be from what it should be for vision tracking algorithms */
    public final static double   ALLOWABLE_ERROR             = 0.05;
    /** Illegal array to return from NetworkVisionProcessing if an value could not be gotten */
    public final static double[] ILLEGAL_VALUE               = {-404, -404};
    /** Illegal double to return from NetworkVisionProcessing if a value could not be gotten */
    public final static double   ILLEGAL_DOUBLE              = -404;
    /** Illegal int to return if data could not be validated */
    public final static int      ILLEGAL_INT                 = -404;
    /** If {@link NetworkVisionProcessing#smartFilterContours(int)} should be used */
    public final static boolean  USE_SMART_FILTER            = true;
    /** Default value to use in when getting values from NetworkTables */
    public final static double[] DEFAULT_VALUE               = new double[0];
    /** Map key for the speed of the right side of the robot */
    public final static String   SPEED_RIGHT_KEY             = "rightSpeed";
    /** Map key for the speed of the left side of the robot */
    public final static String   SPEED_LEFT_KEY              = "leftSpeed";
    /** Map key for center x of a contour */
    public final static String   CONTOUR_X_KEY               = "x";
    /** Map key for center y of a contour */
    public final static String   CONTOUR_Y_KEY               = "y";
    /** Map key for contour height */
    public final static String   CONTOUR_HEIGHT_KEY          = "height";
    /** Map key for contour width */
    public final static String  CONTOUR_WIDTH_KEY                  = "width";
    /** The max speed for the robot to travel during autonomous */
    public final static double  AUTONOMOUS_SPEED                   = 0.50;
    /** The number to multiply times the speed of the robot when the driver enables saftey mode */
    public final static double  DRIVE_SAFTEY_FACTOR                = 0.50;
    /** Whether or not to invert motor direction for the drive train's right side */
    public final static boolean INVERT_RIGHT_DRIVE_MOTOR_DIRECTION = true;
    /** Whether or not to invert motor direction for the drive train's left side */
    public final static boolean INVERT_LEFT_DRIVE_MOTOR_DIRECTION = false;

    /* Variables for configuring command generation */
    /** Track the delays in human commands */
    public final static boolean TRACK_TIME_DELAYS                  = false;
    /** Cut the time delays (requires @see TRACK_TIME_DELAYS to be true) */
    public final static boolean CUT_TIME_DELAYS                    = false;
    /** The amount to cut time delays (requires @see CUT_TIME_DELAYS to be true) */
    public final static double  CUT_FACTOR                         = 0.50;
    /** Use the values from joystick inputs instead of encoder values */
    public final static boolean USE_JOYSTICK_VALUES                = false;

}
