
package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.subsystems.ExampleSubsystem;
public class useDriving extends Command{
	
	public useDriving() {
		requires(Robot.exampleSubsystem);
	}
	protected void initialize(){
		
	}
	protected void interrupted(){
		
	}
	protected void execute() {
		Robot.exampleSubsystem.driving();
	}
	protected boolean isFinished() {
		return false;
	}
	protected void end() {
		
	}
	
	
}