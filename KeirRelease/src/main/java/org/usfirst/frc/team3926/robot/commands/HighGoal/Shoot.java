package org.usfirst.frc.team3926.robot.commands.HighGoal;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/***********************************************************************************************************************
 * Command to make the robot shoot at the high goal
 * @author William Kluge
 * <p>
 *     Contact: klugewilliam@gmail.com
 * </p>
 ***********************************************************************************************************************/
public class Shoot extends Command {

    /**
     * Constructs the Shoot command requiring {@link Robot#shooter}
     */
    public Shoot() {
        requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

        Robot.shooter.enable();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return false;
    }

    /**
     * Called once after isFinished returns true. This disables the
     */
    protected void end() {

        Robot.shooter.disable();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
