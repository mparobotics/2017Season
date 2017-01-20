package org.usfirst.frc.team3926.robot.commands;
//import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.OI;
import org.usfirst.frc.team3926.robot.Robot;

/**
 *
 */
public class DriveCommand extends Command {

    public DriveCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveSystem);

    }

    // Called just before this Command runs the first time
    protected void initialize() {


    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        //gets value of joystick and puts it into tank drive
        Robot.driveSystem.robotDrive.tankDrive(OI.leftstick.getY(), OI.rightStick.getY());

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return false;

    }

    // Called once after isFinished returns true
    protected void end() {

        //sets the motors to 0
        Robot.driveSystem.robotDrive.tankDrive(0,0);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
