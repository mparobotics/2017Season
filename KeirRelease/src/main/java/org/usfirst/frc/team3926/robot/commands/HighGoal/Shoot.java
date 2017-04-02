package org.usfirst.frc.team3926.robot.commands.HighGoal;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/***********************************************************************************************************************
 * Command to make the robot shootAndFeed at the high goal
 *      <p>
 *      This command is part of the group {@link ShootAndFeed}
 *      </p>
 *
 * @author William Kluge
 *      <p>
 *      Contact: klugewilliam@gmail.com
 *      </p>
 * TODO calculate range and speed needed
 ***********************************************************************************************************************/
public class Shoot extends Command {

    /**
     * Constructs the Shoot command requiring {@link Robot#shooter}
     */
    public Shoot() {

        requires(Robot.shooter);
    }

    /**
     * Enables the shooters PID loop
     */
    protected void initialize() {

        //Robot.shooter.enable();

    }

    /**
     * Once the PID loop is started, no code is needed to keep the scheduler updating it
     * TODO calculate range and setpoint here
     */
    protected void execute() {

        double calculatedSetpoint = RobotMap.SHOOTER_SETPOINT;

        Robot.shooter.set(calculatedSetpoint);
    }

    /**
     * isFinished is not needed because this command is controlled with {@link org.usfirst.frc.team3926.robot.OI#shootAndFeed}
     *
     * @return false
     */
    protected boolean isFinished() {

        return false;
    }

    /**
     * Called once after isFinished returns true. This disables the
     */
    protected void end() {

    }

    /**
     * Disables the shooter PID loop when the
     */
    protected void interrupted() {

        Robot.shooter.disable();

    }

}
