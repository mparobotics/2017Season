package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.DriveWithController;

/**
 * Handles variables and methods for the robot's driving functionality
 *
 * @author Benjamin Lash
 */
public class DriveSubsytem extends Subsystem {

    /** Encoder to track the forward motion of the robot */
    private static Encoder       rightDrivingEncoder;
    /** Encoder to track the forward motion of the robot */
    private static Encoder       leftDrivingEncoder;
    /** Declares the drivesystem for the robot */
    private static RobotDrive    driveSystem;
    /** Accelometer to track the robot's acceleration */
    private static Accelerometer accelerometer;
    /** Gyro to track the position of the Robot */
    private static Gyro          gyro;
    /** Distance traveled by the robot */
    private        double        distanceTraveled;
    /** Speed variable for right side in the decelerationForward function */
    private        double        rightSideDecelerationSpeed;
    /** Speed variable for left side in the decelerationForward function */
    private        double        leftSideDecelerationSpeed;
    /** Range Finder to find the distance of the Robot from the wall */
    private        AnalogInput   rangeFinder;

    /**
     * Constructs the driving encoders
     * Sets the distance per pulse of the driving encoders
     * Constructs the motors for the drive system
     * Constructs the accelerometer
     * Constructs the gyro
     * Constructs the rightSideDecelerationSpeed variable which acts as the default decelerationForward speed
     */
    public DriveSubsytem() {

        rangeFinder = new AnalogInput(1);

        rightDrivingEncoder =
                new Encoder(RobotMap.RIGHT_DRIVING_ENCODER_PORT_A, RobotMap.RIGHT_DRIVING_ENCODER_PORT_B, false,
                            Encoder.EncodingType.k4X);
        leftDrivingEncoder = new Encoder(RobotMap.LEFT_DRIVING_ENCODER_PORT_A, RobotMap.LEFT_DRIVING_ENCODER_PORT_B,
                                         false, Encoder.EncodingType.k4X);

        rightDrivingEncoder.setDistancePerPulse(RobotMap.DRIVING_ENCODERS_DISTANCE_PER_PULSE);
        leftDrivingEncoder.setDistancePerPulse(RobotMap.DRIVING_ENCODERS_DISTANCE_PER_PULSE);

        driveSystem = new RobotDrive(new Talon(RobotMap.TALON_FL_CAN_ID), new Talon(RobotMap.TALON_BL_CAN_ID),
                                     new Talon(RobotMap.TALON_FR_CAN_ID), new Talon(RobotMap.TALON_BR_CAN_ID));

        accelerometer = new BuiltInAccelerometer(Accelerometer.Range.k4G);

        gyro = new AnalogGyro(RobotMap.GYRO_PORT);

        rightSideDecelerationSpeed = RobotMap.MAX_AUTO_DRIVING_SPEED;
        leftSideDecelerationSpeed = RobotMap.MAX_AUTO_DRIVING_SPEED;

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
     * Sets the default command to DriveWithController
     */
    public void initDefaultCommand() {

        new DriveWithController();

    }

    /**
     * Plugs in leftStickHeight and rightStickHeight to the driveSystem
     *
     * @param rightStickHeight The y position from the right joystick
     * @param leftStickHeight  The y position from the left joystick
     * @param leftButtonStatus If the button of the left stick is pressed
     */
    public void driveMethod(double rightStickHeight, double leftStickHeight, boolean leftButtonStatus) {

        if (Robot.oi.equalizeRobotSpeedsButton.get()) {

            rightStickHeight = leftStickHeight;

        }

        if (Robot.oi.precisionDrivingButton.get()) {

            rightStickHeight = rightStickHeight * RobotMap.PRECISION_DRIVING_MULTIPLIER;
            leftStickHeight = leftStickHeight * RobotMap.PRECISION_DRIVING_MULTIPLIER;

        }

        driveSystem.tankDrive(leftStickHeight, rightStickHeight);

    }

    /**
     * This sets the rightSideDecelerationSpeed of the motors to full to go forward
     * Then it gets the distance that the robot has traveled
     */
    public void driveForward() {

        driveSystem.tankDrive(RobotMap.MAX_AUTO_DRIVING_SPEED, RobotMap.MAX_AUTO_DRIVING_SPEED);
        distanceTraveled = rightDrivingEncoder.getDistance();

    }

    /**
     * Drives Backward If the wall is 10 meters away
     */
    public void rangeFinderDriveBackward() {

        if (wallLessThenTenMetersAway()) {

            autoDriveDesiredDistance(-RobotMap.AUTO_DRIVE_BACKWARD_DISTANCE);

        }

    }

    /**
     * Checks if the wall is less then then meters away
     *
     * @return if the wall is less than ten meters away
     */
    private boolean wallLessThenTenMetersAway() {

        return rangeFinder.getValue() * RobotMap.RANGE_FINDER_SENSITIVITY <= 10;

    }

    /**
     * Checks if the robot has traveled 10 meters
     *
     * @return If the robot has traveled ten meters
     */
    public boolean properDistanceTraveled(int desiredDistance) {

        rightDrivingEncoder.get();
        return distanceTraveled >= desiredDistance;

    }

    /**
     * Turns after driving backward because the wall is less than ten meters away
     */
    public void rangeFinderTurning() {

        driveSystem.tankDrive(-RobotMap.MAX_AUTO_DRIVING_SPEED, RobotMap.MAX_AUTO_DRIVING_SPEED);

    }

    /**
     * Changes speed of the motor for the robot based on the robot's rate of acceleration until the Robot
     * reaches 0 speed
     */
    private void decelerationMath() {

        double acceleration = accelerometer.getX();

        if (acceleration < -2) {

            rightSideDecelerationSpeed += RobotMap.DECELERATION_SPEED_INCREMENT;

            leftSideDecelerationSpeed += RobotMap.DECELERATION_SPEED_INCREMENT;

        } else if (acceleration > 0) {

            rightSideDecelerationSpeed -= RobotMap.DECELERATION_SPEED_INCREMENT;

            leftSideDecelerationSpeed -= RobotMap.DECELERATION_SPEED_INCREMENT;

        } else if (rightSideDecelerationSpeed < 0.2 && rightSideDecelerationSpeed >= 0) {

            rightSideDecelerationSpeed = 0;

            leftSideDecelerationSpeed = 0;
        }

        /*if (acceleration < -2) {


        } else if (acceleration > 0) {


        } else if (leftSideDecelerationSpeed < 0.2 && leftSideDecelerationSpeed >= 0) {


        }*/

    }

    /**
     * Decelerates while going forward
     */
    public void decelerationForward() {

        decelerationMath();
        driveSystem.tankDrive(leftSideDecelerationSpeed, rightSideDecelerationSpeed);

    }

    public void decelerationTurning(double desiredAngle) {

        decelerationMath();
        if (desiredAngle > 0) {

            if (leftSideDecelerationSpeed < RobotMap.BASICALLY_ZERO &&
                leftSideDecelerationSpeed > -RobotMap.BASICALLY_ZERO) {

                leftSideDecelerationSpeed += RobotMap.DECELERATION_SPEED_INCREMENT;

            }

            if (rightSideDecelerationSpeed < RobotMap.BASICALLY_ZERO &&
                rightSideDecelerationSpeed > RobotMap.BASICALLY_ZERO) {

                rightSideDecelerationSpeed += RobotMap.DECELERATION_SPEED_INCREMENT;

            }

            driveSystem.tankDrive(leftSideDecelerationSpeed, rightSideDecelerationSpeed);

        } else {

            if (leftSideDecelerationSpeed < RobotMap.BASICALLY_ZERO &&
                leftSideDecelerationSpeed > -RobotMap.BASICALLY_ZERO) {

                leftSideDecelerationSpeed -= RobotMap.DECELERATION_SPEED_INCREMENT;

            }

            if (rightSideDecelerationSpeed < RobotMap.BASICALLY_ZERO &&
                rightSideDecelerationSpeed > -RobotMap.BASICALLY_ZERO) {

                rightSideDecelerationSpeed -= RobotMap.DECELERATION_SPEED_INCREMENT;

            }

            driveSystem.tankDrive(-leftSideDecelerationSpeed, -rightSideDecelerationSpeed);

        }

    }

    /**
     * Checks if the robot has reached 0 speed
     *
     * @return If the rightSideDecelerationSpeed and leftSideDecelerationSpeed is zero
     */
    public boolean isSpeedZero() {

        return rightSideDecelerationSpeed < RobotMap.BASICALLY_ZERO &&
               rightSideDecelerationSpeed > -RobotMap.BASICALLY_ZERO &&
               leftSideDecelerationSpeed < RobotMap.BASICALLY_ZERO &&
               leftSideDecelerationSpeed > -RobotMap.BASICALLY_ZERO;
    }

    /**
     * Sets the driveSystem to values that make it turn
     */
    public void turnDesiredAngle(int desiredAngle, double leftTurningSpeed, double rightTurningSpeed) {

        for (; gyro.getAngle() < desiredAngle; ) {

            driveSystem.tankDrive(leftTurningSpeed, rightTurningSpeed);

        }

    }

    /**
     * Resets the encoder to be used again
     */
    public void resetEncoder() {

        rightDrivingEncoder.reset();

    }
    /**
     * Resets the gyro
     */
    public void resetGyro(){

        gyro.reset();

    }

    /**
     * Checks if the robot has turned 90 degrees
     *
     * @return If the robot has turned 90 degrees since the last reset
     */
    public boolean hasRobotTurned(double desiredAngle) {

        return gyro.getAngle() >= desiredAngle;

    }

    /**
     * Checks if the robot has backed up 10 meters
     *
     * @return if the robot has backed up 10 meters
     */
    public boolean properNegativeDistanceTraveled(int desiredDistance) {

        rightDrivingEncoder.get();
        return distanceTraveled <= desiredDistance;

    }

    /**
     * Method which uses paramater of desired distance to travel to go forward or backward
     */
    public void autoDriveDesiredDistance(double desiredDistance) {

        if (desiredDistance > 0) {

            for (; distanceTraveled < desiredDistance; ) {

                rightDrivingEncoder.get();
                driveSystem.tankDrive(RobotMap.MAX_AUTO_DRIVING_SPEED, RobotMap.MAX_AUTO_DRIVING_SPEED);

            }

        } else {

            for (; distanceTraveled < desiredDistance; ) {

                rightDrivingEncoder.get();
                driveSystem.tankDrive(-RobotMap.MAX_AUTO_DRIVING_SPEED, -RobotMap.MAX_AUTO_DRIVING_SPEED);

            }

        }
    }

}

