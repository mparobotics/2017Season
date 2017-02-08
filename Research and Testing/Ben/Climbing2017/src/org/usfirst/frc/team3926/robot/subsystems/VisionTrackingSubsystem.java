package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc.team3926.robot.RobotMap;

import java.util.Arrays;

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
    private double[]     yValues;
    private double[]     contourHeights;
    /** Gets the areas of the contours from the networktable */

    private double[]     areas;
    private boolean[]    hasThisPassedCurrentFilter;
    private boolean[]    hasThisPassedPreviousFilter;
    private double[]    filteredxValues;

    /**
     * Constructs the network table
     * Constructs the xValue array
     * Constructs the areas array
     */
    public VisionTrackingSubsystem() {

        table = NetworkTable.getTable(RobotMap.NETWORK_TABLE_NAME);

        xValue = table.getNumberArray(RobotMap.XVALUE_KEY, new double[0]);

        yValues = table.getNumberArray(RobotMap.YVALUE_KEY, new double[1]);

        areas = table.getNumberArray(RobotMap.AREA_KEY, new double[2]);

    }

    /**
     * No default command is needed for this Subsystem
     */
    protected void initDefaultCommand() {

    }

    /**
     * Fills the hasThisPassedPreviousFilter with true
     * FIlls the hasThis
     */
    public void mainFiltering() {

        Arrays.fill(hasThisPassedPreviousFilter, true);
        Arrays.fill(hasThisPassedCurrentFilter, false);
        usingDistanceBetweenContours();
        partlyFilteredContourArrayCreation();

        for(int i = 0, j = -1; i < xValue.length;i++ ){

            if(hasThisPassedPreviousFilter[i] = true){

                j++;
                filteredxValues[j] = xValue[i];

            }

        }
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
                                   ((xValue[0] - (RobotMap.VISION_SCREEN_CENTER)) /
                                    RobotMap.VISION_SCREEN_CENTER);
        return new double[] {(xValue[0] < RobotMap.VISION_SCREEN_CENTER) ?
                             RobotMap.MAX_VISION_TRACKING_SPEED *
                             (xValue[0] / RobotMap.VISION_SCREEN_CENTER) :
                             RobotMap.MAX_VISION_TRACKING_SPEED,
                             (xValue[0] > RobotMap.VISION_SCREEN_CENTER) ?
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

    /**
     * Filters out contours which are not in a pair of contours in which one contour has double the height of the other
     */
    private void partlyFilteredContourArrayCreation() {

        for (int i = 0; i < contourHeights.length; i++) {

            for (int j = 0; j < contourHeights.length; j++) {

                if ((contourHeights[j] > ((1 - RobotMap.ALLOWABLE_ERROR) * (contourHeights[i] / 2))) &&
                    contourHeights[j] < (1 + RobotMap.ALLOWABLE_ERROR) * (contourHeights[i] / 2) &&
                    hasThisPassedPreviousFilter[i] && hasThisPassedPreviousFilter[j]) {

                    hasThisPassedCurrentFilter[i] = true;
                    hasThisPassedCurrentFilter[j] = true;

                }
            }

        }

        hasThisPassedPreviousFilter = hasThisPassedCurrentFilter;
        Arrays.fill(hasThisPassedCurrentFilter, false);

    }

    /**
     * Filters out contours which are not part a pair of contours which have the same distance
     * between each other as half the area of the larger contour
     */
    public void usingDistanceBetweenContours() {

        for (int i = 0; i < yValues.length; i++) {

            for (int j = 0; j < yValues.length; j++) {

                if (hasThisPassedPreviousFilter[i] && hasThisPassedPreviousFilter[j] &&
                    contourHeights[i] > (yValues[i] - yValues[j]) * (1 - RobotMap.ALLOWABLE_ERROR) &&
                    contourHeights[i] < (yValues[i] - yValues[j]) * (1 + RobotMap.ALLOWABLE_ERROR)) {

                    hasThisPassedCurrentFilter[i] = true;
                    hasThisPassedCurrentFilter[j] = true;

                }

            }

        }

        hasThisPassedPreviousFilter = hasThisPassedCurrentFilter;
        Arrays.fill(hasThisPassedCurrentFilter, false);

    }

}
