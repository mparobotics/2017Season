package org.usfirst.frc.team3926.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

import static org.usfirst.frc.team3926.robot.Robot.driveControl;

/**
 * Turns the robot to center it on the vision tracking target
 */
public class TurnToHighGoalTarget extends Command {

    public TurnToHighGoalTarget() {

        requires(driveControl);

        if (RobotMap.DEBUG)
            SmartDashboard.putBoolean("Turn to Vision Target", false);

    }

    // Called just before this Command runs the first time
    protected void initialize() {

        Robot.visionProcessing.initNetworkTables(RobotMap.TABLE_HIGH_GOAL_NAME, RobotMap.HIGH_GOAL_X_OFFSET_RATIO,
                                                 RobotMap.HIGH_GOAL_Y_OFFSET_RATIO);

        if (RobotMap.DEBUG)
            SmartDashboard.putBoolean("Turn to Vision Target", true);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        driveControl.center();
    }

    /**
     * Make this return true when this Command no longer needs to run execute()
     * @return false, this will run forever TODO get actual end command
     */
    protected boolean isFinished() {

        return false;
    }

    // Called once after isFinished returns true
    protected void end() {

        if (RobotMap.DEBUG)
            SmartDashboard.putBoolean("Turn to Vision Target", false);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

        if (RobotMap.DEBUG)
            SmartDashboard.putBoolean("Turn to Vision Target", false);
    }

}
