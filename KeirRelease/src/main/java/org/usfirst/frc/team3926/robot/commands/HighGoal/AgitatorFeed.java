package org.usfirst.frc.team3926.robot.commands.HighGoal;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/***********************************************************************************************************************
 * Command to spin the agitator to feed balls into the shooter
 * <p>
 *  This command is part of the group {@link ShootAndFeed}
 * </p>
 *
 * @author William Kluge
 *      <p>
 *      Contact: klugewilliam@gmail.com
 *      </p>
 ***********************************************************************************************************************/
public class AgitatorFeed extends Command {

    /**
     * Constructs the AgitatorIdle class with a requirement on {@link Robot#agitator}
     */
    public AgitatorFeed() {

        requires(Robot.agitator);
    }

    /**
     * This changes the setpoint of ball agitator PID loop to
     * {@link org.usfirst.frc.team3926.robot.RobotMap#AGITATOR_FEED_SETPOINT}
     * <p>
     * Note: The agitator's PID loop is enabled in {@link Robot#robotInit()}, so it doesn't need to be enabled here
     * </p>
     */
    protected void initialize() {

        Robot.agitator.setSetpoint(RobotMap.AGITATOR_FEED_SETPOINT);

    }

    /**
     * Nothing needs to be executed because PID loops run continuously
     */
    protected void execute() {

    }

    /**
     * isFinished() is not needed because this command is being controlled with
     * {@link org.usfirst.frc.team3926.robot.OI#shoot}
     *
     * @return false
     */
    protected boolean isFinished() {

        return false;
    }

    /**
     * end() is not needed because this command is being controlled with {@link org.usfirst.frc.team3926.robot.OI#shoot}
     */
    protected void end() {

    }

    /**
     * Nothing needs to be disabled because we want the PID loop to continue. It will fall back to its default command,
     * {@link AgitatorIdle}
     */
    protected void interrupted() {

    }

}
