package org.usfirst.frc.team3926.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * TODO command group to lock onto the gear vision target and drive to it
 */
public class AutoPlaceGear extends CommandGroup {

    /**
     * Constructs the AutoPlaceGear command group
     */
    public AutoPlaceGear() {

        //TODO drive forward
        //TODO turn to vision target
        addSequential(new PlaceGear());

    }

}