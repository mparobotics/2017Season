package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 *  Drives to airship and inserts gear
 *  Goes to baseline
 */
public class GearInsertionFromLeftOfCenter extends CommandGroup {

    public GearInsertionFromLeftOfCenter() {

        addSequential(new DriveForward(RobotMap.DISTANCE_FROM_WALL_TO_AIRSHIP/2));
        addSequential(new DecelerationTurn(-45));
        addSequential(new DriveForward( RobotMap.SECOND_AIRSHIP_JOUNEY_LEG_DISTANCE));
        addSequential(new DecelerationTurn(90));
        addSequential(new DriveForward(RobotMap.DISTANCE_TRAVELED_AFTER_DEPOSITING_GEAR));

        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}