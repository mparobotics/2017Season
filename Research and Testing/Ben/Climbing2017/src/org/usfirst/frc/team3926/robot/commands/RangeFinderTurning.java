package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * Has the robot turn
 *
 * @author Benjamin Lash
 */
public class RangeFinderTurning extends Command {

    /**
     * Requires the robot driveSubsystem
     */
    public void TurnCommand() {

        requires(Robot.driveSubsystem);

    }

    /**
     * Activates the turning method
     */
    public void initialize() {

        Robot.driveSubsystem.resetGyro();

    }

    /**
     * Turns The Robot
     */
    public void execute() {

        Robot.driveSubsystem.rangeFinderTurning();

    }

    /**
     * Ends the command if the robot has turned 90 degrees
     *
     * @return {@link org.usfirst.frc.team3926.robot.subsystems.DriveSubsytem#hasRobotTurned}
     */
    public boolean isFinished() {

        return Robot.driveSubsystem.hasRobotTurned(90);

    }

    /**
     * Decelerates the robot until it stops
     */
    public void end() {

    }

    /**
     * This command should not be interrupted by any of the commands which could interrupt it
     */
    public void interrupted() {

    }

}
