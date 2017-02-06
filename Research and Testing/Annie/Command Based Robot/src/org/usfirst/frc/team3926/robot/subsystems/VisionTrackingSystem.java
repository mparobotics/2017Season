package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.RobotMap;

import java.util.HashMap;
import java.util.Map;

/**
 * gets values of the contours and translates them into speed
 */
public class VisionTrackingSystem extends Subsystem {

    /** table for accessing the values of the contours */
    private NetworkTable table;
    /** value for accessing values from the network table */
    private double[]     defaultValue;
    /** value for how much to turn */
    private double       adjustmentSpeed;
    /** x value of contour */
    private double[]     x;
    /** y value of contour */
    private double[]     y;
    /** width of contour */
    private double[]     width;
    /** hight of contour */
    private double[]     height;
    /** area of contours */
    double[] areaContours;
    /** if robot is going to turn right */
    private boolean             turnRight;
    /** if robot is going to turn left */
    private boolean             turnLeft;
    /** value being returned */
    private double[]            returnValue;
    /** speed of left motor while driving with vision tracking */
    private double              leftSpeed;
    /** speed of right motor while driving with vision tracking */
    private double              rightSpeed;
    /** for debugging */
    private Map<String, Double> map;
    /** index of contour */
    private int                 index;
    /** array of indexes that passed areaContourCheck */
    private int[]               indexAreaArray;
    /** array of x values of contours */
    private double[]            xContour;
    /** array of indexes that passed placementContourCheck */
    private double[]            indexPlacementArray;
    /** array for putting other things in arrays */
    private boolean[]           returnArray;
    /** array of valid indexes */
    private double[]            indexArray;
    /** best index */
    private double              bestIndex;
    /** target area of contours */
    private double              targetValueArea;
    /** target space between contours */
    private double              targetValueSpaceBetween;

    /**
     * initializes the network table, the return value, map, index, and the defaultValue
     */
    public void initDefaultCommand() {

        table = NetworkTable.getTable(RobotMap.NETWORK_TABLE_KEY);
        xContour = table.getNumberArray(RobotMap.X_KEY, defaultValue);
        indexAreaArray = new int[width.length];
        returnArray = new boolean[width.length];
        indexArray = new double[width.length];
        areaContours = table.getNumberArray(RobotMap.AREA_KEY, defaultValue);
        map = new HashMap<>();
        returnValue = new double[0];
        defaultValue = new double[0];
        index = 0;

    }

    /**
     * sets the speed to x position of contour from vision divided by the center of the screen times the set speed
     * turns towards the target then goes forward
     */
    public double[] visionDriving() {

        x = table.getNumberArray(RobotMap.X_KEY, defaultValue);
        y = table.getNumberArray(RobotMap.Y_KEY, defaultValue);
        width = table.getNumberArray(RobotMap.WIDTH_KEY, defaultValue);
        height = table.getNumberArray(RobotMap.HIGHT_KEY, defaultValue);

        adjustmentSpeed = (x[index] / RobotMap.CENTER_OF_SCREEN_X) * RobotMap.VISION_DRIVING_SET_SPEED;

        leftSpeed = (adjustmentSpeed < RobotMap.CENTER_OF_SCREEN_X) ? adjustmentSpeed :
                    RobotMap.VISION_DRIVING_SET_SPEED;

        rightSpeed = (adjustmentSpeed < RobotMap.CENTER_OF_SCREEN_X) ? RobotMap.VISION_DRIVING_SET_SPEED :
                     adjustmentSpeed;

        turnRight = (adjustmentSpeed < RobotMap.CENTER_OF_SCREEN_X);
        turnLeft = (adjustmentSpeed < RobotMap.CENTER_OF_SCREEN_X);

        SmartDashboard.putBoolean("Turn Right: ", turnRight);
        SmartDashboard.putBoolean("Turn Left: ", turnLeft);

        returnValue[0] = leftSpeed;
        returnValue[1] = rightSpeed;

        if (RobotMap.DEBUGGING)
            debugging();

        return returnValue;
    }

    /**
     * turns the robot toward the target then stops
     */
    public double[] visionTurning() {

        if (turnRight) {

            leftSpeed *= -1;

        } else {

            rightSpeed *= -1;
        }

        if (RobotMap.DEBUGGING)
            debugging();

        return returnValue;
    }

    /**
     * filters out unwanted contours by using other methods
     */
    public void contourFilter(int index) {

        for (int i = 0; i < indexAreaArray.length; i++) {

            areaContourCheck(i);
            placementContourCheck(i);

            targetValueArea = areaContours[i] * areaContours[i];
            targetValueSpaceBetween = (xContour[i] * 2) + 1;

            if (areaContourCheck(i).equals(placementContourCheck(i))) {

                indexArray[i] = i;

                //if (targetValueArea - indexArray[i] < bestIndex)

            }

        }

    }

    /**
     * checks to see if the area of one contour is twice the size of the other contour
     */
    private int[] areaContourCheck(int index) {

        int widthContourLenght = table.getNumberArray(RobotMap.HIGHT_KEY, defaultValue).length;
        int returnArrayLenght = returnArray.length;
        for (index = 0; index < widthContourLenght; index++) {

            for (int i = 0; i < widthContourLenght; i++) {

                for (double areaContour : areaContours) {

                    if (areaContour - (areaContour * RobotMap.ALLOWABLE_ERROR) < areaContours[i] &&
                        areaContour + (areaContour * RobotMap.ALLOWABLE_ERROR) > areaContours[i]) {

                        index = i;
                        returnArray[i] = true;

                    } else {

                        returnArray[i] = false;

                    }

                }

            }

            for (int i = index; i < returnArrayLenght; i++) {

                if (returnArray[i]) {

                    indexAreaArray[i] = i;

                } else {

                    indexAreaArray[i] = RobotMap.ILLEGAL_INT;

                }
            }
        }
        return indexAreaArray;
    }

    /**
     * sees if there is another contour above or below it
     *
     * @param index
     * @return
     */
    private double[] placementContourCheck(int index) {

        for (int i = 0; i < xContour.length; i++) {

            for (int j = 0; j < xContour.length; j++) {

                if ((xContour[i] * 2) * (1 - RobotMap.ALLOWABLE_ERROR) < xContour[j] &&
                    (xContour[i] * 2) + (1 + RobotMap.ALLOWABLE_ERROR) > xContour[j]) {

                    index = i;
                    returnArray[i] = true;

                } else {

                    returnArray[i] = false;

                }

            }

            for (int o = index; o < returnArray.length; o++) {

                if (returnArray[o]) {

                    indexPlacementArray[o] = o;

                } else {

                    indexPlacementArray[o] = RobotMap.ILLEGAL_INT;

                }

            }

        }

        return indexPlacementArray;
    }

    /**
     * displays the x, y, height, and width values of the contour (for debugging)
     */
    private void debugging() {

        map.put(RobotMap.X_KEY, x[index]);
        map.put(RobotMap.Y_KEY, y[index]);
        map.put(RobotMap.HIGHT_KEY, height[index]);
        map.put(RobotMap.WIDTH_KEY, width[index]);
    }
}


