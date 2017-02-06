package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3926.robot.Robot;

/**
 *
 */
public class Autonomus extends CommandGroup {
    
    public  Autonomus() {

        Robot.rangeFinderBackupSystem.rangeFinderDistance();
    }
}
