
package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 *  instructs the robot to climb
 *  @author Benjamin Lash
 */
public class ClimbCommand extends Command {

    /**
     * requires climbSubsystem
     * constructs the climber talon
     */
    public ClimbCommand() {

        requires(Robot.climbSubsystem);

    }

    protected void initialize() {

    }

    /**
     * Continuously calls spinMotor
     */
    public void execute() {

        Robot.climbSubsystem.spinMotor();
    }

    /**
     * stops the command if the limit switch is triggered
     */
    public boolean isFinished() {

        return Robot.climbSubsystem.climberLimitSwitch();

    }

    /**
     * sets the speed of the climber talon to zero before the command is over
     */
    protected void end() {

        Robot.climbSubsystem.stopClimber();

    }

    protected void interrupted() {

    }
}
