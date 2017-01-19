/*
 * @file DriveCommand.java
 * @author Benjamin Lash
 * @contact lash.benjamin@yahoo.com
 */

package org.usfirst.frc.team3926.robot.commands;

import org.usfirst.frc.team3926.robot.OI;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.subsystems.DriveSubsytem;

import edu.wpi.first.wpilibj.command.Command;

	

	
public class DriveCommand extends Command{
	
	/*
	 * tells command to require the driveSubsystem
	 */
	public DriveCommand(){
		requires(Robot.driveSubsystem);
	}
	
	public void interrupted(){
		
	}
	
	public void initialize(){
		
	}
	
	/*
	 *continuously calls the driveMethod method
	 */
	public void execute(){
		Robot.driveSubsystem.driveMethod(OI.RIGHT_STICK.getY(),OI.LEFT_STICK.getY(), OI.LEFT_STICK.getRawButton(1));
	}
	
	
	/*
	 *driveCommand has no condition which triggers it to finish 
	 */
	public boolean isFinished(){
		return false;
	}
	
	public void end(){
		
	}
}
