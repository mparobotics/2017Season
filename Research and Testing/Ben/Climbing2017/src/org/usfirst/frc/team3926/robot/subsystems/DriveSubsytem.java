package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.DriveCommand;

/**
 * handles the robot's driving functionality
 * @author Benjamin Lash
 */
public class DriveSubsytem extends Subsystem {

    /**
     * makes an encoder to track the forward motion of the robot
     */
    private static Encoder drivingEncoder;

    private static RobotDrive driveSystem;

    private static Talon talonBR;
    private static Talon talonFR;
    private static Talon talonBL;
    private static Talon talonFL;

    double distanceTraveled;

    private static Accelerometer accelerometer;

    private static Gyro gyro;

    /**
     * creates a variable to track the acceleration of the motors
     */
    static double acceleration;
    /**
     * sets the default speed for the deceleration function to zero
     */
    private double speed;

    double currentAngle;

    public DriveSubsytem() {

        drivingEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);

        driveSystem = new RobotDrive(talonFL, talonBL, talonFR, talonBR);

        talonBR = new Talon(RobotMap.TALON_BR_PORT);
        talonFR = new Talon(RobotMap.TALON_FR_PORT);
        talonBL = new Talon(RobotMap.TALON_BL_PORT);
        talonFL = new Talon(RobotMap.TALON_FL_PORT);

        accelerometer = new BuiltInAccelerometer(Accelerometer.Range.k4G);

        gyro = new AnalogGyro(RobotMap.GYRO_PORT);

        speed = RobotMap.DEFAULT_DECELERATION_SPEED;
    }

    /**
     * makes a gyro to track the position of the Robot
     */






    /**
     * sets the default command to DriveCommand
     */
    public void initDefaultCommand() {

        new DriveCommand();
    }

    /**
     * plugs in leftStickHeight and rightStickHeight to the driveSystem
     * @param rightStickHeight
     * @param leftStickHeight
     * @param leftButtonStatus
     */
    public static void driveMethod(double rightStickHeight, double leftStickHeight, boolean leftButtonStatus) {


        if (leftButtonStatus) {

            rightStickHeight = leftStickHeight;
        }

        driveSystem.tankDrive(leftStickHeight, rightStickHeight);


    }

    public void driveForward() {
        /**
         * this sets the speed of the motors to full to go foward
         * then it gets the distance that the robot has traveled
         */
        driveSystem.tankDrive(1, 1);
        distanceTraveled = drivingEncoder.getDistance();
    }

    /**
     * adds or lowers the speed of the motor for the robot depending on the rate of acceleration to decelerate the
     * robot until it is told to stop
     */

    public void deceleration() {

        acceleration = accelerometer.getX();


        if (acceleration < - 5) {
            speed += RobotMap.DRIVE_FORWARD_SPEED_INCREMENT;


        } else if (acceleration > 0) {
            speed -= RobotMap.DRIVE_FORWARD_SPEED_INCREMENT;
        } else {

            speed = 0;
        }

        driveSystem.tankDrive(speed, speed);

    }

    /**
     * @return if the speed of the robot is zero
     */
    public boolean isSpeedZero() {

        return speed == 0;

    }

    /**
     * sets the driveSystem to values that make it turn
     */
    public void turning() {

        driveSystem.tankDrive(- 1, 1);
    }

    /**
     * resets the encoder to be used again
     */
    public void resetEncoder(){
        drivingEncoder.reset();
    }

    /**
     * gets the robots current angle with the gyro to find it's position
     */
    public void Gyro() {

        currentAngle = gyro.getAngle();
    }
    
    public static void visionTrackingMovement(double visionTrackingLeftSpeed, double visionTrackingRightSpeed){
    	driveSystem.tankDrive(visionTrackingLeftSpeed,visionTrackingRightSpeed);
    }


    /**
     * @return if the robot has turned 90 degrees since the last reset
     */
    public boolean HasRobotTurned(){
        return currentAngle >= 90;
    }


    /**
     * @return if the robot has traveled ten meters
     */
    public boolean tenMetersTraveled() {

        return distanceTraveled >= 10;
    }
}

