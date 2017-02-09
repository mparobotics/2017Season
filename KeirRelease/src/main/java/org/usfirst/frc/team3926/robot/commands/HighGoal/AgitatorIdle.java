package org.usfirst.frc.team3926.robot.commands.HighGoal;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/***********************************************************************************************************************
 * Spins the ball agitator to prevent balls from getting stuck
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 ***********************************************************************************************************************/
public class AgitatorIdle extends Command {

    /**
     * Constructs the AgitatorIdle class with a requirement on {@link Robot#agitator}
     */
    public AgitatorIdle() {

        requires(Robot.agitator);
    }

    /**
     * Called just before this Command runs the first time
     * This changes the setpoint of the ball agitator to the idle speed using
     * {@link org.usfirst.frc.team3926.robot.RobotMap#AGITATOR_IDLE_SETPOINT}
     */
    protected void initialize() {

        Robot.agitator.setSetpoint(RobotMap.AGITATOR_IDLE_SETPOINT);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

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

    }

}
