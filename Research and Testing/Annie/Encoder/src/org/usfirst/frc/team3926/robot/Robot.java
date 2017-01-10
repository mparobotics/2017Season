
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
  
	Encoder enc;
	
    public void robotInit() {
    	
    	enc = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
    	enc.setMaxPeriod(.1);
    	enc.setMinRate(10);
    	enc.setDistancePerPulse(5);
    	enc.setReverseDirection(true);
    	enc.setSamplesToAverage(7);
   
    }
        
    public void teleopPeriodic() {
    	
    	int count = enc.get();
    	double rawDistance = enc.getRaw();
    	double distance = enc.getDistance();
    	double rate = enc.getRate();
    	boolean direction = enc.getDirection();
    	boolean stopped = enc.getStopped(); 
    	
    	SmartDashboard.putNumber("count", count);
    	SmartDashboard.putNumber("distance", rawDistance);
    	SmartDashboard.putNumber("rate", rate);
    	SmartDashboard.putBoolean("direction", direction);
    	SmartDashboard.putBoolean("stopped", stopped);
    }
}   