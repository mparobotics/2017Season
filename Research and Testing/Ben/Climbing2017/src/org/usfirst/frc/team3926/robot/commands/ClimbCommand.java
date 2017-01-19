
package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.subsystems.ClimbSubsystem;

/**
 *
 */
public class ClimbCommand extends Command {

	
	/*
	 * requires climbSubsystem
	 * constructs the climber talon
	 */
    public ClimbCommand() {
        
        requires(Robot.climbSubsystem);
        ClimbSubsystem.climber = new Talon(4);
    }

   
    protected void initialize() {
    }

    /*
     * Continuously calls spinMotor
     */
    public void execute() {
    	Robot.climbSubsystem.spinMotor();    
    }

   /*
    * stops the command if the limit switch is TRIGGERED!
    */
    public boolean isFinished() {
        return Robot.limitSwitch.get();
    	
    }

    /*
     * sets the speed of the climber talon to zero before the command is over
     */
    protected void end() {
    	ClimbSubsystem.climber.set(0);
    }

    
    protected void interrupted() {
    }
}
