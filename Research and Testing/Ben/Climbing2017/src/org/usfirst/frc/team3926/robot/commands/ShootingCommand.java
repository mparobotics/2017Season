package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 *  instructs the robot to shoot
 *  @author Benjamin Lash
 */

public class ShootingCommand extends Command {

    /**
     * requires climbSubsystem
     * constructs the climber talon
     */
    public ShootingCommand() {

        requires(Robot.shootingSubsystem);
    }

    protected void initialize() {

    }

    /**
     * makes the motor go
     */
    public void execute() {

        Robot.shootingSubsystem.useShooter();
        //Robot.shootingSubsystem.useSimpleShooter(Robot.oi.XboxJoystick.getY());
    }

    /**
     * stops the command if the limit switch is triggered
     */
    public boolean isFinished() {

        return Robot.oi.stopShooterButton.get();

    }

    /**
     * sets the speed of the climber talon to zero before the command is over
     */
    protected void end() {

        Robot.shootingSubsystem.stopShooter();
    }

    protected void interrupted() {

    }
}


