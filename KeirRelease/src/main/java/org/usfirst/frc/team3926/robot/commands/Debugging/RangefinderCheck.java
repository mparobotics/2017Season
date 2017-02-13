package org.usfirst.frc.team3926.robot.commands.Debugging;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 *
 */
public class RangefinderCheck extends Command {

    public RangefinderCheck() {

        requires(Robot.driveControl);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    /**
     * Prints the value of the rangefinder
     */
    protected void execute() {

        Robot.driveControl.printRangefinder();

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
