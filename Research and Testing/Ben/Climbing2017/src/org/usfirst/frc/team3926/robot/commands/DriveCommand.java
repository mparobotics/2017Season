/*
 * @file DriveCommand.java
 * @author Benjamin Lash
 * @contact lash.benjamin@yahoo.com
 */

package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * makes the robot drive system
 * @author Benjamin Lash
 */
public class DriveCommand extends Command {

    /**
     * tells command to require the driveSubsystem
     */
    public DriveCommand() {

        requires(Robot.driveSubsystem);
    }

    public void interrupted() {

    }

    public void initialize() {

    }

    /**
     * continuously calls the driveMethod method
     */
    public void execute() {

        Robot.driveSubsystem.driveMethod(Robot.oi.rightStick.getY(), Robot.oi.leftStick.getY(), Robot.oi.leftStick.getRawButton(1));
    }

    /**
     * driveCommand has no condition which triggers it to finish
     */
    public boolean isFinished() {

        return false;
    }

    public void end() {

    }
}
