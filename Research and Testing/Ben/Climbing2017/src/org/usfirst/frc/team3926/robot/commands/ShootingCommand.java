package org.usfirst.frc.team3926.robot.commands;

import org.usfirst.frc.team3926.robot.OI;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.subsystems.ClimbSubsystem;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;

public class ShootingCommand extends Command {

	
	/*
	 * requires climbSubsystem
	 * constructs the climber talon
	 */
    public ShootingCommand() {
        
        requires(Robot.shootingSubsystem);
        Robot.shootingSubsystem.shooter = new Talon(4);
    }

   
    protected void initialize() {
    }

    /*
     * Continuously calls spinMotor
     */
    public void execute() {
    	Robot.shootingSubsystem.useShooter();    
    }

   /*
    * stops the command if the limit switch is triggered
    */
    public boolean isFinished() {
    	
    	return OI.RIGHT_STICK.getRawButton(1);
    	
    }

    /*
     * sets the speed of the climber talon to zero before the command is over
     */
    protected void end() {
    	Robot.shootingSubsystem.shooter.set(0);
    }

    
    protected void interrupted() {
    
    }
}


