package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 *  Turns the robot, the desired angle
 */
public class Turn extends Command {

    double desiredAngle;

    /**
     *
     * @param wantedAngle
     */
    public Turn(double wantedAngle) {

        requires(new Robot.driveSubsystem());
        desiredAngle = wantedAngle;

    }

    /** Resets the Gyro so all previous info is wiped away */
    protected void initialize() {

        Robot.driveSubsystem.resetGyro();

    }

    /** Makes the robot turn */
    protected void execute() {

        Robot.driveSubsystem.turn();

    }

    /**
     * Make the robot stop if the robot has turned the desired angle
     *
     * @return if the robot has turned the desired angle
     */
    protected boolean isFinished() {

        return Robot.driveSubsystem.hasRobotTurned(desiredAngle);

    }

    /**
     * No method necessary to end the
     * command
     */
    protected void end() {

    }

    /**
     * This command should not be interrupted
     */
    protected void interrupted() {

    }

}
