package org.usfirst.frc.team3926.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * Drives the robot towards the vision tracking target using
 * {@link org.usfirst.frc.team3926.subsystems.NetworkVisionProcessing#moveToCenter(int)}
 */
public class DriveToHighGoalTarget extends Command {

    public DriveToHighGoalTarget() {

        requires(Robot.driveControl);

        if (RobotMap.DEBUG)
            SmartDashboard.putBoolean("Drive to Vision Target", false);

    }

    // Called just before this Command runs the first time
    protected void initialize() {

        Robot.visionProcessing.initNetworkTables(RobotMap.TABLE_HIGH_GOAL_NAME, RobotMap.HIGH_GOAL_X_OFFSET_RATIO,
                                                 RobotMap.HIGH_GOAL_Y_OFFSET_RATIO);

        if (RobotMap.DEBUG)
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

        if (RobotMap.DEBUG)
            SmartDashboard.putBoolean("Drive to Vision Target", false);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

        if (RobotMap.DEBUG)
            SmartDashboard.putBoolean("Drive to Vision Target", false);

    }

}
