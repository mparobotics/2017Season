package org.usfirst.frc.team3926.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.Driving.AutoStraightDrive;
import org.usfirst.frc.team3926.robot.commands.Driving.IndividualSideDrive;

/***********************************************************************************************************************
 * TODO command group to lock onto the gear vision target and drive to it
 * @author William Kluge
 *     <p>
 *     Contact: klugewilliam@gmail.com
 *     </p>
 **********************************************************************************************************************/
public class AutoPlaceGear extends CommandGroup {

    /**
     * Constructs the AutoPlaceGear command group
     */
    public AutoPlaceGear() {

        addSequential(new AutoStraightDrive(RobotMap.AUTONOMOUS_DRIVE_FORWARD_DISTANCE));
        addSequential(new IndividualSideDrive(RobotMap.GEAR_TURN_LEFT_DISTANCE, RobotMap.GEAR_TURN_RIGHT_DISTANCE));
        addSequential(new PlaceGear());

    }

}