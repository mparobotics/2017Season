package org.usfirst.frc.team3926.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/***********************************************************************************************************************
 * Drives the robot forward during autonomous
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 * TODO this
 ***********************************************************************************************************************/
public class DriveForward extends Command {

    private double driveDistance;

    /**
     * Constructs the DriveForward command requiring {@link Robot#driveControl}
     * @param driveDistance Distance to drive forward
     */
    public DriveForward(double driveDistance) {

        requires(Robot.driveControl);
        this.driveDistance = driveDistance;

    }

    /**
     *
     */
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        Robot.driveControl.driveTank(RobotMap.AUTONOMOUS_SPEED, RobotMap.AUTONOMOUS_SPEED);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return Robot.driveControl.leftEncoderCheck(driveDistance) &&
               Robot.driveControl.rightEncoderCheck(driveDistance);
    }

    // Called once after isFinished returns true
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
