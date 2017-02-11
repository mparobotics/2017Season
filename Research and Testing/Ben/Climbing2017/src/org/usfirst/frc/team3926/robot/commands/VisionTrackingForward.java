package org.usfirst.frc.team3926.robot.commands;

/**
 * Moves the robot forward while turning toward the shooting target
 *
 * @author Benjamin Lash
 */

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

public class VisionTrackingForward extends Command {

    /**
     * No relevant variables or methods are needed for this function
     */
    protected void initialize() {

    }

    /**
     * Moves forward while turning toward the target using values for movement from the
     * {@link org.usfirst.frc.team3926.robot.subsystems.VisionTrackingSubsystem#visionTrackingForwardSpeeds} array
     */
    protected void execute() {

        double[] forwardVisionArray = Robot.visionTrackingSubsystem.visionTrackingForwardSpeeds();
        Robot.driveSubsystem.visionTrackingMovement(forwardVisionArray[0], forwardVisionArray[1]);

    }

    /**
     * This command uses a while held method on a button to activate and deactivate meaning the isFinished function is
     * not needed
     */
    protected boolean isFinished() {

        return false;

    }

    /**
     * No relevant variables or methods are needed for this function
     */
    protected void end() {

    }

    /**
     * No relevant variables or methods are needed for this function
     */
    protected void interrupted() {

    }

}