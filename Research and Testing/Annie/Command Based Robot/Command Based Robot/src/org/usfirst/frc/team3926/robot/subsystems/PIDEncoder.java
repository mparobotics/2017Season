package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * keeps a motor at a constant speed
 */
public class PIDEncoder extends PIDSubsystem {

    /** declares encoder*/
    private Encoder encoder;
    /** motor for testing*/
    private Talon   testMotor;

    /**
     * declares PID controller for encoder, initializes encoder, sets distance per
     * pulse, and initializes test motor
     */
    public PIDEncoder() {

        super(RobotMap.NAME, RobotMap.P, RobotMap.I, RobotMap.D);

        setSetpoint(RobotMap.SPEED);

        encoder = new Encoder(RobotMap.A_CHANNEL, RobotMap.B_CHANNEL, RobotMap.REVERSE_DIRECTION,
                              Encoder.EncodingType.k4X);
        encoder.setDistancePerPulse(RobotMap.ENCODER_DISTANCE_PER_PULSE);

        testMotor = new Talon(RobotMap.TALON_CHANNEL);

    }

    /**
     * nothing needed
     */
    public void initDefaultCommand() {

    }

    /**
     * returns rate of encoder
     * @return
     */
    protected double returnPIDInput() {

        return encoder.getRate();

    }

    /**
     * sets the speed of the motor to the output of the PID controller
     * @param output
     */
    protected void usePIDOutput(double output) {

        testMotor.set(output);
    }
}
