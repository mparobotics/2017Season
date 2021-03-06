package org.usfirst.frc.team3926.robot.commands.Gears;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 *
 */
public class ActivateGearArm extends Command {

    public ActivateGearArm() {

        requires(Robot.gearPlacer);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

        Robot.gearPlacer.set(RobotMap.GEAR_PLACEMENT_SPEED);

        try {
            wait(RobotMap.GEAR_MOTOR_DOWN_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return true;
    }

    // Called once after isFinished returns true
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

        Robot.gearPlacer.set(0);

    }

}
