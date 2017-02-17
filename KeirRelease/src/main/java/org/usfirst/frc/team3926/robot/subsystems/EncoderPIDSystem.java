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
 *
 */
public class EncoderPIDSystem extends PIDSubsystem {

    private Encoder  encoder;
    private CANTalon motorController;
    private String   name;
    private int      callTimes;

    public EncoderPIDSystem(CANTalon motorController, String name, double p, double i, double d,
                            double period,
                            double feedForward, double absoluteTolerance, double setPoint) {

        super(name, p, i, d, feedForward, period);
        setAbsoluteTolerance(absoluteTolerance);
        setSetpoint(setPoint);
        this.name = name;

        encoder = new Encoder(RobotMap.AGITATOR_ENCODER_A_CHANNEL, RobotMap.AGITATOR_ENCODER_B_CHANNEL, false,
                              CounterBase.EncodingType.k4X);
        encoder.setDistancePerPulse(1 / 2048);
        setOutputRange(-1, 1);
        encoder.setPIDSourceType(PIDSourceType.kRate);
        this.motorController = motorController;
        getPIDController().setContinuous(true);

    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public void createDefaultCommand(Command defaultCommand) {

        setDefaultCommand(defaultCommand);

    }

    protected double returnPIDInput() {

        SmartDashboard.putNumber(name + " PIDInput", encoder.pidGet());
        SmartDashboard.putNumber(name + " Setpoint", getSetpoint());
        System.out.println(encoder.pidGet());

        return encoder.pidGet();
    }

    protected void usePIDOutput(double output) {

        SmartDashboard.putNumber(name + " PID Output", output);

        motorController.set(output);

    }
}

