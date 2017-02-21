package org.usfirst.frc.team3926.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.Driving.AutoStraightDrive;
import org.usfirst.frc.team3926.robot.commands.Driving.ContinueUntilRange;
import org.usfirst.frc.team3926.robot.commands.Driving.IndividualSideDrive;
import org.usfirst.frc.team3926.robot.commands.Gears.GearPlacementMotorDown;
import org.usfirst.frc.team3926.robot.commands.Gears.GearPlacementMotorUp;
import org.usfirst.frc.team3926.robot.commands.Gears.GetGearTrajectory;

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

        addSequential(new GetGearTrajectory());
        addSequential(new ContinueUntilRange(RobotMap.GEAR_PLACEMENT_VOLTAGE), RobotMap.GEAR_PLACEMENT_TIMEOUT);
        addSequential(new GearPlacementMotorUp());
        addSequential(new IndividualSideDrive(RobotMap.GEAR_TURN_LEFT_DISTANCE, RobotMap.GEAR_TURN_RIGHT_DISTANCE));
        addSequential(new AutoStraightDrive(RobotMap.GEAR_BACKUP_DISTANCE));
        addSequential(new GearPlacementMotorDown());

    }
}