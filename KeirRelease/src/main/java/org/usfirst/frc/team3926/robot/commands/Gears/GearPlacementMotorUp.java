package org.usfirst.frc.team3926.robot.commands.Gears;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * Puts the gear placement system's motor up (letting the gear come out)
 * TODO finish
 */
public class GearPlacementMotorUp extends Command {

    /**
     * Constructs the GearPlacementMotorUp command requiring {@link Robot#gearPlacer}
     */
    public GearPlacementMotorUp() {

        requires(Robot.gearPlacer);
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

        return false;
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
