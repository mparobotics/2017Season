package org.usfirst.frc.team3926.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.ConnectionInfo;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.RobotMap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.usfirst.frc.team3926.robot.RobotMap.ILLEGAL_INT;
import static org.usfirst.frc.team3926.robot.RobotMap.SCREEN_CENTER;

/**
 * Notes on vision processing:
 * <p>
 * (1/20/2017:4:40PM) If looking at a piece of (retro-reflective) tape from the side, it's position may indicate that
 * it should move in the opposite direction of where it should go. I (William Kluge) do not think this should be an
 * issue in the 2017 game because the tape is wrapped around a bin, so it's center should actually be in the center.
 * (For an example of side view on a rectangular piece of tape see photo RectangularTapeSideView.png)
 * </p>
 * <p>
 * (1/20/2017:4:44PM) So looking at the tube actually didn't solve the problem when at an extreme angle. While this
 * angle kind of angle isn't likely to actually happen in the game, I'm still going to look into solving it just in
 * case (For an example of extreme angle view on tube see TubeExtremeAngle.png)
 * </p>
 * <p>
 * (1/21/2017:11:25AM) I think that the left and right sides were reversed. Switching the signs to test now
 * </p>
 * <p>
 * (1/21/2017:11:29AM) So left and right were not reversed, but in some cases it still says to go left when it
 * should be going right ot vise-versa. (see IncorrectMoveRight.png for example)
 * </p>
 * <p>
 * (1/21/2017:12:04PM) I have decided to make a method (see (1/24/2017:4:30PM) for reason why this no longer
 * exists) that logs whether or not a direction is correct when we are driving with vision tracking so that we
 * can tune the algorithm based on the data we collect. I will likely have to make another program to find
 * patterns in that data.
 * </p>
 * <p>
 * (1/24/2017:4:19PM) I now have the first version of the drive and center methods done. For the turning method
 * I'm just taking the data from the straight method and subtracting it from one, than multiplying the opposite
 * direction to drive times -1. This <i>should</i> let us do a 0 point turn...but my math might totally be off
 * </p>
 * <p>
 * (1/24/2017:4:30PM) I removed logIncorrectAction because it has been replaced by using a Hashmap that I store
 * debug data along with driving data in.
 * </p>
 * <p>
 * (1/26/2017:4:05PM) I created the {@link #smartFilterContours(int)} function so that we can use a less strict contour
 * finding algorithm and filter for contours that are close together. This should let us detect in many different
 * different situations with (basically) guaranteed same results
 * </p>
 *
 * @author William Kluge
 *         <p>
 *         Contact: klugewilliam@gmail.com
 *         </p>
 */
public class NetworkVisionProcessing extends Subsystem {

    /** Contour report from the Raspberry Pi */
    private NetworkTable contourReport;
    /** Booleans to put on the dashboard that represent information on the state of contours */
    private boolean      contoursFound, moveRight, moveLeft;
    /** Keeps track of the last few speeds to ensure returned values aren't spikes due to temporary interruptions */
    private double[][] speedBuffer;
    /** The last valid speed seen with the buffer */
    private double[]   lastValidSpeed;

    ////////////////////////////////////// Constructors and Initializer ////////////////////////////////////////////////

    /**
     * Constructs the NetworkVisionProcessing class
     */
    public NetworkVisionProcessing() {

        if (RobotMap.USE_SPEED_BUFFER) {
            speedBuffer = new double[RobotMap.VISION_SPEED_BUFFER_SIZE][2];

            for (double[] i : speedBuffer)
                i[0] = i[1] = 0;

            lastValidSpeed = new double[] {0, 0};

        }

    }

