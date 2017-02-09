package org.usfirst.frc.team3926.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.DriveCommand;

/**
 * drives the robot
 */
public class DriveSystem extends Subsystem {

    private RobotDrive robotDrive;
    /** range finder */
    private Ultrasonic rangefinder;
    /** value from range finder */
    private double     range;
    /** encoder */
    private Encoder    enc;
    /** rate of encoder */
    private double     rate;
    /** turn right */
    private boolean    right;
    /** turn left */
    private boolean    left;

    /**
     * declares driving motors and tank drive
     */
    public DriveSystem() {

        CANTalon TalonSRX_FR = new CANTalon(RobotMap.FR_MOTOR);
        CANTalon TalonSRX_BR = new CANTalon(RobotMap.BR_MOTOR);
        CANTalon TalonSRX_FL = new CANTalon(RobotMap.FL_MOTOR);
        CANTalon TalonSRX_BL = new CANTalon(RobotMap.BL_MOTOR);

        robotDrive = new RobotDrive(TalonSRX_BL, TalonSRX_BR, TalonSRX_FL, TalonSRX_FR);
        rangefinder = new Ultrasonic(RobotMap.RANGE_FINDER_PORT_1, RobotMap.RANGE_FINDER_PORT_2);
        enc = new Encoder(RobotMap.A_CHANNEL_ENC, RobotMap.B_CHANNEL_ENC);
        enc.setDistancePerPulse(RobotMap.ENCODER_DISTANCE_PER_PULSE);

    }

    @Override

    /**
     * if it can it will call the drive command
     */
    protected void initDefaultCommand() {

        new DriveCommand();
    }

    /**
     * driving with vision tracking
     */
    public void SetSpeed(double LSpeed, double RSpeed) {

        Robot.visionTrackingSystem.visionDriving();
        robotDrive.tankDrive(LSpeed, RSpeed);

    }

    /** tank drive */
    public void TankDrive(double speedR, double speedL) {

        robotDrive.tankDrive(speedL, speedR);

    }

    /** driving straight with rangefinder */
    public boolean drivingWithRangefinder(double distance) {

        range = rangefinder.getRangeInches();
        rate = enc.getRate();
        Robot.driveSystem.TankDrive(.5, .5);

        if (range <= distance) {

            return true;

        } else if (rate > distance) {

            return true;

        }

        return false;
    }

    public boolean turningWithRangeFinder(double distance) {

        if (range <= distance++) {

            return true;

        } else {

            Robot.rangeFinderBackupSystem.rightOrLeft(right, left);

            if (right) {

                Robot.driveSystem.TankDrive(.5, -.5);

            } else if (left) {

                Robot.driveSystem.TankDrive(-.5, .5);

            }

            Robot.driveSystem.TankDrive(.5, .5);

            if (range <= distance++) {

                return true;

            }
        }

        return false;

    }

    public void encReset() {

        enc.reset();

    }

}
