package org.usfirst.frc.team3926.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    //////////////////////////////////////////////////////Driving//////////////////////////////////////////////////////
    /////Driving Gyro/////
    /** Analog port for gyro */
    public static final  int    GYRO_PORT                                       = 1;
    /////Driving RPM regulation/////
    /** Increment which can adjust the speed of the robot so it it does not speed up too fast */
    public static final  double DRIVE_FORWARD_SPEED_INCREMENT                   = 0.1;
    /////Driving Deceleration/////
    /** Default speed for the start of the deceleration function */
    public static final  double DEFAULT_DECELERATION_SPEED                      = 0.5;
    /////Driving Talons/////
    /** CAN id for the drivesystem's back right talon */
    public static final  int    TALON_BR_PORT                                   = 0;
    /** CAN id for the drivesystem's front right talon */
    public static final  int    TALON_FR_PORT                                   = 1;
    /** CAN id for the drivesystem's back left talon */
    public static final  int    TALON_BL_PORT                                   = 2;
    /** CAN id for the drivesystem's front left talon */
    public static final  int    TALON_FL_PORT                                   = 3;
    /////Driving Encoders/////
    /** Port A for the right driving encoder */
    public static final  int    RIGHT_DRIVING_ENCODER_PORT_A                    = 0;
    /** Port B for the right driving encoder */
    public static final  int    RIGHT_DRIVING_ENCODER_PORT_B                    = 1;
    /** Port A for the left driving encoder */
    public static final  int    LEFT_DRIVING_ENCODER_PORT_A                     = 2;
    /** Port B for the right driving encoder */
    public static final  int    LEFT_DRIVING_ENCODER_PORT_B                     = 3;
    /** Distance per pulse for the driving encoder */
    public static final  int    DRIVING_ENCODERS_DISTANCE_PER_PULSE             = 1;
    //////////////////////////////////////////////////////Shooting/////////////////////////////////////////////////////
    /////Shooting Motor Port/////
    /** Can Port for motor which shoots */
    public static final  int    SHOOTER_PORT                                    = 1;
    /////Shooting RPM Regulation/////
    /**
     * Increment the robot uses to adjust the speed input for the shooter to acheive the desired RPM, i.e
     * SHOOTER_RPM_TARGET
     */
    public static final  double SHOOTER_SPEED_INCREMENT                         = 0.1;
    /**
     * Ideal RPM of the shooter motor which is achieved by adjusting the shooter speed by the
     * SHOOTER_SPEED_INCREMENT
     */
    public static final  int    SHOOTER_RPM_TARGET                              = 5;
    /////Shooting Encoder/////
    /** Port A for the shooting encoder */
    public static final  int    SHOOTING_ENCODER_PORT_A                         = 6;
    /** Port B for the shooting encoder */
    public static final  int    SHOOTING_ENCODER_PORT_B                         = 7;
    /** Distance per pulse for the shooting encoder */
    public static final  int    SHOOTING_ENCODER_DISTANCE_PER_PULSE             = 1;
    //////////////////////////////////////////////////Vision Tracking//////////////////////////////////////////////////
    /////Vision Tracking Screen Dimensions/////
    /** Height of the vision sensing screen */
    public static final  int    VISION_MAX_HEIGHT                               = 2;
    /** Length of the vision sensing screen */
    private static final int    VISION_MAX_LENGTH                               = 6;
    /** Center of the vision sensing screen */
    public static final  int    VISION_SCREEN_CENTER                            = VISION_MAX_LENGTH / 2;
    /////Vision Tracking Speed Regulation/////
    /** Max speed of the motors in vision tracking */
    public static final  double MAX_VISION_TRACKING_SPEED                       = 1;
    /////Vision Tracking Network Table/////
    /** Key for network table */
    public static final  String NETWORK_TABLE_KEY                               = "xValue";
    /** Name of the network table */
    public static final  String NETWORK_TABLE_NAME                              = "GRIP/ContourReport";
    //////////////////////////////////////////////////////Climbing/////////////////////////////////////////////////////
    /////Climbing Motor Speed Regulation/////
    /**
     * Increment the robot uses to adjust the speed input for the climber to acheive the desired Rpm, i.e
     * CLIMBER_RPM_TARGET
     */
    public static final  double CLIMBER_SPEED_INCREMENT                         = 0.1;
    /**
     * Ideal RPM of the climber which is acheived by adjusting the speed input of the climber using
     * CLIMBER_SPEED_INCREMENT
     */
    public static final  int    CLIMBER_RPM_TARGET                              = 5;
    /** Can port for the motor which climbs the rope */
    public static final  int    CLIMBER_PORT                                    = 1;
    /** Digital input number of the limit switch */
    public static final  int    LIMIT_SWITCH_DIGITAL_INPUT_NUMBER               = 0;
    /** Default speed for the climber */
    public static final  double DEFAULT_CLIMBER_SPEED                           = 0.5;
    /** Max speed input for the climber */
    public static final  int    CLIMBER_MAX_SPEED                               = 1;
    /** Minimum speed input for the climber */
    public static final  int    CLIMBER_MIN_SPEED                               = 0;
    /////Climbing Encoder/////
    /** Distance per pulse for the climbing encoder */
    public static final  double CLIMBING_ENCODER_DISTANCE_PER_PULSE             = 1;
    /** Port A for the climbing encoder */
    public static final  int    CLIMBING_ENCODER_PORT_A                         = 4;
    /** Port B for the climbing encoder */
    public static final  int    CLIMBING_ENCODER_PORT_B                         = 5;
    ///////////////////////////////////////////////////Button Numbers//////////////////////////////////////////////////
    /** Number for button which makes both sides of the robot go at constant RPMs */
    public static final  int    EQUALIZE_DRIVE_SYSTEM_SPEED_INPUT_BUTTON_NUMBER = 3;
    /** Button Numbers for the button which starts the climber */
    static final         int    CLIMBER_BUTTON_NUMBER                           = 1;
    /** Button Numbers for the button which starts the shooter */
    static final         int    SHOOTER_BUTTON_NUMBER                           = 1;
    /** Button Number for the button which prompts turning toward the shooting target using vision tracking */
    static final         int    VISION_TRACKING_TURNING_BUTTON_NUMBER           = 2;
    /**
     * Button Number for the button which prompts driving forward and turning toward the shooting target using vision
     * tracking
     */
    public static final  int    VISION_TRACKING_FORWARD_BUTTON_NUMBER           = 2;
    /////////////////////////////////////////////////Joystick Handling/////////////////////////////////////////////////
    /** Port number of the right joystick */
    public static final  int    RIGHT_STICK_PORT                                = 0;
    /** Port number of the left joystick */
    public static final  int    LEFT_STICK_PORT                                 = 1;
    /** Port number of the xbox joystick */
    public static final  int    XBOX_JOYSTICK_PORT                              = 2;

}
