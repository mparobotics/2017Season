
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
	Ultrasonic ultra = new Ultrasonic (1,1);
	double range_in;
	double range_mm;
	
	sensorType currentSensor = sensorType.ANALOG;
	
    public void robotInit() {
    
    	ultra.setAutomaticMode(true);
    	
    }
        
    public void teleopPeriodic() {
        
    	if (currentSensor == sensorType.ANALOG){
    		
    		
    		
    	} else {
    		
    		range_in = ultra.getRangeInches();
    		range_mm = ultra.getRangeMM();
    		SmartDashboard.putNumber("range in", range_in);
    		SmartDashboard.putNumber("range mm", range_mm);
    		
    	}
        
    }
    
    public enum sensorType {
    	ANALOG, DIGITAL  
    	
    }
}