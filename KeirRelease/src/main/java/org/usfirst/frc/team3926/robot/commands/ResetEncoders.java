package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 *
 */
public class ResetEncoders extends Command {

    public ResetEncoders() {

        requires(Robot.driveControl);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

        Robot.driveControl.leftEncoder.reset();
        Robot.driveControl.rightEncoder.reset();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return true;
    }

    // Called once after isFinished returns true
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
