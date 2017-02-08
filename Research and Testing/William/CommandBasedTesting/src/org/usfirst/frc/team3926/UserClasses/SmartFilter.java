package org.usfirst.frc.team3926.UserClasses;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 * This class handles the filtering of data for {@link org.usfirst.frc.team3926.subsystems.NetworkVisionProcessing}
 * <p>
 * Created by William Kluge on 2/7/17.
 */
public class SmartFilter {

    /** Table to get contour information from */
    private NetworkTable visionTable;
    /** Distance between two related contours on the x-axis in terms of units of one contours width */
    private double       widthToXRatio;
    /** Distance between two related contours on the y-axis in terms of units of one contours height */
    private double       heightToYRatio;

    /**
     * Constructs the SmartFilter class
     */
    public SmartFilter(double widthToXRatio, double heightToYRatio) {

        this.widthToXRatio = widthToXRatio;
        this.heightToYRatio = heightToYRatio;

    }

    /**
     * Initializes the network table from the Raspberry Pi
     * <p>
     */
    public void initNetworkTables(String tableName) {

        visionTable = NetworkTable.getTable(tableName);
    }

    /**
     * Filters contours based on program configuration
     *
     * @return Index of the contour with the least error
     * TODO finish
     */
    private int smartFilter() {

        SmartFilterData[] contours;

        { //Sets up arrays of contour data

            double[] contourXs = visionTable.getNumberArray(RobotMap.CONTOUR_X_KEY, RobotMap.DEFAULT_VALUE);
            double[] contourYs = visionTable.getNumberArray(RobotMap.CONTOUR_Y_KEY, RobotMap.DEFAULT_VALUE);
            double[] contourWidths = visionTable.getNumberArray(RobotMap.CONTOUR_WIDTH_KEY, RobotMap.DEFAULT_VALUE);
            double[] contourHeights = visionTable.getNumberArray(RobotMap.CONTOUR_HEIGHT_KEY, RobotMap.DEFAULT_VALUE);
            int arrayLength = contourXs.length;
            double[] contourAreas = new double[arrayLength];
            contours = new SmartFilterData[arrayLength];

            if (arrayLength != contourHeights.length || arrayLength != contourYs.length ||
                arrayLength != contourWidths.length)
                throw new IllegalStateException("The lengths of arrays obtained from the vision tables are not equal");

            for (int i = 0; i < contourHeights.length; ++i)
                contourAreas[i] = contourHeights[i] * contourWidths[i];

            for (int i = 0; i < contourXs.length; i++)
                contours[i] = new SmartFilterData(i, contourWidths[i], contourHeights[i], contourXs[i], contourYs[i],
                                                  contourAreas[i]);

        }



    }

    /**
     * Filters contours based on where another contour SHOULD be located.
     *
     * @return TODO finish
     */
    private boolean[] comparisonFilter() {

    }

    /**
     * Filters contours based on where they alone
     *
     * @return TODO finish
     */
    private boolean[] individualFilter() {

    }

}
