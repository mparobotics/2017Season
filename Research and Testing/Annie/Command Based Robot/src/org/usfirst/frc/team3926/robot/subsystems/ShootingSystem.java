
package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 *
 */
public class ShootingSystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private Talon shootingMotor;
    //private int speed = 1;

    private double speed = 0.8;
    private Encoder enc;




    public ShootingSystem() {
        shootingMotor = new Talon (RobotMap.SHOOTING_MOTOR);
      //  int speed = 1;

    }

    public ShootingSystem(Encoder enc) {
        enc = new Encoder(RobotMap.A_CHANNEL_ENC, RobotMap.B_CHANNEL_ENC, false, Encoder.EncodingType.k4X);
        enc.setDistancePerPulse(1);
    }

    @Override

    protected void initDefaultCommand() {

    }

    public void SetSpeed(double speed) {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
      //  speed = 1;
    }

    public static void shootingEncoder() {

        double encRate = Robot.shootingSystem.enc.getRate();

        if (encRate > RobotMap.DESRIRED_RPM) {

            Robot.shootingSystem.speed -= RobotMap.ADDED_AMOUNT;

        }
        else if (encRate < RobotMap.DESRIRED_RPM) {

            Robot.shootingSystem.speed += RobotMap.ADDED_AMOUNT;
        }

        Robot.shootingSystem.SetSpeed(Robot.shootingSystem.speed);

    }

}

