package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

public class DriveForwardCommand extends Command {

    public DriveForwardCommand() {

        requires(Robot.driveSubsystem);
    }

    public void initialize() {

    }

    /**
     * calls the driveForward command to set the speed of the talons in the driveSystem to full
     */
    public void execute() {

        Robot.driveSubsystem.driveForward();

    }

    public void interrupted() {

    }

    /**
     * checks if the robot has traveled 10 meters and stops it in that case
     */
    public boolean isFinished() {

        return Robot.driveSubsystem.tenMetersTraveled();
    }

    public void end() {
        /**
         * resets the drivingEncoder so it can be used again for redoing this command or doing another command
         */
        Robot.driveSubsystem.resetEncoder();

    }
}

