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


    public void EncodingControl(){
        enc = new Encoder(RobotMap.ENCODER_DI_ONE, RobotMap.ENCODER_DI_TWO,
                false, Encoder.EncodingType.k4X);

        enc.setMaxPeriod(.1);
        enc.setMinRate(10);
        enc.setDistancePerPulse(2);
        enc.setSamplesToAverage(5);
    }
    public int getCount(){
        return enc.get();
    }
    public void reset() {
        enc.reset();
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

