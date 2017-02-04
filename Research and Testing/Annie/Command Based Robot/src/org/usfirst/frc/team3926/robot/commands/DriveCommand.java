package org.usfirst.frc.team3926.robot.commands;
//import edu.wpi.first.wpilibj.RobotDrive;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.OI;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * turns on the motors for driving and puts them in tank drive
 */
public class DriveCommand extends Command {

    /**
     * tells code it needs to access things in the driving subsystem
     */
    public DriveCommand() {

        requires(Robot.driveSystem);

    }

    /**
     * initialization is done in the climbing subsystem
     */
    protected void initialize() {

    }

    /**
     * calls the drive subsystem
     * gets the Y values from the left and right joysticks
     * puts those values in tank drive
     */
    protected void execute() {

        Robot.driveSystem.TankDrive(OI.leftStick.getY(), OI.rightStick.getY());

    }

    /**
     * doesn't return anything
     * @return
     */
    protected boolean isFinished() {

        return false;

    }

    /**
     * stops the motors
     */
    protected void end() {

        Robot.driveSystem.TankDrive(0, 0);

    }

    /**
     * shouldn't be interrupted
     */
    protected void interrupted() {

    }
}
