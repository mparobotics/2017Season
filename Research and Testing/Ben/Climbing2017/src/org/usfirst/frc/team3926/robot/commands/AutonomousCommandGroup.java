package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommandGroup extends CommandGroup {
	public AutonomousCommandGroup() {
		addSequential(new DriveForwardCommand());
		addSequential(new TurnCommand());
		addSequential(new DriveForwardCommand());
		addSequential(new StoppingCommand());
	}
}
