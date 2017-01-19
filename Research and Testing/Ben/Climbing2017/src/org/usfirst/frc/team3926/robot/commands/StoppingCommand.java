package org.usfirst.frc.team3926.robot.commands;

import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.subsystems.DriveSubsytem;

import edu.wpi.first.wpilibj.command.Command;

public class StoppingCommand extends Command {
	
	public void StoppingCommand(){
		requires(Robot.driveSubsystem);
	}
	public void initialize(){
		
	}
	public void execute(){
		DriveSubsytem.deceleration();
	}
	public void interrupted(){
		
	}
	public boolean isFinished(){
		
		return true;
		
	}
	public void end(){
		
	}
}
