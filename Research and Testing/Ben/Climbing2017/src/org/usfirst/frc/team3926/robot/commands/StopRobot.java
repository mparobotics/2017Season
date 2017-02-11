package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.subsystems.DriveSubsytem;

/**
 * Makes the robot slow
 *
 * @author Benjamin Lash
 */
public class StopRobot extends Command {

    /**
     * Requires the driveSubsystem
     */
    public void StoppingCommand() {

        requires(Robot.driveSubsystem);

    }

    /**
     * No relevant variables or methods are needed for this function
     */
    public void initialize() {

    }

    /**
     * Call the {@link DriveSubsytem#deceleration()} function to decelerate before stopping
     */
    public void execute() {

        Robot.driveSubsystem.deceleration();

    }

    /**
     * This command should not be interrupted by any of the commands which could interrupt it
     */
    public void interrupted() {

    }

    /**
     * Stops the robot if it has reached zero speed
     *
     * @return {@link DriveSubsytem#isSpeedZero()}
     */
    public boolean isFinished() {

        return Robot.driveSubsystem.isSpeedZero();

    }

    /**
     * No relevant variables or methods are needed for this function
     */
    public void end() {

    }

}
