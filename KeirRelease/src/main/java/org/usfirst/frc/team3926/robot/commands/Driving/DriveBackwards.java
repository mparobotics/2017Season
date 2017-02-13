package org.usfirst.frc.team3926.robot.commands.Driving;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 *
 */
public class DriveBackwards extends Command {

    double reverseDriveDistance;

    public DriveBackwards(double driveDistance) {

        requires(Robot.driveControl);
        reverseDriveDistance = driveDistance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        Robot.driveControl.driveTank(-RobotMap.AUTONOMOUS_SPEED, -RobotMap.AUTONOMOUS_SPEED);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return Robot.driveControl.leftEncoderCheck(-reverseDriveDistance) &&
               Robot.driveControl.rightEncoderCheck(-reverseDriveDistance);
    }

    // Called once after isFinished returns true
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
