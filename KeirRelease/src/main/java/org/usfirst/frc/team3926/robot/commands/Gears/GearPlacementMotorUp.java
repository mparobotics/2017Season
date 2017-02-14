package org.usfirst.frc.team3926.robot.commands.Gears;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

import java.util.concurrent.TimeUnit;

/**
 * Puts the gear placement system's motor up (letting the gear come out)
 */
public class GearPlacementMotorUp extends Command {

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

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    /**
     * @return false
     */
    protected boolean isFinished() {

        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) >= RobotMap.GEAR_MOTOR_UP_TIME;
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
