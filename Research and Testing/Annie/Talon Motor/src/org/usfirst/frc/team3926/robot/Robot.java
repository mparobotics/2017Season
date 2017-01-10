
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Talon testMotor;
	Joystick leftStick;
	
    public void robotInit() {
    	testMotor = new Talon(1);
    	leftStick = new Joystick(0);
    }
       
    public void teleopPeriodic() {
    	
    	double leftStickValue = leftStick.getY();
       
    	leftStick.getRawButton(0);
    	testMotor.set(leftStickValue);
    		
    	}
    }

