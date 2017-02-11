package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * Turns toward the shooting target
 *
 * @author Benjamin Lash
 */
public class VisionTrackingTurning extends Command {

    /**
     * No relevant variables or methods are needed for this function
     */
    protected void initialize() {

    }

    /**
     * Turns toward the target using movement values from the
     * {@link org.usfirst.frc.team3926.robot.subsystems.VisionTrackingSubsystem#visionTrackingTurningSpeeds}
     */
    protected void execute() {

        double[] turningVisionArray = Robot.visionTrackingSubsystem.visionTrackingTurningSpeeds();
        Robot.driveSubsystem.visionTrackingMovement(turningVisionArray[0], turningVisionArray[1]);

    }

    /**
     * No relevant variables or methods are needed for this function
     */
    protected void interrupted() {

    }

    protected boolean isFinished() {

        return false;

    }

    /**
     * No relevant variables or methods are needed for this function
     */
    protected void end() {

    }

}
