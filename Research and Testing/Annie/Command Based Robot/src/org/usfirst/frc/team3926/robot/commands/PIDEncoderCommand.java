package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * keeps the motor at a constant preset speed
 */
public class PIDEncoderCommand extends Command {

    /**
     * declares dependencies
     */
    public PIDEncoderCommand() {

        requires(Robot.PIDencoder);

    }

    /**
     * enables the PID loop
     */
    protected void initialize() {

        Robot.PIDencoder.enable();

    }

    /**
     * nothing needed
     */
    protected void execute() {


    }

    /**
     * should stay running
     * @return
     */
    protected boolean isFinished() {

        return false;
    }

    /**
     * disables the PID loop
     */
    protected void end() {

        Robot.PIDencoder.disable();

    }

    /**
     * nothing needed
     */
    protected void interrupted() {

    }
}
