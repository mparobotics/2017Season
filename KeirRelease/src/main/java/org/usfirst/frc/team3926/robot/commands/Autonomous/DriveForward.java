package org.usfirst.frc.team3926.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.subsystems.DriveControl;

/***********************************************************************************************************************
 * Drives the robot forward autonomously
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 * TODO this
 ***********************************************************************************************************************/
public class DriveForward extends Command {

    private double driveDistance;

    /**
     * Constructs the DriveForward command requiring {@link Robot#driveControl}
     * @param driveDistance Distance to drive forward
     */
    public DriveForward(double driveDistance) {

        requires(Robot.driveControl);
        this.driveDistance = driveDistance;

    }

    /**
     * Resets drivetrain encoders using {@link DriveControl#resetEncoders()}
     */
    protected void initialize() {

        Robot.driveControl.resetEncoders();

    }

    /**
     * Drives the robot in tank drive at the speed {@link RobotMap#AUTONOMOUS_SPEED}
     */
    protected void execute() {

        Robot.driveControl.driveTank(RobotMap.AUTONOMOUS_SPEED, RobotMap.AUTONOMOUS_SPEED);

    }

    /**
     * Check if each encoder has reached its set value
     *
     * @return If each side of the drivetrain has traveled the necessary distance
     */
    protected boolean isFinished() {

        return Robot.driveControl.leftEncoderCheck(driveDistance) &&
               Robot.driveControl.rightEncoderCheck(driveDistance);
    }

    /**
     * Nothing needs to happen when this command is done
     */
    protected void end() {

    }

    /**
     * Nothing needs to happen if this command is interrupted
     */
    protected void interrupted() {

    }

}
