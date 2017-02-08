package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * if vision tracking doesn't work -----> rangefinder
 */
public class RangeFinderBackupSystem extends Subsystem {

    /** range finder */
    private Ultrasonic rangefinder;
    /** encoder */
    private Encoder    enc;
    /** value from range finder */
    private double     range;
    /** rate of encoder */
    private double     rate;
    /** turn right */
    private boolean    right;
    /** turn left */
    private boolean    left;

    /**
     * initializing
     */
    public void initDefaultCommand() {

        rangefinder = new Ultrasonic(RobotMap.RANGE_FINDER_PORT_1, RobotMap.RANGE_FINDER_PORT_2);
        enc = new Encoder(RobotMap.A_CHANNEL_ENC, RobotMap.B_CHANNEL_ENC);
        enc.setDistancePerPulse(RobotMap.ENCODER_DISTANCE_PER_PULSE);
        rate = enc.getRate();

    }

    /**
     * the robot will drive towards the airship
     * if the range finder does not see the airship the robot will turn around until it sees it
     * once the robot is close enough to the airship (5 inches) it will stop and shoot the ball
     */
    public void rangeFinderDistance() {

        range = rangefinder.getRangeInches();

        if (range < RobotMap.DISTANCE_TO_AIRSHIP) {

            Robot.driveSystem.TankDrive(.5, .5);

        } else if (range > RobotMap.DISTANCE_TO_AIRSHIP) {

            enc.reset();
            Robot.driveSystem.TankDrive(.5, .5);

            if (rate == 5) {

                if (right) {

                    Robot.driveSystem.TankDrive(.5, -.5);

                }
                if (left) {

                    Robot.driveSystem.TankDrive(-.5, .5);

                }

            }

        }

        if (range <= RobotMap.SHOOTING_DISTANCE) {

            Robot.driveSystem.TankDrive(0, 0);
            Robot.shootingSystem.shootingEncoder();

        }

    }

    /**
     *
     * @param turnRight
     * @param turnLeft
     */
    public void rightOrLeft(boolean turnRight, boolean turnLeft) {

        right = turnRight;
        left = turnLeft;

    }

    /**
     * turns
     */
    public void turn() {

        if (rate == 5) {

            if (right) {

                Robot.driveSystem.TankDrive(.5, -.5);

            }
            if (left) {

                Robot.driveSystem.TankDrive(-.5, .5);

            }

        }

    }
}


