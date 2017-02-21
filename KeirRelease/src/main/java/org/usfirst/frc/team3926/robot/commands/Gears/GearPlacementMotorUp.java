package org.usfirst.frc.team3926.robot.commands.Gears;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/***********************************************************************************************************************
 * Puts the gear placement system's motor up (letting the gear come out)
 * @author William Kluge
 *     <p>
 *     Contact: klugewilliam@gmail.com
 *     </p>
 **********************************************************************************************************************/
public class GearPlacementMotorUp extends Command {

    /**
     * Constructs the GearPlacementMotorUp command requiring {@link Robot#gearPlacer}
     */
    public GearPlacementMotorUp() {

        requires(Robot.gearPlacer);

    }

    /**
     * Sets the speed of the gear placement motor
     */
    protected void initialize() {

        Robot.gearPlacer.set(RobotMap.GEAR_PLACEMENT_SPEED);

        try {
            wait(RobotMap.GEAR_MOTOR_UP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * No code needs to be executed during this command because it will stop directly after initialize is finished
     * waiting
     */
    protected void execute() {

    }

    /**
     * @return If the amount of time specified by {@link RobotMap#GEAR_MOTOR_UP_TIME} or if the user has manually
     * canceled the command
     */
    protected boolean isFinished() {

        return true;
    }

    /**
     * Turns off the gear placement motor
     */
    protected void end() {

        Robot.gearPlacer.set(0);

    }

    /**
     * Turns off the gear placement motor
     */
    protected void interrupted() {

        Robot.gearPlacer.set(0);

    }

}
