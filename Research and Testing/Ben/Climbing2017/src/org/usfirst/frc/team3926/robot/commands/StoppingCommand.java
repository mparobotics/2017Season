package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * makes the robot slow
 * @author Benjamin Lash
 */
public class StoppingCommand extends Command {

    public void StoppingCommand() {

        requires(Robot.driveSubsystem);

    }

    public void initialize() {

    }

    /**
     * call the deceleration function to decelerate before stopping
     */
    public void execute() {


        Robot.driveSubsystem.deceleration();

    }

    public void interrupted() {

    }

    /**
     * stops the robot if it has reached zero speed
     */
    public boolean isFinished() {

        return Robot.driveSubsystem.isSpeedZero();

    }

    public void end() {

    }
}
