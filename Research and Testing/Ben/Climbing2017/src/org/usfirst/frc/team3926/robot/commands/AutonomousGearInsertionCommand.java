package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * Makes the robot drive forward 10 meters, then turn, then drive forward 10 meters again, then stop the robot
 *
 * @author Benjamin Lash
 */
public class AutonomousGearInsertionCommand extends CommandGroup {

    public AutonomousGearInsertionCommand() {

        SendableChooser robotPosition = new SendableChooser();
        robotPosition.addObject("Left Of Center", new GearInsertionFromLeftOfCenter());
        robotPosition.addObject("Center", new GearInsertionFromCenter());
        robotPosition.addObject("Right Of Center", new GearInsertionFromRightOfCenter());

        /*
        addSequential(new DriveForward());
        addSequential(new DecelerationTurn());
        addSequential(new DriveForward());
        addSequential(new InsertingGear());
        addSequential(new BackUpRobot());
        addSequential(new StopRobot());
        */

    }

}