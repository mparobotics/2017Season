package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team3926.robot.commands.ClimbCommand;
import org.usfirst.frc.team3926.robot.commands.PIDShootingCommand;
import org.usfirst.frc.team3926.robot.commands.VisionTrackingForwardCommand;
import org.usfirst.frc.team3926.robot.commands.VisionTrackingTurningCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    /** Right joystick */
    public  Joystick rightStick                  = new Joystick(RobotMap.RIGHT_STICK_PORT);
    /** Left joystick */
    public  Joystick leftStick                   = new Joystick(RobotMap.LEFT_STICK_PORT);
    ///** Xbox joystick */
    //private Joystick xboxJoystick                = new Joystick(RobotMap.XBOX_JOYSTICK_PORT);
    /** Button on the joystick that starts the ShootingCommand */
    private Button   shooterPIDButton            = new JoystickButton(rightStick, RobotMap.PID_SHOOTER_BUTTON_NUMBER);
    /** Button on the joystick that starts the ClimbCommand */
    private Button   climberButton               = new JoystickButton(leftStick, RobotMap.CLIMBER_BUTTON_NUMBER);
    /** Button on the joystick that starts the VisionTrackingTurningCommand */
    private Button   visionTrackingTurningButton = new JoystickButton(leftStick, RobotMap
            .VISION_TRACKING_TURNING_BUTTON_NUMBER);
    /** Button on the joystick that starts the VisionTrackingForwardCommand */
    private Button   visionTrackingForwardButton = new JoystickButton(leftStick, RobotMap.
            VISION_TRACKING_FORWARD_BUTTON_NUMBER);

    /**
     * Activates the ClimbCommand and the ShootingCommand if the buttons made for them are pressed
     * Activates the vision tracking command if the vision tracking button is being held
     */
    OI() {

        climberButton.whenPressed(new ClimbCommand());
        shooterPIDButton.whileHeld(new PIDShootingCommand());
        visionTrackingTurningButton.whileHeld(new VisionTrackingTurningCommand());
        visionTrackingForwardButton.whileHeld(new VisionTrackingForwardCommand());

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

