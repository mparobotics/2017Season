package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * Created by blash20 on 2/4/17.
 *
 * @author Benjamin Lash
 */
public class PIDLoopSubsystem extends PIDSubsystem {

    /** Declares the encoder for the shooter */
    private static Encoder shootingEncoder;
    /** Declares a talon for the shooter, it's default speed and an rpm variable */
    private        Talon   shooter;



    public PIDLoopSubsystem() {

        super(RobotMap.SHOOTER_SUPER_NAME, RobotMap.SHOOTER_PID_LOOP_P, RobotMap.SHOOTER_PID_LOOP_I, RobotMap.SHOOTER_PID_LOOP_D);
        shootingEncoder = new Encoder(RobotMap.SHOOTING_ENCODER_PORT_A, RobotMap.SHOOTING_ENCODER_PORT_B, false,
                                      Encoder.EncodingType.k4X);
        shooter = new Talon(RobotMap.SHOOTER_CAN_ID);
        setSetpoint(RobotMap.SHOOTER_PID_LOOP_SET_POINT);

    }

    protected double returnPIDInput() {

        return shootingEncoder.getRate();

    }

    protected void initDefaultCommand() {

    }

    protected void usePIDOutput(double output) {

        shooter.pidWrite(output);

    }

}
