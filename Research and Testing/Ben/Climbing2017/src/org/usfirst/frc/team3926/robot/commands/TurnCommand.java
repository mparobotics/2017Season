package org.usfirst.frc.team3926.robot.commands;

import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.subsystems.DriveSubsytem;

import edu.wpi.first.wpilibj.command.Command;

public class TurnCommand extends Command{

	double currentAngle;
	public void TurnCommand(){
		requires(Robot.driveSubsystem);
	}
	
	public void initialize(){
		Robot.driveSubsystem.turning();
	}
	
	public void execute(){
		currentAngle = Robot.gyro.getAngle();
	}
	
	public void interrupted(){
		
	}
	
	public boolean isFinished(){
		return currentAngle >= 90;
	}
	
	public void end(){
		Robot.driveSubsystem.deceleration();
	}
}
