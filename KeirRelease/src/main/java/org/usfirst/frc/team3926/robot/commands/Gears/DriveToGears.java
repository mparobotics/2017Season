package org.usfirst.frc.team3926.robot.commands.Gears;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/***********************************************************************************************************************
 * Drive towards the gear placement vision target
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 ***********************************************************************************************************************/
public class DriveToGears extends Command {

    /**
     * Constructs the DriveToGears command requiring {@link Robot#driveControl}
     */
    public DriveToGears() {

        requires(Robot.driveControl);
    }

    /**
     * Tells the SmartDashboard that the DriveToGears command is running
     */
    protected void initialize() {

        SmartDashboard.putBoolean("Drive to Gears: ", true);

    }

    /**
     * Drives the robot towards the gear target using
     * {@link org.usfirst.frc.team3926.robot.subsystems.DriveControl#autonomousTank(boolean)}
     */
    protected void execute() {

        Robot.driveControl.autonomousTank(true);
    }

    /**
     * Checks if the robot is within the allowed distance away from the gear placement peg wall
     *
     * @return If the robot is within the allowed distance away from the gear placement peg wall
     */
    protected boolean isFinished() {

        return Robot.driveControl.withinDistance(RobotMap.GEAR_PLACEMENT_DISTANCE) ||
               Robot.oi.cancelCommand.get();
    }

    /**
     * Tells the SmartDashboard that the DriveToGears command is finished
     */
    protected void end() {

        SmartDashboard.putBoolean("Drive to Gears: ", false);

    }

    /**
     * Tells the SmartDashboard that the DriveToGears command is finished
     */
    protected void interrupted() {

        SmartDashboard.putBoolean("Drive to Gears: ", false);

    }

}
