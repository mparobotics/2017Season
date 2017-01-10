
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	CANTalon talonSRX_FR;
	CANTalon talonSRX_FL;
	CANTalon talonSRX_BR;
	CANTalon talonSRX_BL; 
	
	RobotDrive driveSystem;
	
	Joystick leftStick;
	Joystick rightStick;
	
	public void robotInit(){
		
		talonSRX_FR = new CANTalon (1);
		talonSRX_FL = new CANTalon (2);
		talonSRX_BR = new CANTalon (3);
		talonSRX_BL = new CANTalon (4);
		
		driveSystem = new RobotDrive (talonSRX_FR, talonSRX_FL, talonSRX_BR, talonSRX_BL);
		
		leftStick = new Joystick (0);
		rightStick = new Joystick (1);
		
	}

    public void teleopPeriodic() {
      
    	double leftStickValue = leftStick.getY();
    	double rightStickValue = rightStick.getY();
    	
    	if (leftStick.getRawButton(1)) {
    		leftStickValue /= 2;
    		rightStickValue /= 2;
    		
    	}
    	if (rightStick.getRawButton(1)) {
    		leftStickValue = rightStickValue;
    		
    	}

    	driveSystem.tankDrive(leftStickValue, rightStickValue);
    }
}   
   
