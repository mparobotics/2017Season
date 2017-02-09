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
    /** Gets the x value info of each contour from a networktable */
    private double[]     xValues;
    /** Gets the y value info of each contour from a networktable */
    private double[]     yValues;
    /** Gets the height of each contour from a networktable */
    private double[]     contourHeights;
    /**
     * Temporarily used to store the results of a filter while the filter is running so it can be assigned to
     * hasPassedPreviousFilter
     */
    private boolean[]    hasThisPassedCurrentFilter;
    /** Used to check if a contour has passed the previous filter */
    private boolean[]    hasThisPassedPreviousFilter;
    /** An array used to store x values of contours which remain after filtering */
    private double[]     filteredXValues;

    /**
     * Constructs the network table
     * Constructs the xValues array
     * Constructs the areas array
     */
    public VisionTrackingSubsystem() {

        table = NetworkTable.getTable(RobotMap.NETWORK_TABLE_NAME);

        xValues = table.getNumberArray(RobotMap.XVALUE_KEY, new double[0]);

        yValues = table.getNumberArray(RobotMap.YVALUE_KEY, new double[1]);

    }

    /**
     * No default command is needed for this Subsystem
     */
    protected void initDefaultCommand() {

    }

    /**
     * Fills the hasThisPassedPreviousFilter with true
     * Fills the hasThisPassedCurrentFilter with false
     * Runs the filtering methods
     * Uses data from xValues and Filters to make an array of filteredXValues
     */
    public void mainFiltering() {

        Arrays.fill(hasThisPassedPreviousFilter, true);
        Arrays.fill(hasThisPassedCurrentFilter, false);
        usingDistanceBetweenContours();
        partlyFilteredContourArrayCreation();

        for (int i = 0, j = -1; i < xValues.length; i++) {

            if (hasThisPassedPreviousFilter[i] = true) {

                j++;
                filteredXValues[j] = xValues[i];

            }

        }
    }

    /**
     * Stores the speeds of drivesystem for driving toward the shooting target based off the
     * position of retro-reflective tape
     *
     * @return Speeds of robot drivesystem while going forward with vision tracking based off postion of
     * retro-reflective tape
     */
    public double[] visionTrackingForwardSpeeds() {

        mainFiltering();

        double secondVisionSpeed = RobotMap.MAX_VIS_TRACK_SPEED *
                                   ((filteredXValues[0] - (RobotMap.VIS_SCREEN_CENTER)) /
                                    RobotMap.VIS_SCREEN_CENTER);
        return new double[] {(filteredXValues[0] < RobotMap.VIS_SCREEN_CENTER) ?
                             RobotMap.MAX_VIS_TRACK_SPEED * (filteredXValues[0] / RobotMap.VIS_SCREEN_CENTER) :
                             RobotMap.MAX_VIS_TRACK_SPEED,
                             (filteredXValues[0] > RobotMap.VIS_SCREEN_CENTER) ?
                             RobotMap.MAX_VIS_TRACK_SPEED * (filteredXValues[0] / RobotMap.VIS_SCREEN_CENTER) :
                             RobotMap.MAX_VIS_TRACK_SPEED};

    }

    /**
     * Stores the speeds of the drive system for turning toward the shooting target based off the postion of
     * retro-reflective tape
     *
     * @return An array with the speeds of each robot side for turning with vision tracking
     */
    public double[] visionTrackingTurningSpeeds() {

        mainFiltering();

        double[] forwardSpeedArray = visionTrackingForwardSpeeds();

        return new double[] {filteredXValues[0] < RobotMap.VIS_SCREEN_CENTER ? forwardSpeedArray[0] :
                             -forwardSpeedArray[0],
                             filteredXValues[0] > RobotMap.VIS_SCREEN_CENTER ? forwardSpeedArray[1] :
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
    private void usingDistanceBetweenContours() {

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