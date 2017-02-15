package org.usfirst.frc.team3926.robot.commands.HighGoal;

import edu.wpi.first.wpilibj.command.CommandGroup;

/***********************************************************************************************************************
 * Uses {@link Shoot} and {@link AgitatorFeed} in parallel to shoot the ball and feed more balls into the shooter
 * @author William Kluge
 *     <p>
 *     Contact: klugewilliam@gmail.com
 *     </p>
 **********************************************************************************************************************/
public class ShootAndFeed extends CommandGroup {

    /**
     * Constructs the ShootAndFeed command group
     */
    public ShootAndFeed() {

        addParallel(new Shoot());
        addParallel(new AgitatorFeed());

    }

}