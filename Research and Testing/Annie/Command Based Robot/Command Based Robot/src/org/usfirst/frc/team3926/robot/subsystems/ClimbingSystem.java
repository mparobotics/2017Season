package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * turns on the motor for climbing
 * when limit switch is pressed the motor stops
 */
public class ClimbingSystem extends Subsystem {

    /** climbing motor */
    private Talon        climbingMotor;
    /** limit switch to know when to stop the motor */
    private DigitalInput limitSwitch;

    /**
     * initializes the climbing motor and limit switch
     */
    public ClimbingSystem() {

        climbingMotor = new Talon(RobotMap.CLIMBING_MOTOR);
        limitSwitch = Robot.oi.limitSwitch;

    }

    /** sets the climbing motor to the value of Climbing motor speed in robot map */
    public void Climb() {

        climbingMotor.set(RobotMap.CLIMBING_MOTOR_SPEED);
    }

    /** gets value from limit switch */
    public boolean LimitSwitch() {

        return limitSwitch.get();

    }

    /** stops climbing */
    public void StopClimbing(int speed) {

        climbingMotor.set(speed);

    }

    /**
     * doesn't need a default command
     */
    @Override
    protected void initDefaultCommand() {

    }

}

