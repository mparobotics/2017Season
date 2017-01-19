
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	public static int DOUBLE_SOLENIOD_1 = 0; 
	public static int DOUBLE_SOLENIOD_2 = 1;
	Joystick joystick; //the thing that does the things 
	DoubleSolenoid solenoid; // the things that does the things 
	Compressor c; // the thing that does the things  
	
    public void robotInit() {
    	joystick = new Joystick(0); 
    	joystick = new Joystick(1); 
    	solenoid = new DoubleSolenoid(5, 0, 1); 
    	c = new Compressor(5); 
    	c.setClosedLoopControl(true); 
    }
    
    public void teleopPeriodic() {
    	
    	if (joystick.getRawButton(1)) {  
    		
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
