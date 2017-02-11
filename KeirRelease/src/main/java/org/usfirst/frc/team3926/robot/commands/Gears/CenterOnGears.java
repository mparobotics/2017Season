package org.usfirst.frc.team3926.robot.commands.Gears;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.Robot;

/***********************************************************************************************************************
 * Centers the robot on the vision target for gear placement
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 ***********************************************************************************************************************/
public class CenterOnGears extends Command {

    /**
     * Constructs the CenterOnGears command requiring {@link Robot#driveControl}
     */
    public CenterOnGears() {

        requires(Robot.driveControl);
    }

    /**
     * Tells the SmartDashboard that the CenterOnGears command is running
     */
    protected void initialize() {

        SmartDashboard.putBoolean("Center on Gears: ", true);

    }

    /**
     * Uses {@link org.usfirst.frc.team3926.robot.subsystems.DriveControl#center(boolean)} to center the robot on the
     * gears target
     */
    protected void execute() {

        Robot.driveControl.center(true);
    }

    /**
     * isFinished() is not needed here because this is controlled with
     * {@link org.usfirst.frc.team3926.robot.OI#centerOnGear}
     * @return false
     */
    protected boolean isFinished() {

        return false;
    }

    /**
     *
     */
    protected void end() {

    }

    /**
     * Tells the SmartDashboard that the CenterOnGears command is done
     */
    protected void interrupted() {

        SmartDashboard.putBoolean("Center on Gears: ", false);

    }

}
