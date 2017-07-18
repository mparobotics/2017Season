package org.usfirst.frc.team3926.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

import static org.usfirst.frc.team3926.robot.Robot.oi;

/***********************************************************************************************************************
 * Command to execute methods of {@link org.usfirst.frc.team3926.robot.subsystems.DriveControl}
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 *         TODO add reverse direction method
 ***********************************************************************************************************************/
public class UserDriveTank extends Command {

    private boolean invertDirection;
    private boolean toggleButtonLastState;
    private boolean toggleSafety;
    private boolean toggleButtonLastState;

    /**
     * Constructs the UserDriveTank command requiring {@link Robot#driveControl}
     */
    public UserDriveTank() {

        requires(Robot.driveControl);
    }

    /**
     * No initialization code is needed
     */
    protected void initialize() {

    }

    /**
     * Calls
     * {@link org.usfirst.frc.team3926.robot.subsystems.DriveControl#driveTank(double, double, boolean, boolean, boolean)}
     * to control the drive base
     */
    protected void execute() {

        if (Robot.oi.invertDriveDirection.get()) {

            if (!toggleButtonLastState)
                invertDirection = !invertDirection;

            toggleButtonLastState = true;

        } else {

            toggleButtonLastState = false;

        }

        if (RobotMap.XBOX_DRIVE_CONTROLLER)
            Robot.driveControl.driveTank(oi.driverPrimaryStick.getRawAxis(RobotMap.XBOX_RIGHT_SPEED_AXIS),
                                         oi.driverPrimaryStick.getRawAxis(RobotMap.XBOX_LEFT_SPEED_AXIS),
                                         oi.straightMode.get(),
                                         oi.safetyMode.get(), false);
        else {
            double rightSpeed = (RobotMap.SWITCH_CONTROLLER_DRIVE_SIDE) ? oi.driverPrimaryStick.getY() : oi
                    .driverSecondaryStick.getY();
            double leftSpeed = (RobotMap.SWITCH_CONTROLLER_DRIVE_SIDE) ? oi.driverSecondaryStick.getY() :
                               oi.driverPrimaryStick.getY();
            Robot.driveControl.driveTank(rightSpeed, leftSpeed,
                                         oi.straightMode.get(), oi.safetyMode.get(), invertDirection);
        }

        if(toggleButtonLastState != oi.safetyButton.get())
            toggleSafety = !toggleSafety;


        toggleButtonLastState = io.safteyButton.get();
    }

    /**
     * This is the default command for {@link org.usfirst.frc.team3926.robot.subsystems.DriveControl}, so this isn't
     * needed
     *
     * @return false
     */
    protected boolean isFinished() {

        return false;
    }

    /**
     * This command never finishes
     */
    protected void end() {

    }

    /**
     * Sets the speeds of the drive train to 0
     */
    protected void interrupted() {

        Robot.driveControl.reset();

    }

}
