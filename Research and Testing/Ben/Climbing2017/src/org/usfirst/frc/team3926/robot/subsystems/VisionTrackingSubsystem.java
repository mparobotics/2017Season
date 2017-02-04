package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * Handles variables to make the robot drive based off vision tracking
 *
 * @author Benjamin Lash
 */
public class VisionTrackingSubsystem extends Subsystem {

    /** Constructs the Network Table */
    private NetworkTable table;
    /** Gets the x value info from a networktable */
    private double[]     xValue;

    /**
     * Makes a new network table and an array inside to store the xvalue of objects detected by vision tracking
     */
    public VisionTrackingSubsystem() {

        table = NetworkTable.getTable(RobotMap.NETWORK_TABLE_NAME);

        xValue = table.getNumberArray(RobotMap.NETWORK_TABLE_KEY, new double[0]);

    }

    /**
     * No default command is needed for this Subsystem
     */
    protected void initDefaultCommand() {

    }

    /**
     * Stores the speeds of drivesystem for driving toward the shooting target based off the
     * position of retroreflective tape
     *
     * @return Speeds of robot drivesystem while going forward with vision tracking based off postion of
     * retroreflective tape
     */
    public double[] visionTrackingForwardSpeeds() {

        double secondVisionSpeed = RobotMap.MAX_VISION_TRACKING_SPEED *
                                   ((xValue[0] - (RobotMap.VISION_SCREEN_CENTER)) / RobotMap.VISION_SCREEN_CENTER);
        return new double[] {(xValue[0] < RobotMap.VISION_SCREEN_CENTER) ?
                             RobotMap.MAX_VISION_TRACKING_SPEED * (xValue[0] / RobotMap.VISION_SCREEN_CENTER) :
                             RobotMap.MAX_VISION_TRACKING_SPEED, (xValue[0] > RobotMap.VISION_SCREEN_CENTER) ?
                                                                 secondVisionSpeed :
                                                                 RobotMap.MAX_VISION_TRACKING_SPEED};

    }

    /**
     * Stores the speeds of the drive system for turning toward the shooting target based off the postion of
     * retroreflictive tape
     *
     * @return An array with the speeds of each robot side for turning with vision tracking
     */
    public double[] visionTrackingTurningSpeeds() {

        double[] forwardSpeedArray = visionTrackingForwardSpeeds();

        return new double[] {xValue[0] < RobotMap.VISION_SCREEN_CENTER ? forwardSpeedArray[0] :
                             -forwardSpeedArray[0],
                             xValue[0] > RobotMap.VISION_SCREEN_CENTER ? forwardSpeedArray[1] :
                             -forwardSpeedArray[1]};

    }

}
