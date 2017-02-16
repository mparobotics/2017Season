package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/***********************************************************************************************************************
 * This class controls a motor controller (of type T) via a sensor (of type S).
 * <p>
 * This class supports actuators that extend PIDOutput (CANTalon, Jaguar, Spark, Talon, TalonSRX, Victor, VictorSP,
 * and anything that extends PWMSpeedController or SpeedController).
 * </p>
 * <p>
 * This class supports any sensors that extend PIDSource (ADXRS450_Gyro, AnalogAccelerometer, CANTalon, Counter,
 * Encoder, Filter, GearTooth, GyroBase, LinearDigitalFilter, Potentiometer, and Ultrasonic) and sensors of the class
 * Accelerometer, Gyro
 * </p>
 * <p>
 * TODO support solenoid output
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 ***********************************************************************************************************************/
public class PIDControlledActuator<T, S> extends PIDSubsystem {

    /** Holds what the sensor is supposed to be checking for */
    private enum SensorPurpose {
        Rate,
        XAccel,
        YAccel,
        ZAccel,
        Angle
    }

    /** Holds what the current purpose of the sensor is */
    private SensorPurpose sensorPurpose;
    /** Actuator being controlled by this PIDSubsystem */
    private T             acutator;
    /** Sensor to control the output of the PID loop */
    private S             sensor;
    /** Name of this subsystem */
    private String        name;

    /**
     * Constructs the PIDControlledActuator class with all values relevant to PID control.
     *
     * @param name              Name of the PIDSubsystem
     * @param actuator          Object to actuate with this system
     * @param sensor            Object used to sense what this system is doing
     * @param pidSourceType     Source type of the sensors purpose
     * @param target            Target value for the actuator to reach
     * @param proportional      Multiplier for the proportional value
     * @param integral          Multiplier for the integral value (area under the curve) of the actuators get() method
     * @param derivative        Multiplier for the derivative value (instantaneous slope) of the actuators get() method
     * @param forward           yeah...idk man
     * @param period            Amount of time between PID loop updates
     * @param absoluteTolerance Percent that the system is allowed to be off of the target
     */
    public PIDControlledActuator(String name, T actuator, S sensor, PIDSourceType pidSourceType, double target, double
            proportional, double integral, double derivative, double forward, double period, double absoluteTolerance) {

        super(name, proportional, integral, derivative, forward, period);
        setSetpoint(target);
        setAbsoluteTolerance(absoluteTolerance);

        this.name = name;
        this.acutator = actuator;
        this.sensor = sensor;

        if (sensor instanceof PIDSource)
            ((PIDSource) sensor).setPIDSourceType(pidSourceType);
        else
            throw new IllegalArgumentException("The constructor for sensors deriving from PIDSourceType was used, but" +
                                               " the sensor is not a child of that class. Use the other constructor " +
                                               "to resolve this error");

    }

    /**
     * Constructs the PIDControlledActuator class with all values relevant to PID control.
     *
     * @param name              Name of the PIDSubsystem
     * @param actuator          Object to actuate with this system
     * @param sensor            Object used to sense what this system is doing
     * @param pidSourceType     Source type of the sensors purpose
     * @param target            Target value for the actuator to reach
     * @param proportional      Multiplier for the proportional value
     * @param integral          Multiplier for the integral value (area under the curve) of the actuators get() method
     * @param derivative        Multiplier for the derivative value (instantaneous slope) of the actuators get() method
     * @param absoluteTolerance Percent that the system is allowed to be off of the target
     */
    public PIDControlledActuator(String name, T actuator, S sensor, PIDSourceType pidSourceType, double target, double
            proportional, double integral, double derivative, double absoluteTolerance) {

        super(name, proportional, integral, derivative);
        setSetpoint(target);
        setAbsoluteTolerance(absoluteTolerance);

        this.name = name;
        this.acutator = actuator;
        this.sensor = sensor;

        if (sensor instanceof PIDSource)
            ((PIDSource) sensor).setPIDSourceType(pidSourceType);
        else
            throw new IllegalArgumentException("The constructor for sensors deriving from PIDSourceType was used, but" +
                                               " the sensor is not a child of that class. Use the other constructor " +
                                               "to resolve this error");

    }

