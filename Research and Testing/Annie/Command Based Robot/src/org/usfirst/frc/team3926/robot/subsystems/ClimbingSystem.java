package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 *
 */

public class ClimbingSystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    //climbing motor
    private Talon climbingMotor;
    //speed of climbing motor
    private double speed = 1;
    //limit switch
    private DigitalInput limitSwitch;

    //initializes motor and limit switch
    public ClimbingSystem() {

        climbingMotor = new Talon(RobotMap.CLIMBING_MOTOR);
        limitSwitch = new DigitalInput(RobotMap.CLIMBING_LIMIT_SWITCH);

    }

    //sets the climbing motor to the value of speed
    public void Climb() {

        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());

        climbingMotor.set(Robot.climbingSystem.speed);
    }
    public void ClimbingMotor(double speed) {

        climbingMotor.set(speed);

    }

    //gets value from limit switch
    public boolean LimitSwitch() {

        return limitSwitch.get();

    }

    @Override
    protected void initDefaultCommand() {

    }

}

