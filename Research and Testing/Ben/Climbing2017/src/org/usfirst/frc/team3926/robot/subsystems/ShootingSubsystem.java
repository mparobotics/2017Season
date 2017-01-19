package org.usfirst.frc.team3926.robot.subsystems;

import org.usfirst.frc.team3926.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShootingSubsystem extends Subsystem {

		
		/*
		 * make the climber talon
		 * makes the encoder
		 */
		public Talon shooter;			
		static Encoder shootingEncoder;
		
		
		double shooterMotorSpeed = 0.5;
		//makes a variable to monitor the RPM of the shooter's motor
		double shooterRPM; 
		
		
		/*
		 * constructs the climber talon
		 * constructs the encoder
		 */
		public ShootingSubsystem(){
			//making the motor the shooter
			shooter = new Talon(RobotMap.shooterPort);													
			//making the encoder that monitors the shooting encoder
			shootingEncoder = new Encoder(2,3,false, Encoder.EncodingType.k4X);		
			//setting the distance per pulse for the shootingEncoder
			shootingEncoder.setDistancePerPulse(1);
			//setting a variable 
			
		}
	    


	    public void initDefaultCommand() {
	       
	    }
	   
	    public void useShooter(){
	    	//setting the shooterRPM variable to the RPM of the shooter as reported by the shooting encoder
	    	shooterRPM = shootingEncoder.getRate();
	    	
	    	//this if statement adds to the shooterMotorSpeed if the RPM of the shooter's motor is below target 
	    	if(shooterRPM < RobotMap.shooterRPMTarget) { 		
	    		
	    		shooterMotorSpeed += RobotMap.driveFowardSpeedIncrement;
	    	
	    	} 
	    	
	    	//this if statement subtracts from the shooterMotorSpeed if the RPM of the shooter's motor speed to correct
	    	//the RPM while making sure that the current speed is at least the driveSpeedIncrement so that the
	    	//shooterMotorSpeed stays at zero or above
	    	if (shooterRPM > RobotMap.shooterRPMTarget && shooterMotorSpeed >= RobotMap.driveFowardSpeedIncrement) {
	  
	    		shooterMotorSpeed -= RobotMap.driveFowardSpeedIncrement;
	    		
	    	}
	    
	    	shooter.set(shooterMotorSpeed); 
	    	
	    }
	 

	    
	}

