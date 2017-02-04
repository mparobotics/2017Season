package org.usfirst.frc.team3926.robot.commands;

import org.usfirst.frc.team3926.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * turns on the motor for shooting
 */
public class ShooterCommand extends Command {

    /**
     * tells code it needs to access things in the shooting subsystem
     */
    public ShooterCommand() {

        requires(Robot.shootingSystem);

    }

    /**
     * initialization is done in the climbing subsystem
     */
    protected void initialize() {

    }

    /**
     * calls the shooting subsystem
     */
    protected void execute() {

        Robot.shootingSystem.shootingEncoder();
    }

    /**
     * doesn't return anything
     * @return
     */
    protected boolean isFinished() {

        return false;
    }

    /**
     * turns off the shooting motor
     */
    protected void end() {

        Robot.shootingSystem.SetSpeed(0);
    }

    /**
     * shouldn't be interrupted
     */
    protected void interrupted() {

    }
}
