
package org.usfirst.frc.team3926.robot.subsystems;

import org.usfirst.frc.team3926.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimbSubsystem extends Subsystem {
	
	/*
	 * makes the talon than allow the robot to climb
	 * makes the encoder that tracts the climbing talon
	 */
	public static Talon climber;
	static Encoder climbingEncoder;
	/*
	 * defines the CLIMBER_PORT
	 */
	
	double climberMotorSpeed = 0.5;
	
	
	/*
	 * constructs the climber talon
	 * constructs the encoder
	 */
	public ClimbSubsystem(){
		
		climber = new Talon(RobotMap.climberPort);							
		climbingEncoder = new Encoder(0,1,false, Encoder.EncodingType.k4X);
		climbingEncoder.setDistancePerPulse(1);
	}
    


    public void initDefaultCommand() {
       
    }
    /*
     * sets the climber speed of the climber motor
     * @CLIMBER_SPEED
     */
    public void spinMotor(){
    	double climbingSpeed = climbingEncoder.getRate();
    	
    	if(climbingSpeed < RobotMap.climberRPMTarget && climberMotorSpeed < 1) {
    		
    		climberMotorSpeed += RobotMap.climberSpeeedIncrement;
    	
    	}
    	if (climbingSpeed > RobotMap.climberRPMTarget && climberMotorSpeed > 0) {
    	
    		climberMotorSpeed -= RobotMap.climberSpeeedIncrement;
    	
    	}
    	climber.set(climberMotorSpeed);
    	
    }
 

    
}

