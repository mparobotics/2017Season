package org.usfirst.frc.team3926.robot.commands.Gears;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * Starts the robot on the path towards the gear target for
 * {@link org.usfirst.frc.team3926.robot.commands.Driving.ContinueUntilRange}
 */
public class GetGearTrajectory extends Command {

    /**
     * Constructs the GetGearTrajectory command requiring {@link Robot#driveControl}
     */
    public GetGearTrajectory() {

        requires(Robot.driveControl);
    }

    /**
     * Resets values in {@link Robot#driveControl}
     */
    protected void initialize() {

        Robot.driveControl.reset();

    }

    /**
     * Drives the robot towards the gear target
     */
    protected void execute() {

        Robot.driveControl.autonomousTank(true);

    }

    /**
     * Stops this command once the robot is centered or until the vision target is lost
     *
     * @return If the robot is centered or the vision target is lost
     */
    protected boolean isFinished() {

        return Robot.driveControl.isCentered() || Robot.driveControl.lostTarget(true);
    }

    // Called once after isFinished returns true
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }

}
