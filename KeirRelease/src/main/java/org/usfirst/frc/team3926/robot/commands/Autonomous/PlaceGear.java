package org.usfirst.frc.team3926.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.Driving.DriveBackwards;
import org.usfirst.frc.team3926.robot.commands.Driving.Turn;
import org.usfirst.frc.team3926.robot.commands.Gears.GearPlacementMotorDown;
import org.usfirst.frc.team3926.robot.commands.Gears.GearPlacementMotorUp;

/**
 * Command group to drive to the gear target and place it. This can be called during another autonomous group or during
 * teleop
 * TODO test
 */
public class PlaceGear extends CommandGroup {

    public PlaceGear() {

        addSequential(new GearVisionDrive());
        addSequential(new GearPlacementMotorUp());
        addSequential(new Turn(RobotMap.GEAR_TURN_LEFT_DISTANCE, RobotMap.GEAR_TURN_RIGHT_DISTANCE,
                               RobotMap.GEAR_TURN_LEFT));
        addSequential(new DriveBackwards(RobotMap.GEAR_BACKUP_DISTANCE));
        addSequential(new GearPlacementMotorDown());

    }
}