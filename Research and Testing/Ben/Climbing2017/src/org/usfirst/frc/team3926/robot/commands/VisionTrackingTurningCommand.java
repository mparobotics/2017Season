package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

public class VisionTrackingTurningCommand extends Command {

    protected void initialize() {

    }

    protected void execute() {
        double[] visionArray = Robot.shootingSubsystem.visionTrackingTurningSpeeds();
        Robot.shootingSubsystem.visionTracking(visionArray[0],visionArray[1]);

    }

    protected void interrupted() {

    }

    protected boolean isFinished() {

        return false;

    }

    protected void end() {

    }

}
