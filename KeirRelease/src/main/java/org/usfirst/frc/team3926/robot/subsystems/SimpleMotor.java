package org.usfirst.frc.team3926.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls the ball grabbing mechanism on the back of the robot
 */
public class SimpleMotor<T> extends Subsystem {

    /** Motor controller responsible for the ball collector */
    private T motorController;

    ////////////////////////////////////////// Constructors and Initializers ///////////////////////////////////////////

    /**
     * Constructs the SimpleMotor class
     *
     * @param motorController Motor controller that is responsible for the ball collector
     * @throws IllegalArgumentException If the motorController parameter is not a Talon or CANTalon
     */
    public SimpleMotor(T motorController) {

        if (!(motorController instanceof CANTalon) && !(motorController instanceof Talon))
            throw new IllegalArgumentException("Object given to SimpleMotor is not a supported motor controller");

        this.motorController = motorController;

    }

    /**
     * No default command is needed for this system
     */
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    ///////////////////////////////////////////////// Ball Collecting //////////////////////////////////////////////////

    /**
     * Sets the speed of the motor for ball collecting
     *
     * @param speed the speed to set {@link #motorController} to
     */
    public void setCollectorSpeed(double speed) {

        if (motorController instanceof CANTalon)
            ((CANTalon) motorController).set(speed);
        else if (motorController instanceof Talon)
            ((Talon) motorController).set(speed);

    }

}

