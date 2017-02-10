package org.usfirst.frc.team3926.robot.commands.HighGoal;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.Robot;

/***********************************************************************************************************************
 * Command to center the robot on the high goal target
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 ***********************************************************************************************************************/
public class CenterOnHighGoal extends Command {

    public CenterOnHighGoal() {
        requires(Robot.driveControl);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

        SmartDashboard.putBoolean("Center on High Goal: ", true);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        Robot.driveControl.center(false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return false;
    }

    // Called once after isFinished returns true
    protected void end() {

        SmartDashboard.putBoolean("Center on High Goal: ", false);


    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
