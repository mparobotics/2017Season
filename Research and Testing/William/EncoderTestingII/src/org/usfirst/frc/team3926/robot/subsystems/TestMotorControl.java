package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * This class is an example of using an encoder.
 * <p>
 * Proportional: A constant value to use to change towards the target. This is the simplest way to make a PID loop
 * (however it is really just a P loop than). If the system is below the target, this gets added. If a system is above
 * its target, this gets added. If this is the only value in the loop, the system will oscillate around the target and
 * rarely actually hit it.
 * </p>
 */
public class TestMotorControl extends PIDSubsystem {

    private Encoder encoder;
    private Talon   testMotor;
    private double outputSum = 0;

    public TestMotorControl() {
        /* This calls the constructor for a PIDSubsystem(name, proportional, integral, derivative) */
        super(RobotMap.ENCODER_PID_NAME, RobotMap.PROPORTIONAL_VALUE, RobotMap.INTEGRAL_VALUE,
              RobotMap.DERIVATIVE_VALUE, RobotMap.F_VALUE, RobotMap.PERIOD);
        setAbsoluteTolerance(RobotMap.ABSOLUTE_TOLERANCE);
        setSetpoint(RobotMap.TARGET_RATE);
        encoder = new Encoder(RobotMap.ENCODER_A_CHANNEL, RobotMap.ENCODER_B_CHANNEL, false, CounterBase.EncodingType
                .k4X);
        //Sets up the test motor
        testMotor = new Talon(RobotMap.TEST_MOTOR_PWM);
        //Sets up the encoder
        encoder.setDistancePerPulse(RobotMap.WHEEL_DIAMETER * Math.PI / RobotMap.ENCODER_PULSES_PER_REVOLUTION);
        encoder.setPIDSourceType(PIDSourceType.kRate);
        //Starts the PID loop
        enable();
        getPIDController().setContinuous(true);

    }

    /**
     * Displays various values about the encoder
     */
    public void motorDisplayOutput() {

        //usePIDOutput(getPIDController().get());

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

        //outputSum += output/20;
        SmartDashboard.putNumber("PID Output", output);
        motorDisplayOutput();
        //testMotor.set(outputSum);
        testMotor.set(output);

    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new SpinTestMotor());
    }

}

