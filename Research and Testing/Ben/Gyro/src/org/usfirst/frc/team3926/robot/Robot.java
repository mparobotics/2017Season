
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
   
	AnalogGyro gyro;
	
    public void robotInit() {
        SmartDashboard.putNumber("Gyro",gyro.getAngle());
        
    }
    
	
    public void autonomousInit() {
    	
    }

    
    public void autonomousPeriodic() {
    	
    }

    public void teleopPeriodic() {
        
    }
  
    public void testPeriodic() {
    
    }
    
}
