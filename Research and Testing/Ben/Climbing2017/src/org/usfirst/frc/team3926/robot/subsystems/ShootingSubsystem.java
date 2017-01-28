package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * handles the robot's shooting mechanism
 *
 * @author Benjamin Lash
 */

public class ShootingSubsystem extends Subsystem {

    private static Encoder shootingEncoder;
    /**
     * make the climber talon
     * makes the encoder
     */
    private Talon shooter;
    private double shooterMotorSpeed = 0.5;

    private double shooterRPM;

    NetworkTable table;

    double[] xValue;

    /**
     * constructs the shooter talon
     * constructs the encoder
     */
    public ShootingSubsystem() {

        shooter = new Talon(RobotMap.SHOOTER_PORT);
        shootingEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
        shootingEncoder.setDistancePerPulse(1);
        table = NetworkTable.getTable("GRIP/ContourReport");
        xValue = table.getNumberArray(RobotMap.NETWORK_TABLE_KEY, new double[0]);


    }

    public void initDefaultCommand() {

    }

    public double visionTrackingForwardSpeedOne() {

        return xValue[0] < RobotMap.VISION_SCREEN_CENTER ?
               RobotMap.MAX_VISION_TRACKING_SPEED * (xValue[0] / RobotMap.VISION_SCREEN_CENTER) :
               RobotMap.MAX_VISION_TRACKING_SPEED;
    }

    public double visionTrackingForwardSpeedTwo() {

        return xValue[0] > RobotMap.VISION_SCREEN_CENTER ? RobotMap.MAX_VISION_TRACKING_SPEED *
                                                           ((xValue[0] -
                                                             (RobotMap.VISION_SCREEN_CENTER)) /
                                                            RobotMap.VISION_SCREEN_CENTER) :
               RobotMap.MAX_VISION_TRACKING_SPEED;
    }

    public void visionTracking(double visionTrackingSpeedOne, double visionTrackingSpeedTwo) {

        DriveSubsytem.visionTrackingMovement(visionTrackingSpeedOne, visionTrackingSpeedTwo);

    }

    public double[] visionTrackingTurningSpeeds() {

        return new double[]{xValue[0] < RobotMap.VISION_SCREEN_CENTER ? visionTrackingForwardSpeedOne() :
                            - visionTrackingForwardSpeedOne(),
                            xValue[0] > RobotMap.VISION_SCREEN_CENTER ? visionTrackingForwardSpeedTwo() :
                            - visionTrackingForwardSpeedTwo()};

    }


    /**
     * sets the speed of the shooter motor to 0
     */
    public void stopShooter() {

        Robot.shootingSubsystem.shooter.set(0);

    }

    /**
     * a simple method to set the speed of the motor speed based off of the position of the right stick
     *
     * @param rightStickYForShooter
     */
    public void useSimpleShooter(double rightStickYForShooter) {

        shooter.set(rightStickYForShooter);

    }

    /**
     * makes the robot shoot
     * adjusts the speed of the shooter if the shooter RPM is too slow or too fast but makes sure to not lower the speed
     * beyond 0
     */
    public void useShooter() {

        shooterRPM = shootingEncoder.getRate();

        if (shooterRPM < RobotMap.SHOOTER_RPM_TARGET) {

            shooterMotorSpeed += RobotMap.SHOOTER_SPEED_INCREMENT;

        }

        if (shooterRPM > RobotMap.SHOOTER_RPM_TARGET && shooterMotorSpeed >= RobotMap.SHOOTER_SPEED_INCREMENT) {

            shooterMotorSpeed -= RobotMap.DRIVE_FORWARD_SPEED_INCREMENT;

        }

        shooter.set(shooterMotorSpeed);

    }


}

