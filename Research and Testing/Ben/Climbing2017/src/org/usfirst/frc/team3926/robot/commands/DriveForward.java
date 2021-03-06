package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * Makes the robot Drive forward
 *
 * @author Benjamin Lash
 */
public class DriveForward extends Command {

    int wantedDistance;

    /**
     * Requires the driveSubsystem
     */
    public DriveForward(int desiredDistance) {

        requires(Robot.driveSubsystem);
        wantedDistance = desiredDistance;

    }

    /**
     * No relevant variables or methods are needed for this function
     */
    public void initialize() {

    }

    /**
     * Calls the driveForward command to set the speed of the talons in the driveSystem to full
     */
    public void execute() {

        Robot.driveSubsystem.autoDriveDesiredDistance(wantedDistance);

    }

    /**
     * Checks if the robot has traveled 10 meters and stops it in that case
     */
    public boolean isFinished() {

        return Robot.driveSubsystem.properDistanceTraveled(wantedDistance);

    }

    /**
     * Resets the drivingEncoder so it can be used again for redoing this command or doing another command
     */
    public void end() {

        Robot.driveSubsystem.resetEncoder();

    }

    /**
     * This command should not be interrupted by any of the commands which could interrupt it
     */
    public void interrupted() {

    }
}

