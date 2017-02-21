package org.usfirst.frc.team3926.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.commands.Gears.CorrectGearPosition;

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

        if (Robot.startPosition != Robot.StartPositions.BlueCenter ||
            Robot.startPosition != Robot.StartPositions.RedCenter)
            addSequential(new CorrectGearPosition());

        addSequential(new PlaceGear());

    }

}