package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team3926.robot.commands.Autonomous.DriveForward;
import org.usfirst.frc.team3926.robot.commands.Climb;
import org.usfirst.frc.team3926.robot.commands.ContinueTrajectory;
import org.usfirst.frc.team3926.robot.commands.Gears.CenterOnGears;
import org.usfirst.frc.team3926.robot.commands.HighGoal.CenterOnHighGoal;
import org.usfirst.frc.team3926.robot.commands.HighGoal.CollectBalls;
import org.usfirst.frc.team3926.robot.commands.HighGoal.DriveTowardsHighGoal;
import org.usfirst.frc.team3926.robot.commands.HighGoal.ShootAndFeed;
import org.usfirst.frc.team3926.robot.triggers.SaveTrajectoryTrigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * TODO make it easier to switch what joystick something is on
 * TODO Add auxiliary driver stick
 */
public class OI {

    /** Joystick on the drivers right side (or the XBox controller for driving the robot) */
    public Joystick              driverPrimaryStick;
    /** Joystick on the drivers left side (or not used) */
    public Joystick              driverSecondaryStick;
    /** Button to make the robot drive straight */
    public Button   straightMode;
    /** Button to reduce the speed of the robot by {@link RobotMap#DRIVE_SAFETY_FACTOR} */
    public Button   safetyMode;
    /** Button to signify that the robot has made an incorrect action based off of a contour */
    public Button   contourError;
    /** Button to center the robot on the vision target */
    public Button                centerOnHighGoal;
    /** Button to activate driving the robot towards the center of a vision target */
    public Button                driveToHighGoal;
    /** Button to center on the gear's vision target */
    public Button                centerOnGear;
    /** Button to drive towards the gear's vision target */
    public Button                driveToGear;
    /** Button to use the shooter */
    public Button                shoot;
    /** Button to climb */
    public Button                climb;
    /** Button to collect balls */
    public Button                collectBalls;
    /** Trigger to continue driving based on the robot's current trajectory */
    public SaveTrajectoryTrigger saveTrajectoryTrigger;

    /**
     * Constructs the OI class as specified by various options in {@link RobotMap}
     */
    OI() {

        if (RobotMap.XBOX_DRIVE_CONTROLLER) {
            driverPrimaryStick = new Joystick(RobotMap.XBOX_PORT);
            straightMode = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_STRAIGHT_MODE_BUTTON);
            safetyMode = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_SAFETY_MODE_BUTTON);
            contourError = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_CONTOUR_ERROR_BUTTON);
            centerOnHighGoal = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_CENTER_ON_HIGH_GOAL_BUTTON);
            driveToHighGoal = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_DRIVE_TO_HIGH_GOAL_BUTTON);
            centerOnGear = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_CENTER_ON_GEAR_BUTTON);
            driveToGear = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_DRIVE_TO_GEAR_BUTTON);
            shoot = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_SHOOT_BUTTON);
            climb = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_CLIMB_BUTTON);
            collectBalls = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_COLLECT_BUTTON);
        } else {
            driverPrimaryStick = new Joystick(RobotMap.RIGHT_STICK_PORT);
            driverSecondaryStick = new Joystick(RobotMap.LEFT_STICK_PORT);
            straightMode = new JoystickButton(driverSecondaryStick, RobotMap.STRAIGHT_MODE_BUTTON);
            safetyMode = new JoystickButton(driverPrimaryStick, RobotMap.SAFTEY_MODE_BUTTON);
            contourError = new JoystickButton(driverPrimaryStick, RobotMap.CONTOUR_ERROR_BUTTON);
            centerOnHighGoal = new JoystickButton(driverPrimaryStick, RobotMap.CENTER_ON_HIGH_BUTTON_BUTTON);
            driveToHighGoal = new JoystickButton(driverPrimaryStick, RobotMap.DRIVE_TO_HIGH_GOAL_BUTTON);
            centerOnGear = new JoystickButton(driverSecondaryStick, RobotMap.CENTER_ON_GEAR_BUTTON);
            driveToGear = new JoystickButton(driverSecondaryStick, RobotMap.DRIVE_TO_GEAR_BUTTON);
            shoot = new JoystickButton(driverSecondaryStick, RobotMap.SHOOT_BUTTON);
            climb = new JoystickButton(driverSecondaryStick, RobotMap.CLIMB_BUTTON);
            collectBalls = new JoystickButton(driverSecondaryStick, RobotMap.BALL_COLLECT_BUTTON);
        }

        centerOnHighGoal.whileHeld(new DriveTowardsHighGoal());
        driveToHighGoal.whileHeld(new CenterOnHighGoal());
        driveToGear.whileHeld(new DriveForward());
        centerOnGear.whileHeld(new CenterOnGears());
        shoot.whileHeld(new ShootAndFeed());
        climb.whileHeld(new Climb());
        collectBalls.whileHeld(new CollectBalls());
        saveTrajectoryTrigger.whileActive(new ContinueTrajectory());

    }

}

