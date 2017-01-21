package org.usfirst.frc.team3926.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.ConnectionInfo;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.RobotMap;

import java.util.Date;

/**
 * Notes on vision processing:
 * <p>
 * (1/20/2017:4:40) If looking at a piece of (retro-reflective) tape from the side, it's position may indicate that it
 * should move in the opposite direction of where it should go. I (William Kluge) do not think this should be an issue
 * in the 2017 game because the tape is wrapped around a bin, so it's center should actually be in the center.
 * (For an example of side view on a rectangular piece of tape see photo RectangularTapeSideView.png)
 * <p>
 * (1/20/2017:4:44) So looking at the tube actually didn't solve the problem when at an extreme angle. While this
 * angle kind of angle isn't likely to actually happen in the game, I'm still going to look into solving it just in case
 * (For an example of extreme angle view on tube see TubeExtremeAngle.png)
 */
public class NetworkVisionProcessing extends Subsystem {

    /**
     * Total size of the camera's image
     */
    private final int IMAGE_X = 320, IMAGE_Y = 240;

    /**
     * Center point on the screen
     */
    private final int[] SCREEN_CENTER = {IMAGE_X / 2, IMAGE_Y / 2};

    /**
     * Contour report from the Raspberry Pi
     */
    private NetworkTable contourReport;

    /**
     * Booleans to put on the dashboard that represent information on the state of contours
     */
    private boolean contoursFound, moveRight, moveLeft;

    /**
     * Constructs the NetworkVisionProcessing class
     */
    public NetworkVisionProcessing() {

    }

    /**
     * Initializes the network table from the Raspberry Pi
     *
     * @note If it is initialized from the constructor you will get a load of errors from the robot about tables already
     * being initialized. I think this has something to do with how the code is loaded onto the robot
     */
    public void initNetworkTables() {

        contourReport = NetworkTable.getTable("vision/high_goal");
    }

    /**
     * Finds the correction needed to center the robot on a specified contour
     *
     * @param index Index of the contour to get the center of
     * @return The correction needed to move to the target
     */
    public double[] moveToCenter(int index) {

        double contourCenter = getContours("x", index);

        double[] returnValue;

        if (contourCenter != RobotMap.ILLEGAL_DOUBLE) {

            contoursFound = true;

            double[] movement = {RobotMap.AUTONOMOUS_SPEED, RobotMap.AUTONOMOUS_SPEED};

            if (contourCenter < SCREEN_CENTER[0]) {
                movement[0] = contourCenter / SCREEN_CENTER[0];
                moveLeft = false;
                moveRight = true;
            } else if (contourCenter > SCREEN_CENTER[0]) {
                movement[1] = Math.pow(contourCenter / SCREEN_CENTER[0], - 1);
                moveRight = false;
                moveLeft = true;
            }

            returnValue = movement;

        } else {
            returnValue = RobotMap.ILLEGAL_VALUE;
            contoursFound = false;
        }

        SmartDashboard.putBoolean("Move Left", moveLeft);
        SmartDashboard.putBoolean("Move Right", moveRight);
        SmartDashboard.putBoolean("Contours Found", contoursFound);

        return returnValue;

    }

    /**
     * Finds the turn rate to turn the robot towards the vision target
     *
     * @param index Index of the contour to center on
     * @return Speed to set tank drive to center {(right), (left)}
     * TODO finish
     */
    public double[] turnToCenter(int index) {

        double[] contourCenter = contourReport.getNumberArray("x", new double[0]);

        return new double[0];

    }

    /**
     * Prints the info from contourReport to the log
     */
    public void printTableInfo() {

        double[] centerXs = contourReport.getNumberArray("x", RobotMap.DEFAULT_VALUE);
        double[] centerYs = contourReport.getNumberArray("y", RobotMap.DEFAULT_VALUE);
        double[] heights = contourReport.getNumberArray("height", RobotMap.DEFAULT_VALUE);
        double[] widths = contourReport.getNumberArray("width", RobotMap.DEFAULT_VALUE);

        if (centerXs.length != 0) {
            for (int i = 0; i < centerXs.length; ++ i) {
                try {
                    System.out.println("Printing data for contour " + i);
                    System.out.println("\tCenter X: " + centerXs[i]);
                    System.out.println("\tCenter Y: " + centerYs[i]);
                    System.out.println("\tHeight: " + heights[i]);
                    System.out.println("\tWidth: " + widths[i]);
                } catch (Exception ArrayIndexOutOfBoundsException) {
                    System.out.print("Exception accessing array variables");
                }
            }
            contoursFound = true;
        } else {
            contoursFound = false;
        }

        SmartDashboard.putBoolean("Contours Found", contoursFound);

    }

    /**
     * Prints the information on the connections to the robot
     */
    public void printConnectionInfo() {

        System.out.println(NetworkTable.connections().length + " active connections");

        for (ConnectionInfo i : NetworkTable.connections()) {
            System.out.println(i.remote_ip);
        }

    }

    /**
     * The method to use to end vision tracking commands when debugging (it never ends)
     *
     * @return false
     */
    public boolean debugEndCommand() {

        return false;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new DriveToVisionTarget());
    }

    /**
     * Corrects viewing the target from odd angles
     *
     * @note See Notes on Vision Processing 1/20/2017:4:44 for reason why this exists
     */
    private double[] correctAngleOffset() {

        return new double[]{0};

    }

    /**
     * Overloaded getContours function that returns the entire array
     *
     * @param key Key to use when getting contours from the network table
     */
    private double[] getContours(String key) {

        try {

            return contourReport.getNumberArray(key, new double[0]);

        } catch (Exception e) {

            System.out.println(new Date().toString() + ": " + e.getMessage());
            return RobotMap.ILLEGAL_VALUE;

        }

    }

    /**
     * Function to return a specific contour
     *
     * @param key   Key to use when getting contours from the table
     * @param index Array index of the contour to get
     * @return Either the desired value for
     */
    private double getContours(String key, int index) {

        try {

            return contourReport.getNumberArray(key, new double[0])[index];

        } catch (Exception e) {

            System.out.println(new Date().toString() + ": " + e.getMessage());
            return RobotMap.ILLEGAL_DOUBLE;

        }

    }

}

