package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 *
 */
public class TurnDesiredDegreesAndSpeed extends Command {

    int angle;
    int leftSpeed;
    int rightSpeed;

    public TurnDesiredDegreesAndSpeed(int desiredAngle, int desiredLeftSideSpeed, int desiredRightSideSpeed) {

        angle = desiredAngle;
        leftSpeed = desiredLeftSideSpeed;
        rightSpeed = desiredRightSideSpeed;

    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    /**
     * Turns robot using desired speed
     */
    protected void execute() {

        Robot.driveSubsystem.turnDesiredAngle(leftSpeed, rightSpeed);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return Robot.driveSubsystem.hasRobotTurned(angle);

    }

    // Called once after isFinished returns true
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}



























//annie is cool