package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * drives forward
 */
public class RangeFinderTurn extends Command {

    public RangeFinderTurn() {

        requires(Robot.driveSystem);

    }

    /** Called just before this Command runs the first time */
    protected void initialize() {

    }

    /** Called repeatedly when this Command is scheduled to run */
    protected void execute() {

        Robot.driveSystem.turningWithRangeFinder(RobotMap.SHOOTING_DISTANCE);

    }

    /** Make this return true when this Command no longer needs to run execute() */
    protected boolean isFinished() {

        return Robot.driveSystem.turningWithRangeFinder(RobotMap.SHOOTING_DISTANCE);
    }

    /** Called once after isFinished returns true */
    protected void end() {

        Robot.driveSystem.TankDrive(0, 0);

    }

    /**
     * Called when another command which requires one or more of the same
     * subsystems is scheduled to run
     */
    protected void interrupted() {

    }

}
