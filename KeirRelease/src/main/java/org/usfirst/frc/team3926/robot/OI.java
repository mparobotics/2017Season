package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team3926.robot.commands.Autonomous.PlaceGear;
import org.usfirst.frc.team3926.robot.commands.Climb;
import org.usfirst.frc.team3926.robot.commands.Debugging.*;
import org.usfirst.frc.team3926.robot.commands.Gears.CenterOnGears;
import org.usfirst.frc.team3926.robot.commands.Gears.GearPlacementMotorDown;
import org.usfirst.frc.team3926.robot.commands.Gears.GearPlacementMotorUp;
import org.usfirst.frc.team3926.robot.commands.HighGoal.*;
import org.usfirst.frc.team3926.robot.triggers.SaveTrajectoryTrigger;

/***********************************************************************************************************************
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * @author William Kluge
 *      <p>
 *      Contact: klugewilliam@gmail.com
 *      </p>
 * TODO Add auxiliary driver stick
 * TODO add SmartDashboard joystick choice
 **********************************************************************************************************************/
public class OI {

    /** Joystick on the drivers right side (or the XBox controller for driving the robot) */
    public Joystick              driverPrimaryStick;
    /** Joystick on the drivers left side (or not used) */
    public Joystick              driverSecondaryStick;
    /** Joystick to use for the auxiliary driver */
    public Joystick              auxillaryStick;
    /** Button to make the robot drive straight */
    public Button                straightMode;
    /** Button to reduce the speed of the robot by {@link RobotMap#DRIVE_SAFETY_FACTOR} */
    public Button                safetyMode;
    /** Button to signify that the robot has made an incorrect action based off of a contour */
    public Button                contourError;
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
    ///// Debugging Buttons /////
    /** Prints the value of the drive train's right encoder and resets its value */
    public Button                rightDrivetrainEncoder;
    /** Prints the value of the drive train's left encoder and resets its value */
    public Button                leftDrivetrainEncoder;
    /** Prints the value of the drive train's rangefinder */
    public Button   drivetrainRangefinder;
    /** Toggles the inverted direction of the drivetrains */
    public Button   invertDriveDirection;
    /** Runs the shooter backwards */
    public Button   reverseShooter;
    /** Cancel the current autonomous command */
    public Button   cancelCommand;
    /** Runs the gear motor to put the arm up */
    public Button   gearMotorUp;
    /** Runs the gear motor to put the arm down */
    public Button   gearMotorDown;
    /***/
    public Button   placeGear;
    public Joystick debugStick;
    public Button   printShooterValues, increaseShooterSpeed, decreaseShooterSpeed, debugShoot;

