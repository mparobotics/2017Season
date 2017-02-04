
package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * controls the motor for shooting
 */
public class ShootingSystem extends Subsystem {

    /**shooting motor*/
    private Talon shootingMotor;
    /**speed of shooting motor*/
    private double speed;
    /**encoder for shooting system*/
    private Encoder enc;

    /**
     * initializes shooting motor, speed of the shooting motor, and the encoder
     * the sets the distance per pulse to 1
     */
    public ShootingSystem() {

        shootingMotor = new Talon(RobotMap.SHOOTING_MOTOR);
        speed = 0.8;
        enc = new Encoder(RobotMap.A_CHANNEL_ENC, RobotMap.B_CHANNEL_ENC, false, Encoder.EncodingType.k4X);
        enc.setDistancePerPulse(1);

    }

    @Override

    /**
    * doesn't need a default command
    */
    protected void initDefaultCommand() {

    }

    /**
     * is used to set the speed of the robot
     */
    public void SetSpeed(double speed) {

    }

    /**
     * keeps the motor at a constant set speed
     */
    public void shootingEncoder() {

        double encRate = Robot.shootingSystem.enc.getRate();

        if (encRate > RobotMap.DESRIRED_RPM) {

            Robot.shootingSystem.speed -= RobotMap.ADDED_AMOUNT;

        } else if (encRate < RobotMap.DESRIRED_RPM) {

            Robot.shootingSystem.speed += RobotMap.ADDED_AMOUNT;
        }

        Robot.shootingSystem.SetSpeed(Robot.shootingSystem.speed);

    }

}

