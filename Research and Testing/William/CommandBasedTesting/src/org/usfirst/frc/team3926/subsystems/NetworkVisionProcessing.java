package org.usfirst.frc.team3926.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.ConnectionInfo;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 *
 */
public class NetworkVisionProcessing extends Subsystem {

    /** The total size of the camera's image */
    private final int IMAGE_X = 320, IMAGE_Y = 240;
    /** The center point on the screen */
    private final int[] SCREEN_CENTER = {IMAGE_X/2, IMAGE_Y/2};
    /** The contour report from OpenCV */
    private NetworkTable contourReport;
    /**  */
    private boolean contoursFound, moveRight, moveLeft;

    /**
     * Constructs the NetworkVisionProcessing class
     * @note the GRIP report needs to be named contourReport
     */
    public NetworkVisionProcessing() {

    }

    public void initNetworkTables() {
        contourReport = NetworkTable.getTable("vision/high_goal");
    }

    /**
     * Finds the correction needed to center the robot on a specified contour
     * @param index The index of the contour to get the center of
     * @return The correction needed to move to the target
     */
    public double[] moveToCenter(int index) {

        double[] contourCenter = contourReport.getNumberArray("x", new double[0]);

        double[] returnValue;

        if (contourCenter.length > index) {

            contoursFound = true;

            double[] movement = {RobotMap.AUTONOMOUS_SPEED, RobotMap.AUTONOMOUS_SPEED};

            if (contourCenter[index] < SCREEN_CENTER[0]) {
                movement[0] *= contourCenter[index] / SCREEN_CENTER[0];
                moveLeft = true;
                moveRight = false;
            } else if (contourCenter[index] > SCREEN_CENTER[0]) {
                movement[1] *= contourCenter[index] / SCREEN_CENTER[0];
                moveRight = true;
                moveLeft = false;
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
     * Prints the info from contourReport to the log
     */
    public void printTableInfo() {

        System.out.println(NetworkTable.connections().length + " active connections");

        for (ConnectionInfo i: NetworkTable.connections()) {
            System.out.println(i.remote_ip);
        }

        double[] centerXs = contourReport.getNumberArray("x", RobotMap.DEFAULT_VALUE);
        double[] centerYs = contourReport.getNumberArray("y", RobotMap.DEFAULT_VALUE);
        double[] heights  = contourReport.getNumberArray("height", RobotMap.DEFAULT_VALUE);
        double[] widths   = contourReport.getNumberArray("width", RobotMap.DEFAULT_VALUE);


        if (centerXs.length != 0) {
            for (int i = 0; i < centerXs.length; ++i) {
                try {
                    SmartDashboard.putNumber("Printing data for contour ", i);
                    SmartDashboard.putNumber("\tCenter X: ", centerXs[i]);
                    SmartDashboard.putNumber("\tCenter Y: ", centerYs[i]);
                    SmartDashboard.putNumber("\tHeight: ", heights[i]);
                    SmartDashboard.putNumber("\tWidth: ", widths[i]);
                } catch (Exception ArrayIndexOutOfBoundsException) {
                    System.out.print("Exception accesing array variables");
                }
            }
            contoursFound = true;
        } else {
            SmartDashboard.putString("no contours", "nope");
            contoursFound = false;
        }

        SmartDashboard.putBoolean("Contours Found", contoursFound);

    }

    /**
     * The method to use to end vision tracking commands when debugging (it never ends)
     * @return false
     */
    public boolean debugEndCommand() {
        return false;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new DriveToVisionTarget());
    }
}

