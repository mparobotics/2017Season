package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/***********************************************************************************************************************
 * Command to make the robot climb
 * @author William Kluge
 *      <p>
 *      Contact: klugewilliam@gmail.com
 *      </p>
 ***********************************************************************************************************************/
public class Climb extends Command {

    /**
     * Constructs the Climb command using {@link Robot#climber}
     */
    public Climb() {

        requires(Robot.climber);
    }

    /**
     * No initialization code is needed
     */
    protected void initialize() {

    }

    /**
     * Sets the climbing motor to {@link RobotMap#CLIMBER_SPEED}
     */
    protected void execute() {

        Robot.climber.climb(RobotMap.CLIMBER_SPEED);
    }

    /**
     * isFinished() is not needed because this command is controlled using
     * {@link org.usfirst.frc.team3926.robot.OI#climb}
     *
     * @return false
     */
    protected boolean isFinished() {

        return false;
    }

    /**
     * end() is not needed because this command is controlled using {@link org.usfirst.frc.team3926.robot.OI#climb}
     */
    protected void end() {


    }

    /**
     * Stops the robot from climbing
     */
    protected void interrupted() {

        Robot.climber.stopClimbing();

    }

}
