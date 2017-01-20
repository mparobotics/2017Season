package org.usfirst.frc.team3926.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 *
 */
public class ClimberCommand extends Command {

    public ClimberCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.climbingSystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        Robot.climbingSystem.Climb();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return Robot.climbingSystem.LimitSwitch();
        //return Robot.climbingSystem.limitSwitch.get();
    }


    // Called once after isFinished returns true
    protected void end() {

        Robot.climbingSystem.ClimbingMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

