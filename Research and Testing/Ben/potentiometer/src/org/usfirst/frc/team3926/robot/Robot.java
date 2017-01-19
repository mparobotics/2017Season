
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	Potentiometer potentiometer;
	public void robotInit() {
    	
    potentiometer = new AnalogPotentiometer(0,360,60);
    AnalogInput analogInput = new AnalogInput(1); 
    potentiometer = new AnalogPotentiometer(analogInput,360,30);
    }
    
	
    public void autonomousInit() {
    	
    }

    public void autonomousPeriodic() {
    	
    }

    public void teleopPeriodic() {
        SmartDashboard.putNumber("potentiometer degrees", potentiometer.get());
    }
    
    public void testPeriodic() {
    
    }
    
}
