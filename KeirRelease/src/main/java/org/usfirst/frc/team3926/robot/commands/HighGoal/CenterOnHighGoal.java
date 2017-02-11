package org.usfirst.frc.team3926.robot.commands.HighGoal;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.Robot;

/***********************************************************************************************************************
 * Command to center the robot on the high goal target
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 ***********************************************************************************************************************/
public class CenterOnHighGoal extends Command {

    /**
     * Constructs the CenterOnHighGoal command requiring {@link Robot#driveControl}
     */
    public CenterOnHighGoal() {

        requires(Robot.driveControl);
    }

    /**
     * Tells the SmartDashboard that the CenterOnHighGoal command has started
     */
    protected void initialize() {

        SmartDashboard.putBoolean("Center on High Goal: ", true);

    }

    /**
     * Centers on the high goal using {@link org.usfirst.frc.team3926.robot.subsystems.DriveControl#center(boolean)}
     */
    protected void execute() {

        Robot.driveControl.center(false);
    }

    /**
     * isFinished() is not needed because this command is run using
     * {@link org.usfirst.frc.team3926.robot.OI#centerOnHighGoal}
     *
     * @return false
     */
    protected boolean isFinished() {

        return false;
    }

    /**
     * end() is not needed because this command is run using {@link org.usfirst.frc.team3926.robot.OI#centerOnHighGoal}
     */
    protected void end() {

    }

    /**
     * Tells the SmartDashboard that the CenterOnHighGoal command is done
     */
    protected void interrupted() {

        SmartDashboard.putBoolean("Center on High Goal: ", false);

    }

}
