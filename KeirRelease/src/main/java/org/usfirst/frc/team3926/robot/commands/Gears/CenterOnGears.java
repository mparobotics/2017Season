package org.usfirst.frc.team3926.robot.commands.Gears;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.Robot;

/***********************************************************************************************************************
 * Centers the robot on the vision target for gear placement
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 ***********************************************************************************************************************/
public class CenterOnGears extends Command {

    public CenterOnGears() {
        requires(Robot.driveControl);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

        SmartDashboard.putBoolean("Center on Gears: ", true);


    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.driveControl.center(true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return false;
    }

    // Called once after isFinished returns true
    protected void end() {

        SmartDashboard.putBoolean("Center on Gears: ", false);


    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
