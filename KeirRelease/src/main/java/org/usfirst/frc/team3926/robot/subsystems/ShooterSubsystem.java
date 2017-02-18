package org.usfirst.frc.team3926.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.RobotMap;

/***********************************************************************************************************************
 * This subsystem is a (hopefully) temporary patch that I used when trying to figure out
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 **********************************************************************************************************************/
public class ShooterSubsystem extends PIDSubsystem {

    private Encoder  encoder;
    private CANTalon shooterMotor;

    public ShooterSubsystem() {
        /* This calls the constructor for a PIDSubsystem(name, proportional, integral, derivative) */
        super("Shooter PID System", RobotMap.SHOOTER_PROPORTIONAL, RobotMap.SHOOTER_INTEGRAL,
              RobotMap.SHOOTER_DERIVATIVE, RobotMap.SHOOTER_FEED_FORWARD, RobotMap.SHOOTER_PERIOD);
        setAbsoluteTolerance(RobotMap.SHOOTER_ABSOLUTE_TOLERANCE);
        encoder = new Encoder(RobotMap.SHOOTER_ENCODER_A_CHANNEL, RobotMap.SHOOTER_ENCODER_B_CHANNEL, false,
                              CounterBase.EncodingType.k4X);
        //Sets up the test motor
        shooterMotor = new CANTalon(RobotMap.SHOOTER_CAN_ID);
        //Sets up the encoder THIS NUMBER HAS TO BE A DOUBLE, MAKE SURE IT DOESN'T AUTO-CAST
        encoder.setDistancePerPulse(1.0 / RobotMap.SHOOTER_ENCODER_PULSE_PER_REV);
        //encoder.setDistancePerPulse(RobotMap.WHEEL_DIAMETER * Math.PI / RobotMap.ENCODER_PULSES_PER_REVOLUTION);

        encoder.setPIDSourceType(PIDSourceType.kRate);
        setOutputRange(-1, 1);
        setSetpoint(0);
        //Starts the PID loop
        getPIDController().setContinuous(true);

    }

    /**
     * Displays various values about the encoder
     */
    public void motorDisplayOutput() {

        SmartDashboard.putNumber("Encoder value: ", encoder.get());
        SmartDashboard.putNumber("Encoder Distance: ", encoder.getDistance());
        SmartDashboard.putNumber("Encoder Rate: ", encoder.getRate());

    }

    /**
     * Here is where the sensor for the PID loop returns its current value
     *
     * @return The rate of the encoder
     */
    protected double returnPIDInput() {

        return encoder.getRate();
    }

    /**
     * Use the output of the PID loop to control the system
     *
     * @param output The output of the PID loop
     */
    protected void usePIDOutput(double output) {

        SmartDashboard.putNumber("Shooter PID Control Output", output);
        SmartDashboard.putNumber("Shooter PID Control Input", Math.round(encoder.getRate() * 100) / 100);
        SmartDashboard.putNumber("Shooter PID Control Setpoint", getSetpoint());
        shooterMotor.set(output);

    }

    /**
     * We do not create a default command for the shooter
     */
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new SpinTestMotor());
    }

}

