package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * Handles variables and methods for the robot's shooting mechanism
 *
 * @author Benjamin Lash
 */
public class ShootingSubsystem extends Subsystem {

    /** Declares a talon for the shooter */
    private Talon shooter;

    /**
     * Constructs the shooter talon
     */
    public ShootingSubsystem() {

        shooter = new Talon(RobotMap.SHOOTER_PWM_PORT);

    }

    /**
     * No default command is needed for this Subsystem
     */
    public void initDefaultCommand() {

    }

    /**
     * Sets the speed of the shooter motor to 0
     */
    public void stopShooter() {

        shooter.set(0);

    }

    /**
     * A simple method to set the speed of the shooter motor based off of the position of the right stick
     *
     * @param rightStickYForShooter the y position of the rightstick
     */
    public void useSimpleShooter(double rightStickYForShooter) {

        shooter.set(rightStickYForShooter);

    }

    public void useShooter() {

    }

}

