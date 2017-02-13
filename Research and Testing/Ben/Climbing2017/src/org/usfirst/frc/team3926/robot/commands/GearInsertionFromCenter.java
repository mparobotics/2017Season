package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3926.robot.RobotMap;

public class GearInsertionFromCenter extends CommandGroup {

    /**
     * Moves robot to airship
     * Deposits Gear
     * Moves robot to base line
     */
    public GearInsertionFromCenter() {

        addSequential(new DriveForward(RobotMap.DISTANCE_FROM_WALL_TO_AIRSHIP));
        addSequential(new DecelerationTurn(90));
        addSequential(new DriveForward(RobotMap.DISTANCE_TRAVELED_AFTER_DEPOSITING_GEAR));
        addSequential(new DecelerationTurn(-90));
        addSequential(new DriveForward(RobotMap.DISTANCE_FROM_WALL_TO_AIRSHIP - RobotMap.DISTANCE_TO_BASELINE));

    }
}