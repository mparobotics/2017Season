package org.usfirst.frc.team3926.robot.commands.HighGoal;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * Runs the shooter backwards to get stuck balls out
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 */
public class ShooterReverse extends Command {

    /**
     * Constructs the ShooterReverse command requiring {@link Robot#shooter}
     */
    public ShooterReverse() {

        requires(Robot.shooter);
    }

    /**
     * Enables the shooter PID loop and sets the setpoint to {@link RobotMap#SHOOTER_REVERSE_SPEED}
     */
    protected void initialize() {

        Robot.shooter.set(RobotMap.SHOOTER_REVERSE_SPEED);

    }

    /**
     * Nothing needs to run in the execute code
     */
    protected void execute() {

    }

    /**
     * This command is run when {@link org.usfirst.frc.team3926.robot.OI#reverseShooter} is held
     *
     * @return false
     */
    protected boolean isFinished() {

        return false;
    }

    /**
     * Disables the shooter PID loop
     */
    protected void end() {

        Robot.shooter.disable();

    }

    /**
     * Disables the shooter PID loop
     */
    protected void interrupted() {

        Robot.shooter.disable();

    }

}
