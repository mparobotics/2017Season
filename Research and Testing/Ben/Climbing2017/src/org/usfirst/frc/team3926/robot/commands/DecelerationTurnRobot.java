package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * Has the robot turn
 *
 * @author Benjamin Lash
 */
public class DecelerationTurnRobot extends Command {

    /**
     * Requires the robot driveSubsystem
     */
    public void TurnCommand() {

        requires(Robot.driveSubsystem);

    }

    /**
     * No method necessary to initialize this command
     */
    public void initialize() {

    }

    /**
     * Turns the Robot while deccelerationg
     */
    public void execute() {

        Robot.driveSubsystem.decelerationTurning();

    }

    /**
     * Ends the command if the robot has turned 90 degrees
     *
     * @return {@link org.usfirst.frc.team3926.robot.subsystems.DriveSubsytem#hasRobotTurned}
     */
    public boolean isFinished() {

        return Robot.driveSubsystem.hasRobotTurned();

    }

    public void end() {


    }

    /**
     * This command should not be interrupted by any of the commands which could interrupt it
     */
    public void interrupted() {

    }
}
