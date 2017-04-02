package org.usfirst.frc.team3926.robot.commands.HighGoal;

import edu.wpi.first.wpilibj.command.CommandGroup;

/***********************************************************************************************************************
 * Uses {@link Shoot}, {@link CenterOnHighGoal}, and {@link AgitatorFeed} in parallel to shootAndFeed the ball, align the
 * drive base, and feed more balls into the shooter
 * @author William Kluge
 *     <p>
 *     Contact: klugewilliam@gmail.com
 *     </p>
 **********************************************************************************************************************/
public class ShootFeedAlign extends CommandGroup {

    /**
     * Constructs the ShootAndFeed command group
     */
    public ShootFeedAlign() {

        addParallel(new Shoot());
        addParallel(new AgitatorFeed());
        addParallel(new CenterOnHighGoal());

    }
}