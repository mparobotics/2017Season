package org.usfirst.frc.team3926.robot.commands.Gears;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

import java.util.concurrent.TimeUnit;

/***********************************************************************************************************************
 * Puts the gear placement system's motor up (letting the gear come out)
 * @author William Kluge
 *     <p>
 *     Contact: klugewilliam@gmail.com
 *     </p>
 **********************************************************************************************************************/
public class GearPlacementMotorUp extends Command {

    /** Time (in system nano time) that this command started */
    private long startTime;

    /**
     * Constructs the GearPlacementMotorUp command requiring {@link Robot#gearPlacer}
     */
    public GearPlacementMotorUp() {

        requires(Robot.gearPlacer);

        startTime = System.nanoTime();

    }

    /**
     * Sets the speed of the gear placement motor
     */
    protected void initialize() {

        Robot.gearPlacer.setCollectorSpeed(RobotMap.GEAR_PLACEMENT_SPEED);

    }

    /**
     * No code needs to be executed during this command
     */
    protected void execute() {

        Robot.gearPlacer.setCollectorSpeed(RobotMap.GEAR_PLACEMENT_SPEED);

    }

    /**
     * @return If the amount of time specified by {@link RobotMap#GEAR_MOTOR_UP_TIME} or if the user has manually
     * canceled the command
     */
    protected boolean isFinished() {

        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) >= RobotMap.GEAR_MOTOR_UP_TIME ||
               Robot.oi.cancelCommand.get();
    }

    /**
     * Turns off the gear placement motor
     */
    protected void end() {

        Robot.gearPlacer.setCollectorSpeed(0);

    }

    /**
     * Turns off the gear placement motor
     */
    protected void interrupted() {

        Robot.gearPlacer.setCollectorSpeed(0);

    }

}
