package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 *
 */
public class EncodingControl extends Subsystem {

    private Encoder enc;
    private double maxPeriod = 0.1;
    private int minRate = 10;
    private int distance = 2;
    private int samplesAverage = 5;


    public void EncodingControl(){
        enc = new Encoder(RobotMap.ENCODER_DI_ONE, RobotMap.ENCODER_DI_TWO,
                false, Encoder.EncodingType.k4X);

        enc.setMaxPeriod(maxPeriod);
        enc.setMinRate(minRate);
        enc.setDistancePerPulse(distance);
        enc.setSamplesToAverage(samplesAverage);
    }
    public int getCount(){
        return enc.get();
    }
    public double getRate(){
        return enc.getRate();
    }
    public void reset() {
        enc.reset();
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

