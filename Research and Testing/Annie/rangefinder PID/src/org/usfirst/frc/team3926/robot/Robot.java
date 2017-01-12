
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
	Ultrasonic ultra = new Ultrasonic (1,1);
	double range_in;
	Talon motor;
	
    public void robotInit() {
    	ultra.setAutomaticMode(true);
    	motor = new Talon(2);
    	
    }
    	
    public void teleopPeriodic() {
    	motor.set(1);
        range_in = ultra.getRangeInches();
        
        if (range_in == 5){
        	motor.set(0);
        	
        }
        
    }
    
}
