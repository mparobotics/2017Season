/*
 * @file DriveCommand.java
 * @author Benjamin Lash
 * @contact lash.benjamin@yahoo.com
 */
package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * Makes the robot drive system
 *
 * @author Benjamin Lash
 */
public class DriveWithController extends Command {

    /**
     * Requires the driveSubsystem
     */
    public DriveWithController() {

        requires(Robot.driveSubsystem);

    }

    /**
     * This command should not be interrupted by any of the commands which could interrupt it
     */
    public void interrupted() {

    }

    /**
     * No relevant variables or methods are needed for this function
     */
    public void initialize() {

    }

    /**
     * Continuously calls the driveMethod method
     */
    public void execute() {

        Robot.driveSubsystem
                .driveMethod(Robot.oi.rightStick.getY(), Robot.oi.leftStick.getY(), Robot.oi.leftStick.getRawButton
                        (RobotMap.EQUALIZE_DRIVE_SYSTEM_SPEED_INPUT_BUTTON_NUMBER));

    }

    /**
     * DriveWithController has no condition which triggers it to finish
     */
    public boolean isFinished() {

        return false;

    }

    /**
     * No relevant variables or methods are needed for this function
     */
    public void end() {

    }
}
