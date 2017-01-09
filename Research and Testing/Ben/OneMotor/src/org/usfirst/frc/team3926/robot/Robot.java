
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {

	 Joystick rightstick;
	 Talon motor;
    public void robotInit() {
    	
      motor = new Talon(0);
      rightstick = new Joystick(0);
      
    }
    
	
    public void autonomousInit() {
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    }

  
    public void teleopPeriodic() {
    	double rightstickheight = rightstick.getY();
    	motor.set(rightstickheight);
    	
        
    }
    
   
    public void testPeriodic() {
    
    }
    
}
