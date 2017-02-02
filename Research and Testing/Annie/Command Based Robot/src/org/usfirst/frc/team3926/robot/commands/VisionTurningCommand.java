package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 *
 */
public class VisionTurningCommand extends Command {

    public VisionTurningCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.visionTrackingSystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run

    /**
     * returns the value of the speeds from the vision tracking subsystem and sets it to the
     * speed of the robot
     */
    protected void execute() {

        double[] returnValue = Robot.visionTrackingSystem.visionTurning();

        double LSpeed = returnValue[0];
        double RSpeed = returnValue[1];

        Robot.driveSystem.SetSpeed(LSpeed, RSpeed);

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