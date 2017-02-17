package org.usfirst.frc.team3926.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
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
public class AgitatorSystem extends PIDSubsystem {

    private Encoder  encoder;
    private CANTalon agitatorMotor;

    public AgitatorSystem() {
        /* This calls the constructor for a PIDSubsystem(name, proportional, integral, derivative) */
        super("Agitator PID System", RobotMap.AGITATOR_PROPORTIONAL, RobotMap.AGITATOR_INTEGRAL,
              RobotMap.AGITATOR_DERIVATIVE, RobotMap.AGITATOR_FEED_FORWARD, RobotMap.AGITATOR_PERIOD);
        setAbsoluteTolerance(RobotMap.AGITATOR_ABSOLUTE_TOLERANCE);
        encoder = new Encoder(RobotMap.AGITATOR_ENCODER_A_CHANNEL, RobotMap.AGITATOR_ENCODER_B_CHANNEL, false,
                              CounterBase.EncodingType
                                      .k4X);
        //Sets up the test motor
        agitatorMotor = new CANTalon(RobotMap.AGITATOR_CAN_ID);
        //Sets up the encoder THIS NUMBER HAS TO BE A DOUBLE, MAKE SURE IT DOESN'T AUTO-CAST
        encoder.setDistancePerPulse(1.0 / RobotMap.AGITATOR_ENCODER_PULSES_PER_REV);
        //encoder.setDistancePerPulse(RobotMap.WHEEL_DIAMETER * Math.PI / RobotMap.ENCODER_PULSES_PER_REVOLUTION);

        encoder.setPIDSourceType(PIDSourceType.kRate);
        setOutputRange(-1, 1);
        setSetpoint(RobotMap.AGITATOR_IDLE_SETPOINT);
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

        SmartDashboard.putNumber("Agitator PID Control Output", output);
        SmartDashboard.putNumber("Agitator PID Control Input", encoder.getRate());
        SmartDashboard.putNumber("Agitator PID Control Setpoint", getSetpoint());
        agitatorMotor.set(output);

    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new SpinTestMotor());
    }

    public void createDefaultCommand(Command defaultCommand) {

        setDefaultCommand(defaultCommand);

    }

}

