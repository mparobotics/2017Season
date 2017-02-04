package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.DriveCommand;

/**
 * Handles variables and methods for the robot's driving functionality
 *
 * @author Benjamin Lash
 */
public class DriveSubsytem extends Subsystem {

    /** Encoder to track the forward motion of the robot */
    private static Encoder       rightDrivingEncoder;
    /** Encoder to track the forward motion of the robot */
    private static  Encoder       leftDrivingEncoder;
    /** Declares the drivesystem for the robot */
    private static RobotDrive    driveSystem;
    /** Talon on the back right corner of the robot for the driveSystem */
    private static Talon         talonBR;
    /** Talon on the front right corner of the robot for the driveSystem */
    private static Talon         talonFR;
    /** Talon on the back left corner of the robot for the driveSystem */
    private static Talon         talonBL;
    /** Talon on the front left corner of the robot for the driveSystem */
    private static Talon         talonFL;
    /** Distance traveled by the robot */
    private        double        distanceTraveled;
    /** Accelometer to track the robot's acceleration */
    private static Accelerometer accelerometer;
    /** Gyro to track the position of the Robot */
    private static Gyro          gyro;
    /** Variable to track the acceleration of the motor */
    private static double        acceleration;
    /** Speed variable for right side in the deceleration function */
    private        double        rightSideDecelerationSpeed;
    /** Speed variable for left side in the deceleration function */
    private        double        leftSideDecelerationSpeed;
    /** Variable for the robots angle for the turning function */
    private        double        currentAngle;

    /**
     * Constructs the driving encoders
     * Sets the distance per pulse of the driving encoders
     * Constructs the motors for the drive system
     * Constructs the accelerometer
     * Constructs the gyro
     * Constructs the rightSideDecelerationSpeed variable which acts as the default deceleration speed
     */
    public DriveSubsytem() {

        rightDrivingEncoder =
                new Encoder(RobotMap.RIGHT_DRIVING_ENCODER_PORT_A, RobotMap.RIGHT_DRIVING_ENCODER_PORT_B, false,
                            Encoder.EncodingType.k4X);
        leftDrivingEncoder = new Encoder(RobotMap.LEFT_DRIVING_ENCODER_PORT_A, RobotMap.LEFT_DRIVING_ENCODER_PORT_B,
                                         false,
                                         Encoder.EncodingType.k4X);
        rightDrivingEncoder.setDistancePerPulse(RobotMap.DRIVING_ENCODERS_DISTANCE_PER_PULSE);
        leftDrivingEncoder.setDistancePerPulse(RobotMap.DRIVING_ENCODERS_DISTANCE_PER_PULSE);

        talonBR = new Talon(RobotMap.TALON_BR_PORT);
        talonFR = new Talon(RobotMap.TALON_FR_PORT);
        talonBL = new Talon(RobotMap.TALON_BL_PORT);
        talonFL = new Talon(RobotMap.TALON_FL_PORT);

        driveSystem = new RobotDrive(talonFL, talonBL, talonFR, talonBR);

        accelerometer = new BuiltInAccelerometer(Accelerometer.Range.k4G);

        gyro = new AnalogGyro(RobotMap.GYRO_PORT);

        rightSideDecelerationSpeed = RobotMap.DEFAULT_DECELERATION_SPEED;

    }

    /**
     * Sets the default command to DriveCommand
     */
    public void initDefaultCommand() {

        new DriveCommand();

    }

    /**
     * Plugs in leftStickHeight and rightStickHeight to the driveSystem
     *
     * @param rightStickHeight The y position from the right joystick
     * @param leftStickHeight  The y position from the left joystick
     * @param leftButtonStatus If the button of the left stick is pressed
     */
    public void driveMethod(double rightStickHeight, double leftStickHeight, boolean leftButtonStatus) {

        if (leftButtonStatus) {

            rightStickHeight = leftStickHeight;

        }

        driveSystem.tankDrive(leftStickHeight, rightStickHeight);

    }

    /**
     * This sets the rightSideDecelerationSpeed of the motors to full to go foward
     * Then it gets the distance that the robot has traveled
     */
    public void driveForward() {

        driveSystem.tankDrive(1, 1);
        distanceTraveled = rightDrivingEncoder.getDistance();

    }

    /**
     * Changes speed of the motor for the robot based on the robot's rate of acceleration until the Robot
     * reaches 0 speed
     */
    public void deceleration() {

        acceleration = accelerometer.getX();

        if (acceleration < -5) {

            rightSideDecelerationSpeed += RobotMap.DRIVE_FORWARD_SPEED_INCREMENT;

        } else if (acceleration > 0) {

            rightSideDecelerationSpeed -= RobotMap.DRIVE_FORWARD_SPEED_INCREMENT;

        } else {

            rightSideDecelerationSpeed = 0;
        }

        if (acceleration < -5) {

            leftSideDecelerationSpeed += RobotMap.DRIVE_FORWARD_SPEED_INCREMENT;

        } else if (acceleration > 0) {

            leftSideDecelerationSpeed -= RobotMap.DRIVE_FORWARD_SPEED_INCREMENT;

        } else {

            leftSideDecelerationSpeed = 0;

        }

        driveSystem.tankDrive(leftSideDecelerationSpeed, rightSideDecelerationSpeed);

    }

    /**
     * Checks if the robot has reached 0 speed
     *
     * @return If the rightSideDecelerationSpeed and leftSideDecelerationSpeed is zero
     */
    public boolean isSpeedZero() {

        return rightSideDecelerationSpeed == 0 && leftSideDecelerationSpeed == 0;

    }

    /**
     * Sets the driveSystem to values that make it turn
     */
    public void turning() {

        driveSystem.tankDrive(-1, 1);
    }

    /**
     * Resets the encoder to be used again
     */
    public void resetEncoder() {

        rightDrivingEncoder.reset();

    }

    /**
     * Gets the robots current angle with the gyro to find it's position
     */
    public void Gyro() {

        currentAngle = gyro.getAngle();

    }

    /**
     * Makes robot move based off position of retroreflective tape
     *
     * @param visionTrackingLeftSpeed  (the first speed of the drive system)
     * @param visionTrackingRightSpeed (the second speed in the drive system)
     */
    public static void visionTrackingMovement(double visionTrackingLeftSpeed, double visionTrackingRightSpeed) {

        driveSystem.tankDrive(visionTrackingLeftSpeed, visionTrackingRightSpeed);

    }

    /**
     * Checks if the robot has turned 90 degrees
     *
     * @return If the robot has turned 90 degrees since the last reset
     */
    public boolean HasRobotTurned() {

        return currentAngle >= 90;

    }

    /**
     * Checks if the robot has traveled 10 meters
     *
     * @return If the robot has traveled ten meters
     */
    public boolean tenMetersTraveled() {

        return distanceTraveled >= 10;

    }
}

