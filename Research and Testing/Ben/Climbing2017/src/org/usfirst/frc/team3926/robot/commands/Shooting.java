package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * Instructs the robot to shoot
 *
 * @author Benjamin Lash
 */
public class Shooting extends Command {

    /**
     * Requires shooting subsystem
     */
    public Shooting() {

        requires(Robot.shootingSubsystem);
    }

    /**
     * No relevant variables or methods are needed for this function
     */
    protected void initialize() {

    }

    /**
     * Makes the motor move
     */
    public void execute() {

        Robot.shootingSubsystem.useShooter();
        //Robot.shootingSubsystem.useSimpleShooter(Robot.oi.XboxJoystick.getY());

    }

    /**
     * Stops the command if the limit switch is triggered
     */
    public boolean isFinished() {

        return false;

    }

    /**
     * Sets the speed of the climber talon to zero before the command is over
     */
    protected void end() {


        Robot.shootingSubsystem.stopShooter();
    }

    /**
     * This command should not be interrupted by any of the commands which could interrupt it
     */
    protected void interrupted() {

    }
}


