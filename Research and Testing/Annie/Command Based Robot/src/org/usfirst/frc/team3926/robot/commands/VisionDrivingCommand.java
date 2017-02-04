package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.subsystems.VisionTrackingSystem;

/**
 * gets the return values from the vision tracking system and set the speed
 * equal to the return value - so it drives towards the target
 */
public class VisionDrivingCommand extends Command {

    /**
     * tells code it needs to access things in the vision tracking subsystem
     */
    public VisionDrivingCommand() {

        requires(Robot.visionTrackingSystem);
    }

    /**
     * initialization is done in the climbing subsystem
     */
    protected void initialize() {

    }

    /**
     * gets the return value of vision driving
     * sets left speed to the first value in the array
     * sets right speed to the second
     */
    protected void execute() {

        double[] returnValue = Robot.visionTrackingSystem.visionDriving();

        double LSpeed = returnValue[0];
        double RSpeed = returnValue[1];

        Robot.driveSystem.SetSpeed(LSpeed, RSpeed);

    }
    /**
     * doesn't return anything
     * @return
     */
    protected boolean isFinished() {

        return false;
    }

    /**
     * should always be running
     */
    protected void end() {

    }

    /**
     * shouldn't be interrupted
     */
    protected void interrupted() {

    }
}
