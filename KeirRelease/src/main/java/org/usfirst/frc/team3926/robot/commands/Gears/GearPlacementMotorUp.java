package org.usfirst.frc.team3926.robot.commands.Gears;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * Puts the gear placement system's motor up (letting the gear come out)
 */
public class GearPlacementMotorUp extends Command {

    /**
     * Constructs the GearPlacementMotorUp command requiring {@link Robot#gearPlacer}
     */
    public GearPlacementMotorUp() {

        requires(Robot.gearPlacer);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return false;
    }

    // Called once after isFinished returns true
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
