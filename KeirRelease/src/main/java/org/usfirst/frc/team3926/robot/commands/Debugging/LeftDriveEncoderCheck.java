package org.usfirst.frc.team3926.robot.commands.Debugging;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * Calls the {@link org.usfirst.frc.team3926.robot.subsystems.DriveControl#leftEncoderCheck(double)} method
 * @author William Kluge
 *     <p>
 *     Contact: klugewilliam@gmail.com
 *     </p>
 */
public class LeftDriveEncoderCheck extends Command {

    /**
     * Constructs the LeftDriveEncoderCheck command requiring {@link Robot#driveControl}
     */
    public LeftDriveEncoderCheck() {

        requires(Robot.driveControl);
    }

    /**
     * No values need to be initialized for this command
     */
    protected void initialize() {

    }

    /**
     * Prints the value of
     */
    protected void execute() {

        Robot.driveControl.printResetLeftEncoder();

    }

    /**
     * This command is called with {@link org.usfirst.frc.team3926.robot.OI#leftDrivetrainEncoder}, so isFinished is
     * not needed
     *
     * @return false
     */
    protected boolean isFinished() {

        return false;
    }

    /**
     * This command is called with {@link org.usfirst.frc.team3926.robot.OI#leftDrivetrainEncoder}, so end is
     * not needed
     */
    protected void end() {

    }

    /**
     * Nothing needs to happen if this command is inturrupted
     */
    protected void interrupted() {

    }

}
