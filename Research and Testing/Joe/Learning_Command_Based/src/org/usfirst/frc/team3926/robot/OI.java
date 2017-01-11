package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public Joystick rightStick = new Joystick(RobotMap.RIGHT_STICK_PORT);
    public Joystick leftStick  = new Joystick(RobotMap.LEFT_STICK_PORT);

    public Button straightMode,
                  safetyMode;

    OI(){
        if (RobotMap.XBOX_CONTROLLER){
            straightMode = new JoystickButton(rightStick, 2);
            safetyMode = new JoystickButton(rightStick, 1);
        } else {
            straightMode = new JoystickButton(leftStick, 1);
            safetyMode = new JoystickButton(rightStick, 1);
        }
    }
}
