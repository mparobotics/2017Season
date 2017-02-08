package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * turns on climbing motor until limit switch is pressed
 */
public class ClimberCommand extends Command {

    /**
     * declares dependencies
     */
    public ClimberCommand() {

        requires(Robot.climbingSystem);
    }

    /**
     * initialization is done in the climbing subsystem
     */
    protected void initialize() {

    }

    /**
     * calls the climbing subsystem
     */
    protected void execute() {

        Robot.climbingSystem.Climb();
    }

    /**
     * ends the program when limit switch is pushed
     *
     * @return
     */
    protected boolean isFinished() {

        return Robot.climbingSystem.LimitSwitch();

    }

    /**
     * turns off the climbing motor
     */
    protected void end() {

        Robot.climbingSystem.StopClimbing(0);
    }

    /**
     * should only stop if limit switch is pressed
     */
    protected void interrupted() {

    }
}

