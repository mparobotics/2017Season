
package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ExampleSubsystem extends Subsystem{
	
	public void initDefaultCommand(){
		
	}
	public void driving() {
		Talon talonFR = new Talon(0);
		Talon talonFL = new Talon(1);
		Talon talonBR = new Talon(2);
		Talon talonBL = new Talon(3);
	    
		RobotDrive driveSystem = new RobotDrive(talonFL, talonBL, talonFR, talonBR);
		
		Joystick leftJoystick = new Joystick(0);
		Joystick rightJoystick = new Joystick(1);
		
		double rightStickHeight = rightJoystick.getY();
		double leftStickHeight = leftJoystick.getY();

		if(rightJoystick.getRawButton(1)){
			
			rightStickHeight = leftStickHeight;
					
		}
		
		driveSystem.tankDrive(rightStickHeight,leftStickHeight);
	}
	
}