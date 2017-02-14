package org.usfirst.frc.team3926.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * Drives the robot to within a specified distance of something
 * TODO allow driving backwards
 */
public class RangefinderDrive extends Command {

    /** Distance away from object to stop at */
    private double distance;

    /**
     * Constructs the RangefinderDrive command requiring {@link Robot#driveControl}
     */
    public RangefinderDrive(double distance) {

        requires(Robot.driveControl);
        this.distance = distance;
    }

    /**
     * No values need to be initialized for this command
     */
    protected void initialize() {

    }

    /**
     * Drives the robot forward
     */
    protected void execute() {

        Robot.driveControl.driveTank(RobotMap.AUTONOMOUS_SPEED, RobotMap.AUTONOMOUS_SPEED);

    }

    /**
     * Checks if the robot is within the specified distance from a target
     *
     * @return If the robot is within the specified distance from a target
     */
    protected boolean isFinished() {

        return Robot.driveControl.withinDistance(distance);
    }

    /**
     * No code needs to run when this command ends
     */
    protected void end() {

    }

    /**
     * No code needs to be run if this command is interrupted
     */
    protected void interrupted() {

    }

}
