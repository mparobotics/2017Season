package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team3926.robot.commands.DriveCommand;
import org.usfirst.frc.team3926.robot.commands.ExampleCommand;
import org.usfirst.frc.team3926.robot.commands.ShooterCommand;
import org.usfirst.frc.team3926.robot.subsystems.ShootingSystem;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public static Joystick     rightStick     = new Joystick(RobotMap.RIGHT_STICK);
    public static Joystick     leftstick      = new Joystick(RobotMap.LEFT_STICK);
    public        Button       shootingButton = new JoystickButton(rightStick, 1);
    public        DigitalInput limitSwitch    = new DigitalInput(RobotMap.CLIMBING_LIMIT_SWITCH);

    public OI() {

        shootingButton.whileHeld(new ShooterCommand());

    }
}
