package org.usfirst.frc.team3926.robot.commands.Gears;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.Autonomous.DriveForward;
import org.usfirst.frc.team3926.robot.commands.Driving.IndividualSideDrive;
import org.usfirst.frc.team3926.robot.commands.ResetEncoders;

/***********************************************************************************************************************
 * Moves the robot to a point where it can place a gear in autonomous on either of the side pegs if it isnt in the
 * center
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 **********************************************************************************************************************/
public class CorrectGearPosition extends CommandGroup {

    public CorrectGearPosition() {

        addSequential(new ResetEncoders());
        addSequential(new DriveForward(RobotMap.AUTO_GEAR_NOT_CENTER_FORWARD_DISTANCE));

        if (Robot.startPosition == Robot.StartPositions.BlueLeft ||
            Robot.startPosition == Robot.StartPositions.RedLeft) {

            addSequential(new IndividualSideDrive(RobotMap.AUTO_GEAR_OUTER_SIDE_TURN_DISTANCE,
                                                  RobotMap.AUTO_GEAR_INNER_SIDE_TURN_DISTANCE));

        } else {

            addSequential(new IndividualSideDrive(RobotMap.AUTO_GEAR_INNER_SIDE_TURN_DISTANCE,
                                                  RobotMap.AUTO_GEAR_OUTER_SIDE_TURN_DISTANCE));

        }

    }
}