    /**
     * Constructs the PIDControlledActuator class with all values relevant to PID control.
     *
     * @param name              Name of the PIDSubsystem
     * @param actuator          Object to actuate with this system
     * @param sensor            Object used to sense what this system is doing
     * @param sensorPurpose     What the sensor is supposed to measure
     * @param target            Target value for the actuator to reach
     * @param proportional      Multiplier for the proportional value
     * @param integral          Multiplier for the integral value (area under the curve) of the actuators get() method
     * @param derivative        Multiplier for the derivative value (instantaneous slope) of the actuators get() method
     * @param forward           yeah...idk man
     * @param period            Amount of time between PID loop updates
     * @param absoluteTolerance Percent that the system is allowed to be off of the target
     */
    public PIDControlledActuator(String name, T actuator, S sensor, SensorPurpose sensorPurpose, double target, double
            proportional, double integral, double derivative, double forward, double period, double absoluteTolerance) {

        super(name, proportional, integral, derivative, forward, period);
        setSetpoint(target);
        setAbsoluteTolerance(absoluteTolerance);

        this.acutator = actuator;
        this.sensor = sensor;
        this.name = name;

        if (sensor instanceof PIDSource)
            throw new IllegalArgumentException("The constructor for sensors not deriving from PIDSourceType was used," +
                                               " but the sensor is a child of that class. Use the other constructor " +
                                               "to resolve this error");
        else
            this.sensorPurpose = sensorPurpose;

    }

    /**
     * Constructs the PIDControlledActuator class with all values relevant to PID control.
     *
     * @param name              Name of the PIDSubsystem
     * @param actuator          Object to actuate with this system
     * @param sensor            Object used to sense what this system is doing
     * @param sensorPurpose     What the sensor is supposed to measure
     * @param target            Target value for the actuator to reach
     * @param proportional      Multiplier for the proportional value
     * @param integral          Multiplier for the integral value (area under the curve) of the actuators get() method
     * @param derivative        Multiplier for the derivative value (instantaneous slope) of the actuators get() method
     * @param absoluteTolerance Percent that the system is allowed to be off of the target
     */
    public PIDControlledActuator(String name, T actuator, S sensor, SensorPurpose sensorPurpose, double target, double
            proportional, double integral, double derivative, double absoluteTolerance) {

        super(name, proportional, integral, derivative);
        setSetpoint(target);
        setAbsoluteTolerance(absoluteTolerance);

        this.acutator = actuator;
        this.sensor = sensor;
        this.name = name;

        if (sensor instanceof PIDSource)
            throw new IllegalArgumentException("The constructor for sensors not deriving from PIDSourceType was used," +
                                               " but the sensor is a child of that class. Use the other constructor " +
                                               "to resolve this error");
        else
            this.sensorPurpose = sensorPurpose;

    }

    /**
     * Returns values from the sensor according to what type of sensor it is.
     *
     * @return Value from the sensor to determine the value of the PID loop
     * @throws IllegalStateException If the sensor is an accelerometer but the user has not stated what axis to check
     *                               or if sensor is not of a supported type.
     */
    protected double returnPIDInput() {

        double returnValue = 0;

        if (sensor instanceof PIDSource) {

            returnValue = ((PIDSource) sensor).pidGet();

        } else if (sensor instanceof Accelerometer) {

            switch (sensorPurpose) {

                case XAccel:
                    returnValue = ((Accelerometer) sensor).getX();
                    break;
                case YAccel:
                    returnValue = ((Accelerometer) sensor).getY();
                    break;
                case ZAccel:
                    returnValue = ((Accelerometer) sensor).getZ();
                    break;
                default:
                    throw new IllegalStateException("Sensor is an Accelerometer, but no axis was configured to check");

            }

        } else if (sensor instanceof Gyro) {

            switch (sensorPurpose) {

                case Angle:
                    returnValue = ((Gyro) sensor).getAngle();
                    break;
                case Rate:
                    returnValue = ((Gyro) sensor).getRate();
                    break;
                default:
                    throw new IllegalStateException("Sensor is a Gyro, but the purpose of the sensor is not to detect" +
                                                    " angle or rate.");

            }

        } else {

            throw new IllegalStateException(
                    "No supported sensor type was found. Make sure that the template is being " +
                    "used correctly.");

        }

        SmartDashboard.putNumber(name, returnValue);

        return returnValue;

    }

    /**
     * Uses the value that the pid loop calculated. The calculated value is the "output" parameter.
     * This method is a good time to set motor values, maybe something along the lines of
     * <p>
     * All subclasses of {@link PIDSubsystem} must override this method.
     * </p>
     *
     * @param output the value the pid loop calculated
     * @throws IllegalArgumentException if actuator is not an object that extends PIDOutput
     */
    @Override
    protected void usePIDOutput(double output) {

        if (acutator instanceof PIDOutput)
            ((PIDOutput) acutator).pidWrite(output);
        else
            throw new IllegalArgumentException("PIDControlledActuator was created with an actuator that does not " +
                                               "extend PIDOutput.");

    }

    /**
     * Regular initDefaultCommand method.
     */
    public void initDefaultCommand() {

    }

    /**
     * Allows for setting a default command from outside this class
     *
     * @param defaultCommand Command to set as the default
     */
    public void createDefaultCommand(Command defaultCommand) {

        setDefaultCommand(defaultCommand);

    }

}

