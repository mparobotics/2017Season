package org.usfirst.frc.team3926.automation;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;

/**
 * Allows for different types of sensors to be recorded for command automation
 */
public class SensorRecording {

    public enum SensorType {
        Encoder,
        LimitSwitch,
        Accelerometer,
    }
    private SensorType sensorType;
    /** This is used if recording the motion of an encoder */
    private Encoder encoder;

    /**
     * Constructs the SensorRecording class for an encoder
     * @param channelA
     * @param channelB
     * @param forwardDirection
     * @param encodingType
     */
    public SensorRecording(int channelA, int channelB, boolean forwardDirection,
                           CounterBase.EncodingType encodingType) {
        sensorType = SensorType.Encoder;
        encoder = new Encoder(channelA, channelB, forwardDirection, encodingType);
    }

    /**
     * Constructs the SensorRecording class for a limit switch
     * @param channel The DIO channel for the limit switch
     */
    private SensorRecording(int channel) {

    }



}
