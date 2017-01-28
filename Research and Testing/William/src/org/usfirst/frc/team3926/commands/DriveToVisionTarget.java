package org.usfirst.frc.team3926.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * Drives the robot towards the vision tracking target using
 * {@link org.usfirst.frc.team3926.subsystems.NetworkVisionProcessing#moveToCenter(int)}
 */
public class DriveToVisionTarget extends Command {

    public DriveToVisionTarget() {

        requires(Robot.driveControl);
        SmartDashboard.putBoolean("Drive to Vision Target", false);

    }

    // Called just before this Command runs the first time
    protected void initialize() {

        Robot.visionProcessing.initNetworkTables();

        SmartDashboard.putBoolean("Drive to Vision Target", true);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        Robot.driveControl.autonomousTank();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return Robot.visionProcessing.debugEndCommand();

    }

    // Called once after isFinished returns true
    protected void end() {

        SmartDashboard.putBoolean("Drive to Vision Target", false);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

        SmartDashboard.putBoolean("Drive to Vision Target", false);
    }

}
