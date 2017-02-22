package org.usfirst.frc.team3926.robot.commands.Driving;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.subsystems.DriveControl;

/***********************************************************************************************************************
 * Drives the robot in a straight line for a set distance
 * <p>
 * Note: this is not intended to be used for teleop driving. For driving in a straight line at variable speeds see
 * {@link DriveControl#straightDrive()}
 * </p>
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 **********************************************************************************************************************/
public class AutoStraightDrive extends Command {

    /** Distance that the robot should drive (forwards or backwards) */
    private double driveDistance;
    private long   waitTime;

    /**
     * Constructs the AutoStraightDrive command requiring {@link Robot#driveControl}
     *
     * @param driveDistance Distance that the robot should drive (forwards or backwards)
     */
    public AutoStraightDrive(double driveDistance) {

        requires(Robot.driveControl);
        this.driveDistance = driveDistance;
    }

    /**
     * Constructs the AutoStraightDrive command requiring {@link Robot#driveControl}
     *
     * @param driveDistance Distance that the robot should drive (forwards or backwards)
     */
    public AutoStraightDrive(double driveDistance, long waitTime) {

        requires(Robot.driveControl);
        this.driveDistance = driveDistance;
        this.waitTime = waitTime;
    }

    /**
     * Resets encoder values
     */
    protected void initialize() {

        System.out.println("Driving the robot autonomously with distance " + driveDistance);

        Robot.driveControl.resetEncoders();

        if (waitTime != 0) {
            try {
                wait(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Drives the robot in the desired direction
     */
    protected void execute() {

        if (driveDistance < 0)
            Robot.driveControl.driveTank(-RobotMap.AUTONOMOUS_SPEED, -RobotMap.AUTONOMOUS_SPEED);
        else
            Robot.driveControl.driveTank(RobotMap.AUTONOMOUS_SPEED, RobotMap.AUTONOMOUS_SPEED);

    }

    /**
     * Checks if both sides of the robot have traveled for the needed distance
     *
     * @return If both sides of the robot have traveled for the desired distance
     */
    protected boolean isFinished() {

        return Robot.driveControl.leftEncoderCheck(driveDistance) &&
               Robot.driveControl.rightEncoderCheck(driveDistance);
    }

    /**
     * Resets the drive values
     */
    protected void end() {

        Robot.driveControl.reset();

    }

    /**
     * This command <i>shouldn't</i> be interrupted, but if it is this will reset the drive values
     */
    protected void interrupted() {

        Robot.driveControl.reset();

    }

}
