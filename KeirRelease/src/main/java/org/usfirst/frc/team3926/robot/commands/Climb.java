package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/***********************************************************************************************************************
 * Command to make the robot climb
 * @author William Kluge
 * <p>
 *     Contact: klugewilliam@gmail.com
 * </p>
 ***********************************************************************************************************************/
public class Climb extends Command {

    public Climb() {

        //requires(Robot.climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        //Robot.climber.climb(RobotMap.CLIMBER_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return false;
    }

    // Called once after isFinished returns true
    protected void end() {

        //Robot.climber.stopClimbing();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
