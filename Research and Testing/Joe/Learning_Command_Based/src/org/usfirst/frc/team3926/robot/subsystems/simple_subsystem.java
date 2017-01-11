package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 *
 */
public class simple_subsystem extends Subsystem {

    private double rightSide = 0;
    private double leftSide = 0;

    private RobotDrive driveSystem;


    public DriveControl() {
        driveSystem = new RobotDrive(RobotMap.FRONT_RIGHT_MOTOR_PWM,
                RobotMap.BACK_RIGHT_MOTOR_PWM, RobotMap.FRONT_LEFT_MOTOR_PWM, RobotMap.BACK_LEFT_MOTOR_PWM);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

