package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * handles the climbing functionality of the robot
 * @author Benjamin Lash
 */
public class ClimbSubsystem extends Subsystem {

    /**
     * makes the talon than allow the robot to climb
     * makes the encoder that tracts the climbing talon
     */
    private static Talon climber;
    DigitalInput limitSwitch = new DigitalInput(0);

    /** makes an encoder which will track the motor that climbs */
    static Encoder climbingEncoder;

    /** defines the default speed of the motor that climbs */
    double climberMotorSpeed = 0.5;

    /**
     * constructs the climber talon
     * constructs the encoder
     * sets the distance per pulse for the climber
     */
    public ClimbSubsystem() {

        climber = new Talon(RobotMap.CLIMBER_PORT);
        climbingEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
        climbingEncoder.setDistancePerPulse(1);
    }

    public void initDefaultCommand() {

    }

    /**
     * @return if the limitswitch is activated and the robot has fully climbed
     */
    public boolean climberLimitSwitch(){
      return limitSwitch.get();
    }

    /**
     * completely stops the climber motor
     */
    public void stopClimber(){
        ClimbSubsystem.climber.set(0);
    }
    /**
     * sets the speed of the climber motor
     * adds to or subtracts from climberMotorSpeed if the RPM of the climbing motor is above or below target
     */
    public void spinMotor() {

        double climbingSpeed = climbingEncoder.getRate();


        if (climbingSpeed < RobotMap.CLIMBER_RPM_TARGET && climberMotorSpeed < 1) {

            climberMotorSpeed += RobotMap.CLIMBER_SPEED_INCREMENT;

        }

        if (climbingSpeed > RobotMap.CLIMBER_RPM_TARGET && climberMotorSpeed > 0) {

            climberMotorSpeed -= RobotMap.CLIMBER_SPEED_INCREMENT;

        }


        climber.set(climberMotorSpeed);

    }


}

