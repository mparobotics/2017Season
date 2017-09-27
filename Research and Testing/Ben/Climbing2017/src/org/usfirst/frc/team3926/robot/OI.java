package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team3926.robot.commands.Climb;
import org.usfirst.frc.team3926.robot.commands.ShootWithPID;
import org.usfirst.frc.team3926.robot.commands.VisionTrackingForward;
import org.usfirst.frc.team3926.robot.commands.VisionTrackingTurning;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    /** Right joystick */
    public  Joystick rightStick;
    /** Left joystick */
    public  Joystick leftStick;
    /** Button on the joystick that equalizes the speed of each robot side */
    public  Button   goStraightButton;
    /** Button for slowing the speed of the Robot */
    public  Button   safeModeButton;
    ///** Xbox joystick */
    //private Joystick xboxJoystick                = new Joystick(RobotMap.XBOX_JOYSTICK_PORT);
    /** Button on the joystick that starts the Shooting */
    private Button   shooterPIDButton;
    /** Button on the joystick that starts the Climb */
    private Button   climberButton;
    /** Button on the joystick that starts the VisionTrackingTurning */
    private Button   visionTrackingTurningButton;
    /** Button on the joystick that starts the VisionTrackingForward */
    private Button   visionTrackingForwardButton;

    /**
     * Activates the Climb and the Shooting if the buttons made for them are pressed
     * Activates the vision tracking command if the vision tracking button is being held
     */
    OI() {

        /** Right joystick */
        rightStick = new Joystick(RobotMap.RIGHT_STICK_PORT);
        /** Left joystick */
        leftStick = new Joystick(RobotMap.LEFT_STICK_PORT);
        /** Button on the joystick that equalizes the speed of each robot side */
        goStraightButton = new JoystickButton(leftStick,
                                              RobotMap.EQUALIZE_DRIVE_SYSTEM_SPEED_BUTTON_NUMBER);
        /** Button for slowing the speed of the Robot */
        safeModeButton = new JoystickButton(rightStick,
                                            RobotMap.PRECISE_DRIVING_BUTTON_NUMBER);
        ///** Xbox joystick */
        //private Joystick xboxJoystick                = new Joystick(RobotMap.XBOX_JOYSTICK_PORT);
        /** Button on the joystick that starts the Shooting */
        shooterPIDButton = new JoystickButton(rightStick, RobotMap.PID_SHOOTER_BUTTON_NUMBER);
        /** Button on the joystick that starts the Climb */
        climberButton = new JoystickButton(leftStick, RobotMap.CLIMBER_BUTTON_NUMBER);
        /** Button on the joystick that starts the VisionTrackingTurning */
        visionTrackingTurningButton = new JoystickButton(leftStick,
                                                         RobotMap.VISION_TRACKING_TURNING_BUTTON_NUMBER);
        /** Button on the joystick that starts the VisionTrackingForward */
        visionTrackingForwardButton = new JoystickButton(leftStick,
                                                         RobotMap.VISION_TRACKING_FORWARD_BUTTON_NUMBER);

        climberButton.whenPressed(new Climb());
        shooterPIDButton.whileHeld(new ShootWithPID());
        visionTrackingTurningButton.whileHeld(new VisionTrackingTurning());
        visionTrackingForwardButton.whileHeld(new VisionTrackingForward());

    }
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}

