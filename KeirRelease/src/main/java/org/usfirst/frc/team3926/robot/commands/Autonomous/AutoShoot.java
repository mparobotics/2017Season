package org.usfirst.frc.team3926.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.HighGoal.CenterOnHighGoal;
import org.usfirst.frc.team3926.robot.commands.HighGoal.ShootAndFeed;

/**
 * Command group to drive and shoot balls in autonomous
 * TODO handle turning
 */
public class AutoShoot extends CommandGroup {

    public AutoShoot() {

        addSequential(new DriveForward(RobotMap.AUTONOMOUS_SHOOT_DRIVE_DISTANCE));
        addSequential(new CenterOnHighGoal());
        addSequential(new ShootAndFeed(), RobotMap.AUTONOMOUS_SHOOT_TIMEOUT);

    }
}