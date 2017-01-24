package org.usfirst.frc.team3926.commands;

import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to execute methods of {@link org.usfirst.frc.team3926.subsystems.DriveControl}
 */
public class ControlTank extends Command {

    public ControlTank() {
        requires(Robot.driveControl);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (RobotMap.XBOX_DRIVE_CONTROLLER)
            Robot.driveControl.driveTank(Robot.oi.driverPrimaryStick.getRawAxis(1), Robot.oi.driverPrimaryStick
                                                 .getRawAxis(5), Robot.oi.straightMode.get(),
                                         Robot.oi.safteyMode.get());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.driveControl.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
