package org.usfirst.frc.team3926.robot.commands.HighGoal;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.Robot;

/***********************************************************************************************************************
 * Command to autonomously drive towards the high goal target
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 ***********************************************************************************************************************/
public class DriveTowardsHighGoal extends Command {

    /**
     * Constructs the DriveTowardsHighGoal command requiring {@link Robot#driveControl}
     */
    public DriveTowardsHighGoal() {

        requires(Robot.driveControl);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

        SmartDashboard.putBoolean("Drive to High Goal: ", true);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        Robot.driveControl.autonomousTank(false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return false;
    }

    // Called once after isFinished returns true
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

        SmartDashboard.putBoolean("Drive to High Goal: ", false);

    }

}
