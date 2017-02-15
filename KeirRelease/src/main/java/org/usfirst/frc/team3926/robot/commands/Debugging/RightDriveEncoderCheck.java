package org.usfirst.frc.team3926.robot.commands.Debugging;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * DEBUGGING COMMAND Prints the value of the drive train's right encoder and resets its value
 * @author William Kluge
 * <p>
 *     Contact: klugewilliam@gmail.com
 * </p>
 */
public class RightDriveEncoderCheck extends Command {

    public RightDriveEncoderCheck() {

        requires(Robot.driveControl);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        Robot.driveControl.printResetRightEncoder();

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return false;
    }

    // Called once after isFinished returns true
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
