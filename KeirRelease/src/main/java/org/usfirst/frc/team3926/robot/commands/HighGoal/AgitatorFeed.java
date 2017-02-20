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
     * This changes the setpoint of ball agitator to
     * {@link org.usfirst.frc.team3926.robot.RobotMap#AGITATOR_FEED_SETPOINT}
     * <p>
     * Note: If {@link RobotMap#AGITATOR_DELAY_START}, the agitator will wait for
     * {@link RobotMap#AGITATOR_DELAY_TIME} milliseconds before starting
     * </p>
     */
    protected void initialize() {

        if (RobotMap.AGITATOR_DELAY_START) {
            try {
                wait(RobotMap.AGITATOR_DELAY_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Robot.agitator.set(RobotMap.AGITATOR_FEED_SETPOINT);

    }

    /**
     * TODO finish testing this
     */
    protected void execute() {

        Robot.agitator.set(RobotMap.AGITATOR_FEED_SETPOINT);

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
     * Sets the agitator to its idle speed
     */
    protected void end() {

        Robot.agitator.set(RobotMap.AGITATOR_IDLE_SETPOINT);

    }

    /**
     * Sets the agitator to its idle speed
     */
    protected void interrupted() {

        Robot.agitator.set(RobotMap.AGITATOR_IDLE_SETPOINT);

    }

}