    /**
     * Method to set the default command for this subsystem.
     * <p>
     * Note: We currently do not have a command that should be set as default
     * </p>
     */
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new DriveToVisionTarget());
    }

    /**
     * Initializes the network table from the Raspberry Pi
     * <p>
     * Note: If it is initialized from the constructor you will get a load of errors from the robot about tables
     * already being initialized. I think this has something to do with how the code is loaded onto the robot
     * </p>
     */
    public void initNetworkTables() {

        contourReport = NetworkTable.getTable(RobotMap.TABLE_NAME);
    }

    ///////////////////////////////////////////////// Moving the Robot /////////////////////////////////////////////////

    /**
     * Finds the correction needed to center the robot on a specified contour
     *
     * @param index Index of the contour to get the center of
     * @return Correction needed to move to the target and, if {@link RobotMap#DEBUG} is true, information on the
     * contour that produced the correction.
     */
    public Map<String, Double> moveToCenter(int index) {

        int checkIndex;

        if (RobotMap.USE_SMART_FILTER)
            checkIndex = smartFilterContours(index);
        else
            checkIndex = index;

        double contourCenter = getContours(RobotMap.CONTOUR_X_KEY, checkIndex);

        Map<String, Double> returnValue = new HashMap<>();

        if (RobotMap.DEBUG)
            returnValue = addDebugData(returnValue, checkIndex);

        if (contourCenter != RobotMap.ILLEGAL_DOUBLE) {

            contoursFound = true;

            double[] movement = {RobotMap.AUTONOMOUS_SPEED, RobotMap.AUTONOMOUS_SPEED};

            if (contourCenter > SCREEN_CENTER[0]) { //turn to vision reversed
                movement[0] = 1 - (((contourCenter - SCREEN_CENTER[0]) / SCREEN_CENTER[0]) * RobotMap.AUTONOMOUS_SPEED);
                moveLeft = false;
                moveRight = true;
            } else if (contourCenter < SCREEN_CENTER[0]) {
                movement[1] = 1 - ((contourCenter / SCREEN_CENTER[0]) * RobotMap.AUTONOMOUS_SPEED);
                moveRight = false;
                moveLeft = true;
            } else {
                moveLeft = false;
                moveRight = false;
            }

            returnValue.put(RobotMap.SPEED_RIGHT_KEY, movement[0]);
            returnValue.put(RobotMap.SPEED_LEFT_KEY, movement[1]);

        } else {
            returnValue.put(RobotMap.SPEED_RIGHT_KEY, RobotMap.ILLEGAL_DOUBLE);
            returnValue.put(RobotMap.SPEED_LEFT_KEY, RobotMap.ILLEGAL_DOUBLE);
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
     * @return Speed to set tank drive to turn towards the center
     */
    public Map<String, Double> turnToCenter(int index) {

        Map<String, Double> returnValue = moveToCenter(index);

        if (returnValue.get(RobotMap.SPEED_LEFT_KEY) == RobotMap.ILLEGAL_DOUBLE)
            return returnValue;

        double rightSpeed = returnValue.get(RobotMap.SPEED_RIGHT_KEY);
        double leftSpeed = returnValue.get(RobotMap.SPEED_LEFT_KEY);

        if (moveLeft)
            rightSpeed *= -1;
        else
            leftSpeed *= -1;

        returnValue.replace(RobotMap.SPEED_RIGHT_KEY, rightSpeed);
        returnValue.replace(RobotMap.SPEED_LEFT_KEY, leftSpeed);

        return returnValue;

    }

    ////////////////////////////////////////////////// Debugging Data //////////////////////////////////////////////////

    /**
     * Prints the info from contourReport to the log
     */
    public void printTableInfo() {

        double[] centerXs = contourReport.getNumberArray("x", RobotMap.DEFAULT_VALUE);
        double[] centerYs = contourReport.getNumberArray("y", RobotMap.DEFAULT_VALUE);
        double[] heights = contourReport.getNumberArray("height", RobotMap.DEFAULT_VALUE);
        double[] widths = contourReport.getNumberArray("width", RobotMap.DEFAULT_VALUE);

        if (centerXs.length != 0) {
            for (int i = 0; i < centerXs.length; ++i) {
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
     * Adds debugging data to the data Map for drive control functions
     *
     * @param data  Driving data to add contour information to
     * @param index Index of the contour to add data on
     * @return Data map with contour data added in
     */
    private Map<String, Double> addDebugData(Map<String, Double> data, int index) {

        data.put(RobotMap.CONTOUR_X_KEY, getContours(RobotMap.CONTOUR_X_KEY, index));
        data.put(RobotMap.CONTOUR_Y_KEY, getContours(RobotMap.CONTOUR_Y_KEY, index));
        data.put(RobotMap.CONTOUR_HEIGHT_KEY, getContours(RobotMap.CONTOUR_HEIGHT_KEY, index));
        data.put(RobotMap.CONTOUR_WIDTH_KEY, getContours(RobotMap.CONTOUR_WIDTH_KEY, index));

        return data;

    }

    /**
     * The method to use to end vision tracking commands when debugging (it never ends)
     *
     * @return false
     */
    public boolean debugEndCommand() {

        return false;
    }

    ////////////////////////////////////////// Methods for Obtaining Contours //////////////////////////////////////////

    /**
     * Function to return a specific contour
     *
     * @param key   Key to use when getting contours from the table
     * @param index Array index of the contour to get
     * @return Either the desired value for the value and index or an illegal character if an exception occurred
     */
    private double getContours(String key, int index) {

        try {

            return contourReport.getNumberArray(key, new double[0])[index];

        } catch (Exception e) {

            System.out.println(new Date().toString() + ": " + e.getMessage());
            return RobotMap.ILLEGAL_DOUBLE;

        }

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

    ///////////////////////////////////////////////// Contour Filtering ////////////////////////////////////////////////

    /**
     * Allows the filtering of contour data past what GRIP can do
     *
     * @param index The index of the contour to check
     * @return index (if it is a valid contour), otherwise the next valid contour (if there are valid contours),
     * otherwise {@link RobotMap#ILLEGAL_INT}
     */
    private int smartFilterContours(int index) {

        double[] contourXs = contourReport.getNumberArray(RobotMap.CONTOUR_X_KEY, RobotMap.DEFAULT_VALUE);
        double[] contourYs = contourReport.getNumberArray(RobotMap.CONTOUR_Y_KEY, RobotMap.DEFAULT_VALUE);
        double[] contourHeights = contourReport.getNumberArray(RobotMap.CONTOUR_HEIGHT_KEY, RobotMap.DEFAULT_VALUE);
        double[] contourWidths = contourReport.getNumberArray(RobotMap.CONTOUR_WIDTH_KEY, RobotMap.DEFAULT_VALUE);
        double[] contourAreas = new double[contourHeights.length];

        if (!checkEquality(contourXs.length, contourYs.length, contourHeights.length, contourWidths.length))
            return ILLEGAL_INT;

        for (int i = 0; i < contourHeights.length; ++i)
            contourAreas[i] = contourHeights[i] * contourWidths[i];

        boolean[] validAreas = validateContourAreas(contourAreas);

        if (validAreas[index])
            return index;

        for (int i = 0; i < validAreas.length; ++i)
            if (validAreas[index])
                return index;

        return ILLEGAL_INT;

    }

    /**
     * Checks the contours based on area.
     * This checks to make sure that a contour has another contour with about double/half of it's area.
     * <p>
     * This is used in {@link #smartFilterContours(int)}
     * </p>
     *
     * @return Indexes that match the criteria of this method
     */
    private boolean[] validateContourAreas(double[] contourAreas) {

        boolean[] validatedIndexes = new boolean[contourAreas.length];

        for (int i = 0; i < contourAreas.length; ++i) {

            validatedIndexes[i] = false;

            for (double checkArea : contourAreas) {

                if (checkArea - (checkArea * RobotMap.ALLOWABLE_ERROR) < contourAreas[i] &&
                    contourAreas[i] < checkArea + (checkArea + RobotMap.ALLOWABLE_ERROR))
                    validatedIndexes[i] = true;

            }

        }

        return validatedIndexes;

    }

    /**
     * Corrects viewing the target from odd angles. This is not for turning the robot, this solely exists to correct
     * errors caused by viewing the target from a bad angle.
     * <p>
     * Note: See Notes on Vision Processing 1/20/2017:4:44 for reason why this exists
     * </p>
     * TODO finish
     */
    private double[] correctAngleOffset() {

        return new double[] {0};

    }

    ///////////////////////////////////////////////// Speed Buffering //////////////////////////////////////////////////

    /**
     * TODO finish
     * @param data
     * @return
     */
    private Map<String, Double> bufferSpeeds(Map<String, Double> data) {

        return data;

    }

    ////////////////////////////////////////////////// Other Methods ///////////////////////////////////////////////////

    /**
     * Checks if a bunch of numbers are equal
     *
     * @param numbers the numbers to check
     * @return If the numbers are equal
     */
    private boolean checkEquality(int... numbers) {

        for (int number : numbers)
            for (int x : numbers)
                if (number != x)
                    return false;

        return true;

    }

}

