
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
   
	Talon talonFR;
	Talon talonFL;
	Talon talonBR;
	Talon talonBL;
    
	RobotDrive driveSystem;
	
	Joystick leftJoystick;
	Joystick rightJoystick;
	
   
    public void robotInit() {
      talonFR = new Talon(0);
      talonFL = new Talon(1);
      talonBR = new Talon(2);
      talonBL = new Talon(3);
      
      driveSystem = new RobotDrive(talonFL, talonBL, talonFR, talonBR);
      
      leftJoystick = new Joystick(0);
      rightJoystick = new Joystick(1);
    		  
    }
    
    public void autonomousInit() {
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
    	double leftstickheight = leftJoystick.getY();
    	double rightstickheight = rightJoystick.getY();
    	
        if (rightJoystick.getRawButton(1)) {
        	leftstickheight = rightstickheight;
        }
        driveSystem.tankDrive(leftstickheight, rightstickheight);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
