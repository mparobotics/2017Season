package org.usfirst.frc.team3926.robot.commands.Gears;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.Robot;

/***********************************************************************************************************************
 * Drive towards the gear placement vision target
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 ***********************************************************************************************************************/
public class DriveToGears extends Command {

    public DriveToGears() {

        requires(Robot.driveControl);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

        SmartDashboard.putBoolean("Drive to Gears: ", true);


    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        Robot.driveControl.autonomousTank(true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return false;
    }

    // Called once after isFinished returns true
    protected void end() {

        SmartDashboard.putBoolean("Drive to Gears: ", false);


    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