    /**
     * Constructs the OI class as specified by various options in {@link RobotMap}
     * <p>
     * Note: LiveWindow control is also handled here
     * </p>
     */
    OI() {

        ///// Shooter /////
        LiveWindow.addActuator("shooter", "Shooter PID Loop", Robot.shooter.getPIDController());
        Robot.shooter.encoder.startLiveWindowMode();
        LiveWindow.addSensor("shooter", "Shooter Encoder", Robot.shooter.encoder);
        ///// Drive Control Sensors /////
        LiveWindow.addSensor("drive", "Rangefinder", Robot.driveControl.rangefinder);
        //LiveWindow.addSensor("drive", "Left Encoder", Robot.driveControl.leftEncoder);
        //LiveWindow.addSensor("drive", "Right Encoder", Robot.driveControl.rightEncoder);

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
            saveTrajectoryTrigger = new SaveTrajectoryTrigger();
        } else {
            ///// Stick Initiation /////
            driverPrimaryStick = new Joystick(RobotMap.RIGHT_STICK_PORT);
            driverSecondaryStick = new Joystick(RobotMap.LEFT_STICK_PORT);
            ///// Primary Stick /////
            safetyMode = new JoystickButton(driverPrimaryStick, RobotMap.SAFETY_MODE_BUTTON);
            contourError = new JoystickButton(driverPrimaryStick, RobotMap.CONTOUR_ERROR_BUTTON);
            centerOnHighGoal = new JoystickButton(driverPrimaryStick, RobotMap.CENTER_ON_HIGH_BUTTON_BUTTON);
            driveToHighGoal = new JoystickButton(driverPrimaryStick, RobotMap.DRIVE_TO_HIGH_GOAL_BUTTON);
            shoot = new JoystickButton(driverPrimaryStick, RobotMap.SHOOT_BUTTON);
            invertDriveDirection = new JoystickButton(driverPrimaryStick, RobotMap.TOGGLE_INVERT_DRIVE_BUTTON);
            reverseShooter = new JoystickButton(driverPrimaryStick, RobotMap.REVERSE_SHOOT_DIRECTION);
            gearMotorDown = new JoystickButton(driverPrimaryStick, RobotMap.GEAR_DOWN_BUTTON);
            gearMotorUp = new JoystickButton(driverPrimaryStick, RobotMap.GEAR_UP_BUTTON);
            ///// Secondary Stick /////
            straightMode = new JoystickButton(driverSecondaryStick, RobotMap.STRAIGHT_MODE_BUTTON);
            centerOnGear = new JoystickButton(driverSecondaryStick, RobotMap.CENTER_ON_GEAR_BUTTON);
            driveToGear = new JoystickButton(driverSecondaryStick, RobotMap.DRIVE_TO_GEAR_BUTTON);
            climb = new JoystickButton(driverSecondaryStick, RobotMap.CLIMB_BUTTON);
            collectBalls = new JoystickButton(driverSecondaryStick, RobotMap.BALL_COLLECT_BUTTON);
            cancelCommand = new JoystickButton(driverSecondaryStick, RobotMap.CANCEL_COMMAND);
            placeGear = new JoystickButton(driverPrimaryStick, 11);
            ///// Debugging Buttons /////
            if (RobotMap.DEBUG) {
                rightDrivetrainEncoder = new JoystickButton(driverSecondaryStick, RobotMap.RIGHT_DRIVE_ENCODER_CHECK);
                leftDrivetrainEncoder = new JoystickButton(driverPrimaryStick, RobotMap.LEFT_DRIVE_ENCODER_CHECK);
                drivetrainRangefinder = new JoystickButton(driverPrimaryStick, RobotMap.RANGEFINDER_CHECK);
                rightDrivetrainEncoder.whenPressed(new RightDriveEncoderCheck());
                leftDrivetrainEncoder.whenPressed(new LeftDriveEncoderCheck());
                drivetrainRangefinder.whenPressed(new RangefinderCheck());
                debugStick = new Joystick(2);
                printShooterValues = new JoystickButton(debugStick, 4);
                printShooterValues.whenPressed(new PrintShootingInfo());
                increaseShooterSpeed = new JoystickButton(debugStick, 1);
                decreaseShooterSpeed = new JoystickButton(debugStick, 2);
                debugShoot = new JoystickButton(debugStick, 3);
                debugShoot.whileHeld(new DebugShoot());

            }
        }

        ///// High Goal Commands /////
        centerOnHighGoal.whileHeld(new DriveTowardsHighGoal());
        driveToHighGoal.whileHeld(new CenterOnHighGoal());
        shoot.whileHeld(new ShootAndFeed());
        collectBalls.whileHeld(new CollectBalls());
        reverseShooter.whileHeld(new ShooterReverse());
        ///// Gear Commands /////
        driveToGear.whileHeld(new PlaceGear());
        centerOnGear.whileHeld(new CenterOnGears());
        gearMotorDown.whileHeld(new GearPlacementMotorDown());
        gearMotorUp.whileHeld(new GearPlacementMotorUp());
        ///// Misc Commands /////
        climb.whileHeld(new Climb());
        placeGear.whenPressed(new PlaceGear());

    }

}

