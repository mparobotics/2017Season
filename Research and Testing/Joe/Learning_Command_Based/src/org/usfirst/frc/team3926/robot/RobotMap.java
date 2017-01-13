package org.usfirst.frc.team3926.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;
       public static int FRONT_RIGHT_MOTOR_PWM=0,
                         BACK_RIGHT_MOTOR_PWM=1,
                         FRONT_LEFT_MOTOR_PWM=2,
                         BACK_LEFT_MOTOR_PWM=3,
                         SHOOTER_MOTOR_PWM=4;

       public static double SAFETY_FACTOR = 0.50;

       public static int RIGHT_STICK_PORT=0,
                         LEFT_STICK_PORT=1;

       public static int ENCODER_DI_ONE =0,
                         ENCODER_DI_TWO =1;




       public static boolean XBOX_CONTROLLER = true;
	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
