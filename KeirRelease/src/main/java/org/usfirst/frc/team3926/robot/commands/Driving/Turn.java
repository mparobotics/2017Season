package org.usfirst.frc.team3926.robot.commands.Driving;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 *
 */
public class Turn extends Command {

    private double  leftDistance;
    private double  rightDistance;
    private boolean turnLeft;

    /**
     * @param leftDistance  Distance the left side should travel
     * @param rightDistance Distance the right side shoudl travel
     * @param turnLeft      Whether or not the robot is turning left (determines which side is negative)
     */
    public Turn(double leftDistance, double rightDistance, boolean turnLeft) {

        requires(Robot.driveControl);
        this.leftDistance = leftDistance;
        this.rightDistance = rightDistance;
        this.turnLeft = turnLeft;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        double leftSpeed = 0, rightSpeed = 0;

        if (!Robot.driveControl.leftEncoderCheck(leftDistance))
            leftSpeed = (turnLeft) ? -RobotMap.AUTONOMOUS_SPEED : RobotMap.AUTONOMOUS_SPEED;

        if (!Robot.driveControl.rightEncoderCheck(rightDistance))
            rightSpeed = (turnLeft) ? RobotMap.AUTONOMOUS_SPEED : -RobotMap.AUTONOMOUS_SPEED;

        Robot.driveControl.driveTank(rightSpeed, leftSpeed);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return Robot.driveControl.leftEncoderCheck(leftDistance) &&
               Robot.driveControl.rightEncoderCheck(rightDistance);
    }

    // Called once after isFinished returns true
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
