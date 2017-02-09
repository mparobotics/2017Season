package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * the robot will drive towards the airship
 * if the range finder does not see the airship the robot will turn around until it sees it
 * once the robot is close enough to the airship (5 inches) it will stop and shoot the ball
 */
public class RangeFinderCommandGroup extends CommandGroup {

    public RangeFinderCommandGroup() {

        addSequential(new RangeFinderDriveFoward());
        addSequential(new RangeFinderTurn());
        addSequential(new RangeFinderShoot());

    }
}