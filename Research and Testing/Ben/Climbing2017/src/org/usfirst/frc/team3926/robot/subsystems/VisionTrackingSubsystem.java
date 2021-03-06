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

    /**
     * Constructs the network table
     */
    public VisionTrackingSubsystem() {

        table = NetworkTable.getTable(RobotMap.NETWORK_TABLE_NAME);

    }

    /**
     * No default command is needed for this Subsystem
     */
    protected void initDefaultCommand() {

    }

    /**
     * Stores the speeds of the drive system for turning toward the shooting target based off the postion of
     * retro-reflective tape
     *
     * @return An array with the speeds of each robot side for turning with vision tracking
     */
    public double[] visionTrackingTurningSpeeds() {

        double[] filteredXValues = filteredXValues();

        double[] forwardSpeedArray = visionTrackingForwardSpeeds();

        return new double[] {filteredXValues[0] < RobotMap.VIS_SCREEN_CENTER ?
                             forwardSpeedArray[0] : -forwardSpeedArray[0],
                             filteredXValues[0] > RobotMap.VIS_SCREEN_CENTER ?
                             forwardSpeedArray[1] : -forwardSpeedArray[1]};

    }

    /**
     * An array used to store x values of contours which remain after filtering
     */
    private double[] filteredXValues() {

        double[] xValues = table.getNumberArray(RobotMap.XVALUE_KEY, new double[0]);
        double[] contourHeights = table.getNumberArray(RobotMap.CONTOUR_HEIGHTS_KEY, new double[0]);
        double[] xValuesFiltered = new double[xValues.length];

        boolean[] passedAllFilters = new boolean[xValues.length];
        boolean[] passedCurrentFilter = new boolean[xValues.length];

        Arrays.fill(passedAllFilters, true);
        Arrays.fill(passedCurrentFilter, false);

        partlyFilteredContourArrayCreation(passedAllFilters, passedCurrentFilter, contourHeights);
        usingDistanceBetweenContours(passedAllFilters, passedCurrentFilter, contourHeights);

        for (int i = 0, j = -1; i < xValues.length; i++) {

            if (passedCurrentFilter[i]) {

                j++;
                xValuesFiltered[j] = xValues[i];

            }

        }
        return xValuesFiltered;

    }

    /**
     * Fills the passedPreviousFilter with true
     * Fills the passedCurrentFilter with false
     * Runs the filtering methods
     * Uses data from xValues and Filters to make an array of filteredXValues
     */
    /**
     * Stores the speeds of drivesystem for driving toward the shooting target based off the
     * position of retro-reflective tape
     *
     * @return Speeds of robot drivesystem while going forward with vision tracking based off position of
     * retro-reflective tape
     */
    public double[] visionTrackingForwardSpeeds() {

        double[] filteredXValues = filteredXValues();

        double forwardVisTrackSpeed1 = RobotMap.MAX_VIS_TRACK_SPEED * (filteredXValues[0] / RobotMap.VIS_SCREEN_CENTER);
        double forwardVisTrackSpeed2 = RobotMap.MAX_VIS_TRACK_SPEED;

        return new double[] {(filteredXValues[0] < RobotMap.VIS_SCREEN_CENTER) ?
                             forwardVisTrackSpeed1 : forwardVisTrackSpeed2,
                             (filteredXValues[0] > RobotMap.VIS_SCREEN_CENTER) ?
                             forwardVisTrackSpeed1 : forwardVisTrackSpeed2};

    }

    /**
     * Filters out contours which are not in a pair of contours in which one contour has double the height of the other
     */
    private void partlyFilteredContourArrayCreation(boolean[] passedPreviousFilter, boolean[] passedCurrentFilter,
                                                    double[] contourHeights) {

        for (int i = 0; i < contourHeights.length; i++) {

            for (int j = 0; j < contourHeights.length; j++) {

                if ((contourHeights[j] > ((1 - RobotMap.ALLOWABLE_ERROR) * (contourHeights[i] / 2))) &&
                    contourHeights[j] < (1 + RobotMap.ALLOWABLE_ERROR) * (contourHeights[i] / 2) &&
                    passedPreviousFilter[i] && passedPreviousFilter[j]) {

                    passedCurrentFilter[i] = true;
                    passedCurrentFilter[j] = true;

                }

            }

        }

        passedPreviousFilter = passedCurrentFilter;
        Arrays.fill(passedCurrentFilter, false);

    }

    /**
     * Filters out contours which are not part a pair of contours which have the same distance
     * between each other as half the area of the larger contour
     */
    private void usingDistanceBetweenContours(boolean[] passedPreviousFilter, boolean[] passedCurrentFilter,
                                              double[] contourHeights) {

        double[] yValues = table.getNumberArray(RobotMap.YVALUE_KEY, new double[1]);

        for (int i = 0; i < yValues.length; i++) {

            for (int j = 0; j < yValues.length; j++) {

                if (passedPreviousFilter[i] && passedPreviousFilter[j] &&
                    contourHeights[i] > (yValues[i] - yValues[j]) * (1 - RobotMap.ALLOWABLE_ERROR) &&
                    contourHeights[i] < (yValues[i] - yValues[j]) * (1 + RobotMap.ALLOWABLE_ERROR)) {

                    passedCurrentFilter[i] = true;
                    passedCurrentFilter[j] = true;

                }

            }

        }

        passedPreviousFilter = passedCurrentFilter;
        Arrays.fill(passedCurrentFilter, false);

    }

}