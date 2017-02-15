package org.usfirst.frc.team3926.robot.commands.HighGoal;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * Activates the ball collector
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 */
public class CollectBalls extends Command {

    /**
     * Constructs the CollectBalls command requiring {@link Robot#ballCollector}
     */
    public CollectBalls() {

        requires(Robot.ballCollector);
    }

    /**
     * Speed of the collector is set to {@link RobotMap#BALL_COLLECTION_SPEED}
     */
    protected void initialize() {

        Robot.ballCollector.setCollectorSpeed(RobotMap.BALL_COLLECTION_SPEED);

    }

    /**
     * Because the speed was set in {@link #initialize()}, nothing needs to be executed
     * <p>
     * Note: For individual motors it's okay to set the speed like this, for the drive train it is not
     * </p>
     */
    protected void execute() {

    }

    /**
     * isFinished() is not needed becuase this command is controlled with
     * {@link org.usfirst.frc.team3926.robot.OI#collectBalls}
     *
     * @return false
     */
    protected boolean isFinished() {

        return false;
    }

    /**
     * end() is not needed because this command is controlled with
     * {@link org.usfirst.frc.team3926.robot.OI#collectBalls}
     */
    protected void end() {

    }

    /**
     * Turns off the ball collection motor
     */
    protected void interrupted() {

        Robot.ballCollector.setCollectorSpeed(0);

    }

}
