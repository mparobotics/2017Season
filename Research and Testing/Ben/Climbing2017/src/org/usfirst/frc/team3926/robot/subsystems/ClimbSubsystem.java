package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * Handles variables and methods for the robot's climbing functionality
 *
 * @author Benjamin Lash
 */
public class ClimbSubsystem extends Subsystem {

    /** Makes the talon than allows the robot to climb */
    private static Talon climber;
    /** Makes a limit switch to stop the robot from climbing once it has reached the top */
    private static DigitalInput limitSwitch;
    /** Makes an encoder which will track the motor that climbs */
    private static Encoder climbingEncoder;
    /** Defines the default speed of the motor that climbs */
    private double climberMotorSpeed;

    /**
     * Constructs the climber talon
     * Constructs the encoder
     * Sets the distance per pulse for the climber
     */
    public ClimbSubsystem() {

        climber = new Talon(RobotMap.CLIMBER_CAN_ID);
        climbingEncoder = new Encoder(RobotMap.CLIMBING_ENCODER_PORT_A, RobotMap.CLIMBING_ENCODER_PORT_B, false,
                                      Encoder.EncodingType.k4X);
        climbingEncoder.setDistancePerPulse(RobotMap.CLIMBING_ENCODER_DISTANCE_PER_PULSE);
        limitSwitch = new DigitalInput(RobotMap.LIMIT_SWITCH_DIGITAL_INPUT_NUMBER);
        climberMotorSpeed = RobotMap.DEFAULT_CLIMBER_SPEED;

    }

    /**
     * No default command is needed for this Subsystem
     */
    public void initDefaultCommand() {

    }

    /**
     * Determines if the robot has finished climbing
     * @return If the limitswitch is activated and the robot has fully climbed
     */
    public boolean climberLimitSwitch() {

        return limitSwitch.get();

    }

    /**
     * Completely stops the climber motor
     */
    public void stopClimber() {

        ClimbSubsystem.climber.set(0);

    }

    /**
     * Sets the speed of the climber motor
     * Adds to or subtracts from climberMotorSpeed if the RPM of the climbing motor is above or below target
     */
    public void spinMotor() {

        double climbingSpeed = climbingEncoder.getRate();

        if (climbingSpeed < RobotMap.CLIMBER_RPM_TARGET && climberMotorSpeed < RobotMap.CLIMBER_MAX_SPEED) {

            climberMotorSpeed += RobotMap.CLIMBER_SPEED_INCREMENT;

        }

        if (climbingSpeed > RobotMap.CLIMBER_RPM_TARGET && climberMotorSpeed > RobotMap.CLIMBER_MIN_SPEED) {

            climberMotorSpeed -= RobotMap.CLIMBER_SPEED_INCREMENT;

        }

        climber.set(climberMotorSpeed);

    }

}

