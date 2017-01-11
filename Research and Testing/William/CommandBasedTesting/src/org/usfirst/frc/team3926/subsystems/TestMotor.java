package org.usfirst.frc.team3926.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.RobotMap;

import static org.usfirst.frc.team3926.robot.Robot.oi;

/**
 *
 */
public class TestMotor extends Subsystem {

    Talon testMotor;

    public TestMotor() {
        testMotor = new Talon(RobotMap.TEST_MOTOR_PWM);
    }

    public void RunTestMotor() {
        double rightTrigger = oi.rightStick.getRawAxis(RobotMap.TEST_MOTOR_FORWARD);
        double leftTrigger  = oi.leftStick.getRawAxis(RobotMap.TEST_MOTOR_REVERSE);
        double setSpeed = 0;

        if (rightTrigger > leftTrigger)
            setSpeed = rightTrigger;
        else if (leftTrigger > rightTrigger)
            setSpeed = leftTrigger*=-1;

        testMotor.set(setSpeed);

    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

