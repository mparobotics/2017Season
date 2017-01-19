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
	public void driveForward(){
		
		driveSystem.tankDrive(1 ,1 );
		distanceTraveled = Robot.drivingEncoder.getDistance();
	}
	static double  acceleration;
	double speed = 0.5;
	public void deceleration(){
		

		acceleration = Robot.accelerometer.getX();
		if(acceleration < -5) {
			speed += RobotMap.driveFowardSpeedIncrement;
			
			
		} else if(acceleration > 0) {
			speed -= RobotMap.driveFowardSpeedIncrement; 
		} else {
			driveSystem.tankDrive(0, 0);
		}
		
		driveSystem.tankDrive(speed, speed);
		
	}
	public boolean isSpeedZero(){
		return speed == 0;
	}
	public static void stopMotors(){
		driveSystem.tankDrive(0, 0);
		
	}
	
	public void turning(){
		driveSystem.tankDrive(-1, 1);
	}
	public boolean tenMetersTraveled(){
		
		
		return distanceTraveled >= 10;
	}
}

