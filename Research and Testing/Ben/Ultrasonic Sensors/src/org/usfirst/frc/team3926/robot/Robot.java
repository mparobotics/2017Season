
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	double scaleFactor;
	
	public enum sensors{
		analog, digital
	}
	
	sensors sensorType = sensors.analog;
	
	
    public void robotInit() {
    	
    	if(sensorType==sensors.analog){
    		AnalogInput ultrasonicAnalog = new AnalogInput(0);
    		SmartDashboard.putNumber("UltrasonicRangeInInches", ultrasonicAnalog.getAverageVoltage()*scaleFactor);
    		
    		
    	}
    	if(sensorType==sensors.digital){
    		 Ultrasonic ultra = new Ultrasonic(1,1);
    		 ultra.setAutomaticMode(true);
    		 SmartDashboard.putNumber("UltrasonicRangeInInches", ultra.getRangeInches());
    	}
    	
       
    }
    

    public void autonomousInit() {
    	
    }

    public void autonomousPeriodic() {
    	
    }
	public class sensor{
		
		
	}
    
    public void teleopPeriodic() {
    
    }
    
    
    public void testPeriodic() {
    
    }
    
}

