package org.usfirst.frc.team3926.robot.commands.Driving;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * TODO finish
 */
public class DriveLeftSide extends Command {

    double leftEncoderTarget;

    public DriveLeftSide(double leftEncoderTarget) {

        requires(Robot.driveControl);
        this.leftEncoderTarget = leftEncoderTarget;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    /**
     * TODO figure out how to drive the two sides differently
     */
    protected void execute() {

        //Robot.driveControl.

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return Robot.driveControl.leftEncoderCheck(leftEncoderTarget);
    }

    // Called once after isFinished returns true
    protected void end() {

        Robot.driveControl.leftEncoder.reset();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
