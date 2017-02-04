
package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * Instructs the robot to climb
 *
 * @author Benjamin Lash
 */
public class ClimbCommand extends Command {

    /**
     * Requires climbSubsystem
     */
    public ClimbCommand() {

        requires(Robot.climbSubsystem);

    }

    /**
     * No relevant variables or methods are needed for this function
     */
    protected void initialize() {

    }

    /**
     * Continuously calls spinMotor
     */
    public void execute() {

        Robot.climbSubsystem.spinMotor();

    }

    /**
     * Stops the command if the limit switch is triggered
     */
    public boolean isFinished() {

        return Robot.climbSubsystem.climberLimitSwitch();

    }

    /**
     * Sets the speed of the climber talon to zero before the command is over
     */
    protected void end() {

        Robot.climbSubsystem.stopClimber();

    }

    /**
     * This command should not be interrupted by any of the commands which could interrupt it
     */
    protected void interrupted() {

    }
}
