package org.usfirst.frc.team3926.robot.commands.HighGoal;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/***********************************************************************************************************************
 * Command to spin the agitator to feed balls into the shooter
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 ***********************************************************************************************************************/
public class AgitatorFeed extends Command {

    /**
     * Constructs the AgitatorIdle class with a requirement on {@link Robot#agitator}
     */
    public AgitatorFeed() {

        requires(Robot.agitator);
    }

    /**
     * Called just before this Command runs the first time
     * This changes the setpoint of ball agitator PID loop to
     * {@link org.usfirst.frc.team3926.robot.RobotMap#AGITATOR_FEED_SETPOINT}
     */
    protected void initialize() {

        Robot.agitator.setSetpoint(RobotMap.AGITATOR_FEED_SETPOINT);
        Robot.agitator.enable();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    /**
     * This method doesn't matter because we are using the whileHeld trigger
     *
     * @return false
     */
    protected boolean isFinished() {

        return false;
    }

    // Called once after isFinished returns true
    protected void end() {

        Robot.agitator.disable();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
