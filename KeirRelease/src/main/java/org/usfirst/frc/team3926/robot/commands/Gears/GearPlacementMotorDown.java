package org.usfirst.frc.team3926.robot.commands.Gears;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/***********************************************************************************************************************
 * Puts the gear placement system's motor down (blocking the gear from falling out)
 * @author William Kluge
 *     <p>
 *     Contact: klugewilliam@gmail.com
 *     </p>
 **********************************************************************************************************************/
public class GearPlacementMotorDown extends Command {

    /**
     * Constructs the GearPlacementMotorDown command requiring {@link Robot#gearPlacer}
     */
    public GearPlacementMotorDown() {

        requires(Robot.gearPlacer);

    }

    /**
     * Sets the speed of the gear placement motor
     */
    protected void initialize() {

        Robot.gearPlacer.set(RobotMap.GEAR_PLACEMENT_SPEED * -1);

        try {
            wait(RobotMap.GEAR_MOTOR_DOWN_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * No code needs to be executed during this command
     */
    protected void execute() {

    }

    /**
     * Tells if the amount of time desired to run the motor has passed
     *
     * @return If the amount of time specified by {@link RobotMap#GEAR_MOTOR_DOWN_TIME} or if the user has manually
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
