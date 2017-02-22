package org.usfirst.frc.team3926.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.Driving.AutoStraightDrive;
import org.usfirst.frc.team3926.robot.commands.Driving.ContinueUntilRange;
import org.usfirst.frc.team3926.robot.commands.Gears.DriveToGears;
import org.usfirst.frc.team3926.robot.commands.Gears.GearPlacementMotorDown;

/***********************************************************************************************************************
 * Command group to drive to the gear target and place it. This can be called during another autonomous group or during
 * teleop
 * @author William Kluge
 *     <p>
 *     Contact: klugewilliam@gmail.com
 *     </p>
 * TODO figure out actual values
 **********************************************************************************************************************/
public class PlaceGear extends CommandGroup {

    public PlaceGear() {

        addSequential(new DriveToGears());
        addSequential(new ContinueUntilRange(RobotMap.GEAR_PLACEMENT_VOLTAGE), RobotMap.GEAR_PLACEMENT_TIMEOUT);
        addParallel(new GearPlacementMotorDown());
        //addSequential(new IndividualSideDrive(RobotMap.GEAR_TURN_LEFT_DISTANCE, RobotMap.GEAR_TURN_RIGHT_DISTANCE));
        addParallel(new AutoStraightDrive(RobotMap.GEAR_BACKUP_DISTANCE));
        //addSequential(new GearPlacementMotorUp());

    }
}