package org.usfirst.frc.team3926.robot.commands.Driving;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.subsystems.DriveControl;

/**
 * Continues driving on the current trajectory until the rangefinder returns a certain range
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 */
public class ContinueUntilRange extends Command {

    /** Voltage to stop the robot at */
    private double targetVoltage;

    /**
     * Constructs the ContinueUntilRange
     *
     * @param targetVoltage Voltage to stop the robot at
     */
    public ContinueUntilRange(double targetVoltage) {

        this.targetVoltage = targetVoltage;
        requires(Robot.driveControl);
    }

    /**
     * Nothing needs to be initialized for this
     */
    protected void initialize() {

    }

    /**
     * Calls {@link DriveControl#continueDriving()} to keep the robot driving on its current trajectory
     */
    protected void execute() {

        Robot.driveControl.continueDriving();

    }

    /**
     * Stops the robot if the target voltage on the rangefinder was met or the driver cancelled the command
     *
     * @return If the robot is within range or the command was canceled
     */
    protected boolean isFinished() {

        return Robot.driveControl.withinRangefinderVoltage(targetVoltage) || Robot.oi.cancelCommand.get();
    }

    // Called once after isFinished returns true
    protected void end() {

        Robot.driveControl.reset();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
