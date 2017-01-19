package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.RobotDrive;

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
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	
	public static int RIGHT_STICK_PORT 			   = 1;
	public static int LEFT_STICK_PORT  			   = 2;
	
	public static double shooterSpeedIncrement 	   = 0.1;
	public static int shooterRPMTarget 		   	   = 5;
	
	public static double climberSpeeedIncrement	   = 0.1;
	public static int climberRPMTarget 			   = 5;
	public static int climberPort 				   = 1;
	

	public static double driveFowardSpeedIncrement = 0.1;
	public static int shooterPort 				   = 2;

	
}
