package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team3926.robot.commands.Autonomous.PlaceGear;
import org.usfirst.frc.team3926.robot.commands.Climb;
import org.usfirst.frc.team3926.robot.commands.Debugging.LeftDriveEncoderCheck;
import org.usfirst.frc.team3926.robot.commands.Debugging.RangefinderCheck;
import org.usfirst.frc.team3926.robot.commands.Debugging.RightDriveEncoderCheck;
import org.usfirst.frc.team3926.robot.commands.HighGoal.CollectBalls;
import org.usfirst.frc.team3926.robot.commands.HighGoal.ShootAndFeed;
import org.usfirst.frc.team3926.robot.commands.HighGoal.ShootFeedAlign;
import org.usfirst.frc.team3926.robot.commands.HighGoal.ShooterReverse;
import org.usfirst.frc.team3926.robot.triggers.SaveTrajectoryTrigger;

import static org.usfirst.frc.team3926.robot.RobotMap.COMPETITION_DRIVE_CONFIG;
import static org.usfirst.frc.team3926.robot.RobotMap.PLACE_GEAR_BUTTON;

/***********************************************************************************************************************
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * @author William Kluge
 *      <p>
 *      Contact: klugewilliam@gmail.com
 *      </p>
 * List of possible commands
 *   - Place gear (Secondary driver stick)
 *   - Shoot (without alignment) (Auxillary driver stick)
 *   - Shoot (with alignment) (Auxillary driver stick)
 *   - Reverse shooter (Auxillary driver stick)
 *   - Climb (Auxillary driver stick)
 *   - Collect balls (Auxillary driver stick)
 *   - Reverse ball collector (Auxillary driver stick)
 *   - Inverse drive direction (Primary driver stick)
 *   - Saftey mode (Primary driver stick)
 *   - Straight mode (Secondary driver stick)
 *   - Cancel command (Primary driver stick)
 **********************************************************************************************************************/
public class OI {

    /** Joystick on the drivers right side (or the XBox controller for driving the robot) */
    public Joystick              driverPrimaryStick;
    /** Joystick on the drivers left side (or not used) */
    public Joystick              driverSecondaryStick;
    /** Joystick to use for the auxiliary driver */
    public Joystick              auxiliaryStick;
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
    public Button                shootAndFeed;
    /** Button to shoot, feed, and align the robot with the high goal */
    public Button                shootFeedAlign;
    /** Button to climb */
    public Button                climb;
    /** Button to collect balls */
    public Button                collectBalls;
    /** Button to reverse ball collector */
    public Button                reverseCollectBalls;
    /** Trigger to continue driving based on the robot's current trajectory */
    public SaveTrajectoryTrigger saveTrajectoryTrigger;
    ///// Debugging Buttons /////
    /** Prints the value of the drive train's right encoder and resets its value */
    public Button                rightDrivetrainEncoder;
    /** Prints the value of the drive train's left encoder and resets its value */
    public Button                leftDrivetrainEncoder;
    /** Prints the value of the drive train's rangefinder */
    public Button                drivetrainRangefinder;
    /** Toggles the inverted direction of the drivetrains */
    public Button                invertDriveDirection;
    /** Runs the shooter backwards */
    public Button                reverseShooter;
    /** Cancel the current autonomous command */
    public Button                cancelCommand;
    /** Runs the gear motor to put the arm up */
    public Button                gearMotorUp;
    /** Runs the gear motor to put the arm down */
    public Button                gearMotorDown;
    /** Uses vision tracking to center the robot on the gear peg and drives towards it */
    public Button                placeGear;
    public Joystick              debugStick;
    public Button                printShooterValues, increaseShooterSpeed, decreaseShooterSpeed, debugShoot;
    public Button driverShootWithAlignment;
    public Button driverShoot;
    public Button driverReverseShoot;
    public Button driverClimb;
    public Button driverCollectBalls;
    public Button driverReverseBallCollector;

