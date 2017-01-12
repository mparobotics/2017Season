
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Encoder enc;
	Talon motor;
    public void robotInit() {
    	enc = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
        
    	enc.setMaxPeriod(.1);
    	enc.setMinRate(10);
    	enc.setDistancePerPulse(5);
    	enc.setReverseDirection(true);
    	enc.setSamplesToAverage(7);
    	
        motor = new Talon(2);
    }
   
    public void teleopPeriodic() {
    	motor.set(1);
    	double distance = enc.getDistance();
        
    	if(distance >= 1){
    		
    		motor.set(0);
    	}
        
    }
    
}
