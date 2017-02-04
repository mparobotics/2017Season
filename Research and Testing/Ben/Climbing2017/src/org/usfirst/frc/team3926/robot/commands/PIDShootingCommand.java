package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * Created by blash20 on 2/4/17.
 */
public class PIDShootingCommand extends Command {

    public PIDShootingCommand() {

    }

    protected void initialize() {

        Robot.pidSubsystem.enable();

    }

    protected void execute() {

    }

    protected void interrupted() {

    }

    protected boolean isFinished() {

        return false;

    }

    protected void end() {

        Robot.pidSubsystem.disable();

    }
}
