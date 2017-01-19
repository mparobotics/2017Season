
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    
	AnalogAccelerometer accel;
	double accelerationAmount;
	
    public void robotInit() {
        
    	
    	accel = new AnalogAccelerometer(0);
    	accel.setSensitivity(.018);
    	accel.setZero(2.5);
    }
   
    public void autonomousInit() {
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    }

   
    public void teleopPeriodic() {
        SmartDashboard.putNumber("Acceleration", accel.getAcceleration());
    	
    	
    }
    
  
    public void testPeriodic() {
    
    }
    
}
