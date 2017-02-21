package org.usfirst.frc.team3926.robot.commands.Driving;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/***********************************************************************************************************************
 * Turns the robot based on each drive encoders individual values
 * <p>
 *     This is essentially a more complex version of {@link AutoStraightDrive}
 * </p>
 *
 * @author William Kluge
 *     <p>
 *     Contact: klugewilliam@gmail.com
 *     </p>
 **********************************************************************************************************************/
public class IndividualSideDrive extends Command {

    /** Distance for the left side of the robot to travel */
    private double leftDistance;
    /** Distance for the right side of the robot to travel */
    private double rightDistance;

    /**
     * Constructs the IndividualSideDrive command requiring {@link Robot#driveControl}
     *
     * @param leftDistance  Distance the left side should travel
     * @param rightDistance Distance the right side should travel
     */
    public IndividualSideDrive(double leftDistance, double rightDistance) {

        requires(Robot.driveControl);
        this.leftDistance = leftDistance;
        this.rightDistance = rightDistance;
    }

    /**
     * Resets encoder values and drive values
     */
    protected void initialize() {

        Robot.driveControl.leftEncoder.reset();
        Robot.driveControl.rightEncoder.reset();

    }

    /**
     * Drives each side of the robot. For each side the encoder must not have a value past or equal to the value it is
     * set at
     */
    protected void execute() {

        double leftSpeed = 0, rightSpeed = 0;

        if (!Robot.driveControl.leftEncoderCheck(leftDistance))
            leftSpeed = (leftDistance < 0) ? -RobotMap.AUTONOMOUS_SPEED : RobotMap.AUTONOMOUS_SPEED;

        if (!Robot.driveControl.rightEncoderCheck(rightDistance))
            rightSpeed = (rightDistance < 0) ? RobotMap.AUTONOMOUS_SPEED : -RobotMap.AUTONOMOUS_SPEED;

        Robot.driveControl.driveTank(rightSpeed, leftSpeed);

    }

    /**
     * This command is finished when both encoders have met their value
     *
     * @return If both encoders have reached their set value
     */
    protected boolean isFinished() {

        return Robot.driveControl.leftEncoderCheck(leftDistance) &&
               Robot.driveControl.rightEncoderCheck(rightDistance);
    }

    /**
     * Resets encoders and drive values
     */
    protected void end() {

        Robot.driveControl.reset();

    }

    /**
     * This command really <i>shouldn't</i> be interrupted, but if it is this will reset the drive and encoder values
     */
    protected void interrupted() {

        Robot.driveControl.reset();

    }

}
