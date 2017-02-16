package org.usfirst.frc.team3926.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Arrays;
import java.util.List;

/***********************************************************************************************************************
 * Subsystem to handle climbing on the robot TODO document better
 * @author William Kluge
 *     <p>
 *     Contact: klugewilliam@gmail.com
 *     </p>
 **********************************************************************************************************************/
public class Climber<T> extends Subsystem {

    /** Motor controllers for climbing */
    private List<T>      motorController;
    /** Limit switch to display on the dashboard */
    private DigitalInput limitSwitch;

    /**
     * Constructor for the Climber class
     *
     * @param limitSwitch      Limit switch to monitor the state of the climber
     * @param motorControllers All the motor controllers for this class to control
     */
    @SafeVarargs
    public Climber(DigitalInput limitSwitch, T... motorControllers) {

        this.limitSwitch = limitSwitch;
        motorController = Arrays.asList(motorControllers);
        
    }

    /**
     * Constructor for the Climber class
     *
     * @param motorControllers All the motor controllers for this class to control
     */
    @SafeVarargs
    public Climber(T... motorControllers) {

        motorController = Arrays.asList(motorControllers);

    }

    /**
     * Activates the robot's climber
     *
     * @param climbSpeed Speed to climb at
     */
    public void climb(double climbSpeed) {

        setSpeeds(climbSpeed);

        //SmartDashboard.putBoolean("Climber Limit Switch: ", limitSwitch.get());

    }

    /**
     * Sets the speeds of motor in this system to 0
     */
    public void stopClimbing() {

        setSpeeds(0);

    }

    /**
     * No default command is needed for this subsystem
     */
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    /**
     * Sets the speeds of every motor used by this system
     *
     * @param speed Speed to set the motors to
     */
    private void setSpeeds(double speed) {

        for (T i : motorController)
            if (i instanceof CANTalon)
                ((CANTalon) i).set(speed);
            else if (i instanceof Talon)
                ((Talon) i).set(speed);
            else
                throw new IllegalArgumentException("Motor controller for Climber was not Talon or CANTalon");

    }

}

