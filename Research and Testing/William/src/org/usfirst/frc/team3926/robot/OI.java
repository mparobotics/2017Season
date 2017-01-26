package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    /** Joystick on the drivers right side (or the XBox controller for driving the robot) */
    public Joystick driverPrimaryStick;
    /** Joystick on the drivers left side (or not used) */
    public Joystick driverSecondaryStick;
    /** Button to make the robot drive straight */
    public Button   straightMode;
    /** Button to reduce the speed of the robot by {@link RobotMap#DRIVE_SAFTEY_FACTOR} */
    public Button   safteyMode;
    /** Button to signify that the robot has made an incorrect action based off of a contour */
    public Button   contourError;

    OI() {

        driverPrimaryStick = new Joystick(RobotMap.RIGHT_STICK_PORT);
        driverSecondaryStick = new Joystick(RobotMap.LEFT_STICK_PORT);

        if (RobotMap.XBOX_DRIVE_CONTROLLER) {
            straightMode = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_STRAIGHT_MODE_BUTTON);
            safteyMode = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_SAFTEY_MODE_BUTTON);
            contourError = new JoystickButton(driverPrimaryStick, RobotMap.XBOX_CONTOUR_ERROR_BUTTON);
        } else {
            straightMode = new JoystickButton(driverSecondaryStick, 1);
            safteyMode = new JoystickButton(driverPrimaryStick, 1);
        }
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
