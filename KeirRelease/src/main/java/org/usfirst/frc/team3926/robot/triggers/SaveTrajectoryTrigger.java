package org.usfirst.frc.team3926.robot.triggers;

import edu.wpi.first.wpilibj.buttons.Trigger;
import org.usfirst.frc.team3926.robot.Robot;

/**
 * Trigger to save the trajectory of the robot and continue along that path
 * <p>
 * Note: This is a temporary solution that only supports the XBox controller as the primary stick
 * </p>
 */
public class SaveTrajectoryTrigger extends Trigger {

    /**
     * @return Whether or not the XBox controller
     */
    public boolean get() {

        return Robot.oi.driverPrimaryStick.getPOV() == 0;

    }

}
