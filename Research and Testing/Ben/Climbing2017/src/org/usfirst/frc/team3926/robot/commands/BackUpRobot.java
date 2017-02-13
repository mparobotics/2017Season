package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * Makes the robot Drive forward
 *
 * @author Benjamin Lash
 */
public class BackUpRobot extends Command {

    int wantedDistance;

    /**
     * Requires the driveSubsystem
     */
    public BackUpRobot(int desiredDistance) {

        requires(Robot.driveSubsystem);

        wantedDistance = desiredDistance;

    }

    /**
     * No relevant variables or methods are needed for this function
     */
    public void initialize() {

    }

    /**
     * Drives backward
     */
    public void execute() {

        Robot.driveSubsystem.autoDriveDesiredDistance(wantedDistance);

    }

    /**
     * Checks if the robot has backed up 10 meters
     */
    public boolean isFinished() {

        return Robot.driveSubsystem.properNegativeDistanceTraveled(wantedDistance);

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

