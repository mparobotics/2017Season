package org.usfirst.frc.team3926.robot.commands.HighGoal;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.Robot;

/***********************************************************************************************************************
 * Command to autonomously drive towards the high goal target
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 ***********************************************************************************************************************/
public class DriveTowardsHighGoal extends Command {

    /**
     * Constructs the DriveTowardsHighGoal command requiring {@link Robot#driveControl}
     */
    public DriveTowardsHighGoal() {

        requires(Robot.driveControl);
    }

    /**
     * Tells the SmartDashboard that the DriveToHighGoal command is running
     */
    protected void initialize() {

        SmartDashboard.putBoolean("Drive to High Goal: ", true);

    }

    /**
     * Drives towards the high goal vision target using
     * {@link org.usfirst.frc.team3926.robot.subsystems.DriveControl#autonomousTank(boolean)}
     */
    protected void execute() {

        Robot.driveControl.autonomousTank(false);
    }

    /**
     * isFinished() is not needed because this command is controlled by
     * {@link org.usfirst.frc.team3926.robot.OI#driveToHighGoal}
     *
     * @return false
     */
    protected boolean isFinished() {

        return false;
    }

    /**
     * end() is not needed because this command is controlled by
     * {@link org.usfirst.frc.team3926.robot.OI#driveToHighGoal}
     */
    protected void end() {

        SmartDashboard.putBoolean("Drive to High Goal: ", false);

    }

    /**
     * Tells the SmartDashboard
     */
    protected void interrupted() {

        SmartDashboard.putBoolean("Drive to High Goal: ", false);

    }

}
