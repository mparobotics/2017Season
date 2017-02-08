package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

import static org.usfirst.frc.team3926.robot.Robot.oi;

/**
 * Command to execute methods of {@link org.usfirst.frc.team3926.robot.subsystems.DriveControl}
 */
public class UserDriveTank extends Command {

    public UserDriveTank() {

        requires(Robot.driveControl);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    /**
     * Called repeatedly when this Command is scheduled to run
     */
    protected void execute() {

        if (RobotMap.XBOX_DRIVE_CONTROLLER)
            Robot.driveControl.driveTank(oi.driverPrimaryStick.getRawAxis(RobotMap.XBOX_RIGHT_SPEED_AXIS),
                                         oi.driverPrimaryStick.getRawAxis(RobotMap.XBOX_LEFT_SPEED_AXIS),
                                         oi.straightMode.get(),
                                         oi.safetyMode.get());
        else
            Robot.driveControl.driveTank(oi.driverPrimaryStick.getY(), oi.driverSecondaryStick.getY(),
                                         oi.straightMode.get(), oi.safetyMode.get());
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
