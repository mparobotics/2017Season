package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * has the robot turn
 * @author Benjamin Lash
 */

public class TurnCommand extends Command {




    public void TurnCommand() {

        requires(Robot.driveSubsystem);
    }

    /**
     * activates the turning method
     */
    public void initialize() {

        Robot.driveSubsystem.turning();
    }

    /**
     * stores the the angle info from the gyro, the
     */
    public void execute() {

       Robot.driveSubsystem.Gyro();
    }

    /**
     * this command should not be interrupted by any of the commands which could interrupt it
     */
    public void interrupted() {

    }

    /**
     * @return if the robot has turned 90 degrees
     */
    public boolean isFinished() {

        return Robot.driveSubsystem.HasRobotTurned();
    }

    /**
     * decelerates the robot until it stops
     */
    public void end() {

        Robot.driveSubsystem.deceleration();

    }
}
