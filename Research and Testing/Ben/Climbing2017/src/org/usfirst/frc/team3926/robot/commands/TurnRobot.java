package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.subsystems.DriveSubsytem;

/**
 * Has the robot turn
 *
 * @author Benjamin Lash
 */
public class TurnRobot extends Command {

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

        Robot.driveSubsystem.turning();

    }

    /**
     * Stores the the angle info from the {@link DriveSubsytem#Gyro()}
     */
    public void execute() {

        Robot.driveSubsystem.Gyro();

    }

    /**
     * This command should not be interrupted by any of the commands which could interrupt it
     */
    public void interrupted() {

    }

    /**
     * Ends the command if the robot has turned 90 degrees
     *
     * @return {@link org.usfirst.frc.team3926.robot.subsystems.DriveSubsytem#HasRobotTurned}
     */
    public boolean isFinished() {

        return Robot.driveSubsystem.HasRobotTurned();

    }

    /**
     * Decelerates the robot until it stops
     */
    public void end() {

        Robot.driveSubsystem.deceleration();

    }
}
