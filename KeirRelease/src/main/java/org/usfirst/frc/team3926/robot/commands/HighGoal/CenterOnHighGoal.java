package org.usfirst.frc.team3926.robot.commands.HighGoal;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

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

        if (Robot.driveControl.lostTarget(false)) //turns to try to find the target
            Robot.driveControl.driveTank(RobotMap.AUTONOMOUS_SPEED, -RobotMap.AUTONOMOUS_SPEED);

    }

    /**
     *
     */
    protected boolean isFinished() {

        return Robot.driveControl.isCentered();
    }

    /**
     * Nothing needs to happen in the end method
     */
    protected void end() {

        SmartDashboard.putBoolean("Center on High Goal: ", false);

    }

    /**
     * Tells the SmartDashboard that the CenterOnHighGoal command is done
     */
    protected void interrupted() {

        SmartDashboard.putBoolean("Center on High Goal: ", false);

    }

}
