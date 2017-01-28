
package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

public class ShootingSystem extends Subsystem {

    /**shooting motor*/
    private Talon shootingMotor;
    /**speed of shooting motor*/
    private double speed = 0.8;
    /**encoder for shooting system*/
    private Encoder enc;

    /**
     * declares encoder
     */
    public ShootingSystem() {

        shootingMotor = new Talon(RobotMap.SHOOTING_MOTOR);
        enc = new Encoder(RobotMap.A_CHANNEL_ENC, RobotMap.B_CHANNEL_ENC, false, Encoder.EncodingType.k4X);
        enc.setDistancePerPulse(1);

    }

    @Override

    protected void initDefaultCommand() {

    }

    public void SetSpeed(double speed) {

    }

    /**
     * keeps the motor at a constant set speed
     */
    public static void shootingEncoder() {

        double encRate = Robot.shootingSystem.enc.getRate();

        if (encRate > RobotMap.DESRIRED_RPM) {

            Robot.shootingSystem.speed -= RobotMap.ADDED_AMOUNT;

        } else if (encRate < RobotMap.DESRIRED_RPM) {

            Robot.shootingSystem.speed += RobotMap.ADDED_AMOUNT;
        }

        Robot.shootingSystem.SetSpeed(Robot.shootingSystem.speed);

    }

}

