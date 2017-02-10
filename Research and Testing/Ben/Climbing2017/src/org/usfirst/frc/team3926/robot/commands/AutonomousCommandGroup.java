package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Makes the robot drive forward 10 meters, then turn, then drive forward 10 meters again, then stop the robot
 *
 * @author Benjamin Lash
 */
public class AutonomousCommandGroup extends CommandGroup {

    public AutonomousCommandGroup() {

        addSequential(new DriveForward());
        addSequential(new TurnRobot());
        addSequential(new DriveForward());
        addSequential(new StopRobot());

    }
}
