package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.subsystems.DriveControl;

/***********************************************************************************************************************
 * Keeps the robot on its current course
 * @author William Kluge
 *     <p>
 *     Contact: klugewilliam@gmail.com
 *     </p>
 **********************************************************************************************************************/
public class ContinueTrajectory extends Command {

    /**
     * Constructs the ContinueTrajectory command requiring {@link Robot#driveControl}
     */
    public ContinueTrajectory() {

        requires(Robot.driveControl);
    }

    /**
     * Nothing needs to be initialized for this command
     */
    protected void initialize() {

        //Robot.driveControl.reverseSpeeds(); //TODO fix this more elegantly

    }

    /**
     * Calls the {@link DriveControl#continueDriving()} command
     */
    protected void execute() {

        Robot.driveControl.continueDriving();

    }

    /**
     * Controlled with whileHeld, this is not needed
     *
     * @return false
     */
    protected boolean isFinished() {

        return false;
    }

    /**
     * Controlled with whileHeld, this is not needed
     */
    protected void end() {

    }

    /**
     * No code needs to run when this command is interrupted
     */
    protected void interrupted() {

    }

}
