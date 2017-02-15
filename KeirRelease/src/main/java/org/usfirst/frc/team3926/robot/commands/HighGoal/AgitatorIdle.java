package org.usfirst.frc.team3926.robot.commands.HighGoal;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/***********************************************************************************************************************
 * Spins the ball agitator to prevent balls from getting stuck
 * <p>
 *     Note: This is the default command of {@link Robot#agitator}
 * </p>
 *
 * @author William Kluge
 *     <p>
 *     Contact: klugewilliam@gmail.com
 *     </p>
 ***********************************************************************************************************************/
public class AgitatorIdle extends Command {

    /**
     * Constructs the AgitatorIdle class with a requirement on {@link Robot#agitator}
     */
    public AgitatorIdle() {

        requires(Robot.agitator);
    }

    /**
     * This changes the setpoint of the ball agitator to the idle speed using
     * {@link org.usfirst.frc.team3926.robot.RobotMap#AGITATOR_IDLE_SETPOINT}
     * <p>
     * Note: The agitator's PID loop is enables in {@link Robot#robotInit()}, so it doesn't need to be enabled here
     * </p>
     */
    protected void initialize() {

        Robot.agitator.setSetpoint(RobotMap.AGITATOR_IDLE_SETPOINT);

    }

    /**
     * No code is needed to execute because PID loops run continuously
     */
    protected void execute() {

    }

    /**
     * This command is the default command and will never finish unless interrupted
     *
     * @return false
     */
    protected boolean isFinished() {

        return false;
    }

    /**
     * This command is the default command and will never finish unless interrupted
     */
    protected void end() {

    }

    /**
     * If this command is ever interrupted, that means that {@link AgitatorFeed} has been started, and we will still
     * need the PID loop enabled
     */
    protected void interrupted() {

    }

}
