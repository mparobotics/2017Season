package org.usfirst.frc.team3926.robot.commands;

import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.subsystems.DriveSubsytem;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForwardCommand extends Command{
	
	public void DriveForwardCommand(){
		requires(Robot.driveSubsystem);
	}
	
	
	
	public void initialize(){
		
	}
	
	public void execute(){
		
		Robot.driveSubsystem.driveForward();
		
	
	}
	
	public void interrupted(){
		
	}
	
	public boolean isFinished(){
		return Robot.driveSubsystem.tenMetersTraveled();	
	}
	
	public void end(){
		Robot.drivingEncoder.reset();
		
	}
}

