package org.usfirst.frc.team3926.robot.commands;

import org.usfirst.frc.team3926.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 *
 *
 */
public class ShooterCommand extends Command {



    public ShooterCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.shootingSystem);

    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        Robot.shootingSystem.shootingEncoder();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return false;
    }

    // Called once after isFinished returns true
    protected void end() {

        Robot.shootingSystem.SetSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}