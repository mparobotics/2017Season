package org.usfirst.frc.team3926.robot.subsystems;

import org.usfirst.frc.team3926.robot.OI;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.DriveCommand;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsytem extends Subsystem    {
	
	static RobotDrive driveSystem;
	
	public static Talon talonBR = new Talon(0);
	public static Talon talonFR = new Talon(1);
	public static Talon talonBL = new Talon(2);
	public static Talon talonFL = new Talon(3);
	
	double distanceTraveled;
	
	/*
	 * constructs and defines the four talons
	 * constructs the driveSystem
	 */
	public DriveSubsytem(){

		driveSystem = new RobotDrive(talonFL, talonBL, talonFR, talonBR);
		
	}
	
	/*
	 *sets the default command to DriveCommand 
	 */
	
	public void initDefaultCommand(){
		new DriveCommand();
	}
	
	/*
	 * plugs in leftStickHeight and rightStickHeight to the driveSystem
	 * @rightStickHeight
	 * @leftStickHeight
	 * @leftButtonStatus
	 */
	public static void driveMethod(double rightStickHeight, double leftStickHeight, boolean leftButtonStatus){

		if (leftButtonStatus) {
			
			rightStickHeight = leftStickHeight;
		}
		driveSystem.tankDrive(leftStickHeight, rightStickHeight);
		
			
		
	}
	public static void driveForward(){
		
		driveSystem.tankDrive(1 ,1 );
		distanceTraveled = Robot.drivingEncoder.getDistance();
	}
	static double  acceleration;
	double speed = 0.5;
	public static void deceleration(){
		
	//	for(double acceleration = -5, speed = 1 ; acceleration <= -5 && acceleration != 0  ; acceleration = Robot.accelerometer.getX() 
	//	, speed = speed-0.1 ){
	//	driveSystem.tankDrive(speed, speed);
	//	}
		
		acceleration = Robot.accelerometer.getX();
		if(acceleration <= -5){
			
		}
		
		
	}
	public static void stopMotors(){
		driveSystem.tankDrive(0, 0);
		
	}
	
	public static void turning(){
		driveSystem.tankDrive(-1, 1);
	}
	public boolean tenMetersTraveled(){
		
		
		return distanceTraveled >= 10;
	}
}

