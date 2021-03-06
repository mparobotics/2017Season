package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * turns on shooting motor
 */
public class RangeFinderShoot extends Command {


    /**
     * declares dependencies
     */
    public RangeFinderShoot() {

        requires(Robot.rangeFinderBackupSystem);
    }

    /** Called just before this Command runs the first time */
    protected void initialize() {

    }

    /** Called repeatedly when this Command is scheduled to run  */
    protected void execute() {

        Robot.shootingSystem.shootingEncoder();

    }

    /** Make this return true when this Command no longer needs to run execute() */
    protected boolean isFinished() {

        return false;
    }

    /** Called once after isFinished returns true */
    protected void end() {

    }

    /**
     * Called when another command which requires one or more of the same
     * subsystems is scheduled to run
      */

    protected void interrupted() {

    }

}