    /**
     * Constructs the OI class as specified by various options in {@link RobotMap}
     * <p>
     * Note: LiveWindow control is also handled here
     * </p>
     */
    OI() {

        ///// Shooter /////
        //LiveWindow.addActuator("shooter", "Shooter PID Loop", Robot.shooter.getPIDController());
        //Robot.shooter.encoder.startLiveWindowMode();
        //LiveWindow.addSensor("shooter", "Shooter Encoder", Robot.shooter.encoder);
        ///// Drive Control Sensors /////
        LiveWindow.addSensor("drive", "Rangefinder", Robot.driveControl.rangefinder);
        //LiveWindow.addSensor("drive", "Left Encoder", Robot.driveControl.leftEncoder);
        //LiveWindow.addSensor("drive", "Right Encoder", Robot.driveControl.rightEncoder);

        if (RobotMap.XBOX_DRIVE_CONTROLLER) { // Debug to just put everything on an XBOX controller
            driverPrimaryStick = new Joystick(RobotMap.XBOX_PORT);
            straightMode = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_STRAIGHT_MODE_BUTTON);
            safetyMode = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_SAFETY_MODE_BUTTON);
            contourError = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_CONTOUR_ERROR_BUTTON);
            centerOnHighGoal = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_CENTER_ON_HIGH_GOAL_BUTTON);
            driveToHighGoal = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_DRIVE_TO_HIGH_GOAL_BUTTON);
            centerOnGear = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_CENTER_ON_GEAR_BUTTON);
            driveToGear = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_DRIVE_TO_GEAR_BUTTON);
            shootAndFeed = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_SHOOT_BUTTON);
            climb = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_CLIMB_BUTTON);
            collectBalls = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_COLLECT_BUTTON);
            saveTrajectoryTrigger = new SaveTrajectoryTrigger();
        } else if (COMPETITION_DRIVE_CONFIG) { //!!!!! Actual joystick configuration !!!!!
            ///// Stick Initiation /////
            driverPrimaryStick = new Joystick(RobotMap.RIGHT_STICK_PORT);
            driverSecondaryStick = new Joystick(RobotMap.LEFT_STICK_PORT);
            auxiliaryStick = new Joystick(RobotMap.AUXILIARY_STICK_PORT);
            ///// Primary Stick /////
            straightMode = new JoystickButton(driverPrimaryStick, RobotMap.STRAIGHT_MODE_BUTTON);
            invertDriveDirection = new JoystickButton(driverPrimaryStick, RobotMap.TOGGLE_INVERT_DRIVE_BUTTON);
            cancelCommand = new JoystickButton(driverPrimaryStick, RobotMap.CANCEL_COMMAND_BUTTON);
            driverCollectBalls = new JoystickButton(driverPrimaryStick, 4);
            driverReverseBallCollector = new JoystickButton(driverPrimaryStick, 5);
            ///// Secondary Stick /////
            safetyMode = new JoystickButton(driverSecondaryStick, RobotMap.SAFETY_MODE_BUTTON);
            placeGear = new JoystickButton(driverSecondaryStick, PLACE_GEAR_BUTTON);
            driverShoot = new JoystickButton(driverSecondaryStick, 4);
            driverShootWithAlignment = new JoystickButton(driverSecondaryStick, 3);
            driverReverseShoot = new JoystickButton(driverSecondaryStick, 5);
            ///// Auxiliary Stick /////
            shootAndFeed = new JoystickButton(auxiliaryStick, RobotMap.SHOOT_BUTTON);
            shootFeedAlign = new JoystickButton(auxiliaryStick, RobotMap.SHOOT_AND_ALIGN_BUTTON);
            reverseShooter = new JoystickButton(auxiliaryStick, RobotMap.REVERSE_SHOOT_DIRECTION);
            climb = new JoystickButton(auxiliaryStick, RobotMap.CLIMB_BUTTON);
            collectBalls = new JoystickButton(auxiliaryStick, RobotMap.BALL_COLLECT_BUTTON);
            reverseCollectBalls = new JoystickButton(auxiliaryStick, RobotMap.REVERSE_BALL_COLLECT_BUTTON);
            driverCollectBalls.whileHeld(new CollectBalls(RobotMap.BALL_COLLECTION_SPEED));
            driverReverseBallCollector.whileHeld(new CollectBalls(-RobotMap.BALL_COLLECTION_SPEED));
            driverShoot.whileHeld(new ShootAndFeed());
            driverShootWithAlignment.whileHeld(new ShootFeedAlign());
            driverReverseShoot.whileHeld(new ShooterReverse());
        } else { // This configuration is used for debugging
            ///// Stick Initiation /////
            driverPrimaryStick = new Joystick(RobotMap.RIGHT_STICK_PORT);
            driverSecondaryStick = new Joystick(RobotMap.LEFT_STICK_PORT);
            ///// Primary Stick /////
            safetyMode = new JoystickButton(driverPrimaryStick, RobotMap.SAFETY_MODE_BUTTON);
            contourError = new JoystickButton(driverPrimaryStick, RobotMap.CONTOUR_ERROR_BUTTON);
            centerOnHighGoal = new JoystickButton(driverPrimaryStick, RobotMap.CENTER_ON_HIGH_BUTTON_BUTTON);
            driveToHighGoal = new JoystickButton(driverPrimaryStick, RobotMap.DRIVE_TO_HIGH_GOAL_BUTTON);
            shootAndFeed = new JoystickButton(driverPrimaryStick, RobotMap.SHOOT_BUTTON);
            invertDriveDirection = new JoystickButton(driverPrimaryStick, 8);
            reverseShooter = new JoystickButton(driverPrimaryStick, RobotMap.REVERSE_SHOOT_DIRECTION);
            //gearMotorDown = new JoystickButton(driverPrimaryStick, RobotMap.GEAR_DOWN_BUTTON);
            //gearMotorUp = new JoystickButton(driverPrimaryStick, RobotMap.GEAR_UP_BUTTON);
            ///// Secondary Stick /////
            straightMode = new JoystickButton(driverSecondaryStick, RobotMap.STRAIGHT_MODE_BUTTON);
            centerOnGear = new JoystickButton(driverSecondaryStick, RobotMap.CENTER_ON_GEAR_BUTTON);
            driveToGear = new JoystickButton(driverSecondaryStick, RobotMap.DRIVE_TO_GEAR_BUTTON);
            climb = new JoystickButton(driverSecondaryStick, RobotMap.CLIMB_BUTTON);
            collectBalls = new JoystickButton(driverSecondaryStick, RobotMap.BALL_COLLECT_BUTTON);
            cancelCommand = new JoystickButton(driverSecondaryStick, 8);
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
                //printShooterValues.whenPressed(new PrintShootingInfo());
                increaseShooterSpeed = new JoystickButton(debugStick, 1);
                decreaseShooterSpeed = new JoystickButton(debugStick, 2);
                debugShoot = new JoystickButton(debugStick, 3);
                //debugShoot.whileHeld(new DebugShoot());
            }
//            centerOnHighGoal.whileHeld(new DriveTowardsHighGoal());
//            driveToHighGoal.whileHeld(new CenterOnHighGoal());
//            ///// Gear Commands /////
//            driveToGear.whileHeld(new PlaceGear());
//            centerOnGear.whileHeld(new CenterOnGears());
//            gearMotorDown.whileHeld(new GearPlacementMotorDown());
//            gearMotorUp.whileHeld(new GearPlacementMotorUp());
        }

        ///// High Goal Commands /////
        shootAndFeed.whileHeld(new ShootAndFeed());
        shootFeedAlign.whileHeld(new ShootFeedAlign());
        reverseShooter.whileHeld(new ShooterReverse());
        collectBalls.whileHeld(new CollectBalls(RobotMap.BALL_COLLECTION_SPEED));
        reverseCollectBalls.whileHeld(new CollectBalls(-RobotMap.BALL_COLLECTION_SPEED));
        ///// Misc Commands /////
        climb.whileHeld(new Climb());
        placeGear.whenPressed(new PlaceGear());

    }

}

