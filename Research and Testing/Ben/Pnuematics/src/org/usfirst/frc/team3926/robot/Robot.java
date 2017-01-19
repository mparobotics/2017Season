
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
	//this is the compressor
	Compressor c;				
	//this is the doublesolenoid 
	DoubleSolenoid solenoid;	
	//this is the joystick for setting the doublesolenoid's states
	Joystick joystick;			
	
    public void robotInit() {
    	
    	solenoid = new DoubleSolenoid(5, 0, 1);	
    	joystick = new Joystick(0);				
    	c = new Compressor(5);					
    	c.setClosedLoopControl(true);           
    	
    }
    

    public void autonomousInit() {
    	
    }

    public void autonomousPeriodic() {
    	
    }

  
    public void teleopPeriodic() {
        
    	if (joystick.getRawButton(1)) {					//this sets the state of the doublesolenoid depending on the button that is pressed
        	solenoid.set(DoubleSolenoid.Value.kForward);        	
        } else if (joystick.getRawButton(2)) {        	
        	solenoid.set(DoubleSolenoid.Value.kReverse);        	
        } else {
        	solenoid.set(DoubleSolenoid.Value.kOff);
        }
    	
    }
    
    public void testPeriodic() {
    
    }
    
}
