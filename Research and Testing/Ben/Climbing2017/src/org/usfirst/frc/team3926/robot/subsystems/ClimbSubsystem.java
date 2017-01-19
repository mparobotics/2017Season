
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
	 * make the climber talon
	 * makes the encoder
	 */
	public static Talon climber;
	static Encoder climbingEncoder;
	/*
	 * defines the CLIMBER_PORT
	 */
	int climber_Port;
	double climberMotorSpeed = 0.5;
	
	
	/*
	 * constructs the climber talon
	 * constructs the encoder
	 */
	public ClimbSubsystem(){
		climber = new Talon(climber_Port);
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
    	
    	if(climbingSpeed < 5 && climberMotorSpeed <1){
    		
    		climberMotorSpeed = climberMotorSpeed + 0.1;
    	
    	}
    	if (climbingSpeed > 5 && climberMotorSpeed > 0){
    	
    		climberMotorSpeed = climberMotorSpeed - 0.1;
    	
    	}
    	climber.set(climberMotorSpeed);
    	
    }
 

    
}

