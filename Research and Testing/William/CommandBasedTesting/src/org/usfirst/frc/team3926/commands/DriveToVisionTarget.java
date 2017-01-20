package org.usfirst.frc.team3926.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 *
 */
public class DriveToVisionTarget extends Command {

    public DriveToVisionTarget() {
        requires(Robot.driveControl);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.visionProcessing.initNetworkTables();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.driveControl.autonomousTank();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return Robot.visionProcessing.debugEndCommand();

    }

    // Called once after isFinished returns true
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
