package org.usfirst.frc.team3926.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.ConnectionInfo;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.RobotMap;

import java.util.*;

import static org.usfirst.frc.team3926.robot.RobotMap.*;

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

    /** Enables/Disables offset checking on the x-axis */
    private boolean      XAxisOffsetCheck;
    /** Enables/Disables offset checking on the y-axis */
    private boolean      YAxisOffsetCheck;
    /** Contour report from the Raspberry Pi */
    private NetworkTable contourReport;
    /** Booleans to put on the dashboard that represent information on the state of contours */
    private boolean      contoursFound, moveRight, moveLeft, centered;
    /** Keeps track of the last few speeds to ensure returned values aren't spikes due to temporary interruptions */
    private List<double[]>      speedBuffer;
    /** Last valid speed from the buffer */
    private Map<String, Double> lastValidSpeed;
    /** Distance between two contours in terms of a ratio of their total size. */
    private double              contourXOffsetRatio, contourYOffsetRatio;

    ////////////////////////////////////// Constructors and Initializer ////////////////////////////////////////////////

    /**
     * Constructs the NetworkVisionProcessing class
     */
    public NetworkVisionProcessing() {

        if (RobotMap.USE_SPEED_BUFFER) {
            speedBuffer = new Vector<>();

            for (int i = 0; i < RobotMap.SPEED_BUFFER_SIZE; ++i)
                speedBuffer.add(0, new double[] {0, 0});

            lastValidSpeed = new HashMap<>();
            lastValidSpeed.put(RobotMap.SPEED_RIGHT_KEY, 0.0);
            lastValidSpeed.put(RobotMap.SPEED_LEFT_KEY, 0.0);

        }

        XAxisOffsetCheck = true;
        YAxisOffsetCheck = true;

    }

    /**
     * Method to set the default command for this subsystem.
     * <p>
     * Note: We currently do not have a command that should be set as default
     * </p>
     */
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new DriveToHighGoalTarget());
    }

    /**
     * Initializes the network table from the Raspberry Pi
     * <p>
     * Note: If it is initialized from the constructor you will get a load of errors from the robot about tables
     * already being initialized. I think this has something to do with how the code is loaded onto the robot
     * </p>
     */
    public void initNetworkTables(String tableName, double xOffsetRatio, double yOffsetRatio) {

        contourReport = NetworkTable.getTable(tableName);
        contourXOffsetRatio = xOffsetRatio;
        contourYOffsetRatio = yOffsetRatio;
    }

    ////////////////////////////////////////////// Variable Configuration //////////////////////////////////////////////

    /**
     * Setter enable/disable offset checking of various axis
     *
     * @param XAxisOffsetCheck New value for XAxisOffsetCheck
     * @param YAxisOffsetCheck New value for YAxisOffsetCheck
     */
    public void setXAxisOffsetCheck(boolean XAxisOffsetCheck, boolean YAxisOffsetCheck) {

        this.XAxisOffsetCheck = XAxisOffsetCheck;
        this.YAxisOffsetCheck = YAxisOffsetCheck;
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

        if (contourCenter != RobotMap.ILLEGAL_DOUBLE && checkIndex != RobotMap.ILLEGAL_INT) {

            contoursFound = true;

            double[] movement = {RobotMap.AUTONOMOUS_SPEED, RobotMap.AUTONOMOUS_SPEED};

            if (contourCenter > RobotMap.SCREEN_CENTER[0] * (1 + RobotMap.ALLOWABLE_ERROR)) {
                movement[RIGHT_INDEX] = (((contourCenter - RobotMap.SCREEN_CENTER[0]) / RobotMap.SCREEN_CENTER[0]) *
                                         RobotMap.AUTONOMOUS_SPEED);
                moveLeft = false;
                moveRight = true;
                centered = false;
            } else if (contourCenter < RobotMap.SCREEN_CENTER[0] * (1 - RobotMap.ALLOWABLE_ERROR)) {
                movement[LEFT_INDEX] = ((contourCenter / RobotMap.SCREEN_CENTER[0]) * RobotMap.AUTONOMOUS_SPEED);
                moveRight = false;
                centered = false;
                moveLeft = true;
            } else {
                moveLeft = false;
                moveRight = false;
                centered = true;
            }

            returnValue.put(RobotMap.SPEED_RIGHT_KEY, movement[RIGHT_INDEX]);
            returnValue.put(RobotMap.SPEED_LEFT_KEY, movement[LEFT_INDEX]);

            if (RobotMap.USE_SPEED_BUFFER)
                bufferSpeeds(returnValue);

        } else {
            returnValue.put(RobotMap.SPEED_RIGHT_KEY, 0.0);
            returnValue.put(RobotMap.SPEED_LEFT_KEY, 0.0);
            contoursFound = false;
        }

        SmartDashboard.putBoolean("Move Left", moveLeft);
        SmartDashboard.putBoolean("Move Right", moveRight);
        SmartDashboard.putBoolean("Centered", centered);
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

        if (rightSpeed != RobotMap.AUTONOMOUS_SPEED || leftSpeed != RobotMap.AUTONOMOUS_SPEED) {

            if (moveLeft)
                rightSpeed *= -1;
            else
                leftSpeed *= -1;

        } else {

            rightSpeed = 0;
            leftSpeed = 0;

        }

        returnValue.replace(RobotMap.SPEED_RIGHT_KEY, rightSpeed);
        returnValue.replace(RobotMap.SPEED_LEFT_KEY, leftSpeed);

        return returnValue;

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
     * @return index (if it is a valid contour), otherwise the next valid contour (if there are valid contours),
     * otherwise {@link RobotMap#ILLEGAL_INT}
     * <p>
     * TODO convert to SmartFilterData format
     */
    private int smartFilterContours() {

        double[] contourXs = contourReport.getNumberArray(RobotMap.CONTOUR_X_KEY, RobotMap.DEFAULT_VALUE);
        double[] contourYs = contourReport.getNumberArray(RobotMap.CONTOUR_Y_KEY, RobotMap.DEFAULT_VALUE);
        double[] contourHeights = contourReport.getNumberArray(RobotMap.CONTOUR_HEIGHT_KEY, RobotMap.DEFAULT_VALUE);
        double[] contourWidths = contourReport.getNumberArray(RobotMap.CONTOUR_WIDTH_KEY, RobotMap.DEFAULT_VALUE);
        double[] contourAreas = new double[contourHeights.length];

        if (!checkEquality(contourXs.length, contourYs.length, contourHeights.length, contourWidths.length))
            return RobotMap.ILLEGAL_INT;

        int arrayLength = contourXs.length;

        for (int i = 0; i < arrayLength; ++i)
            contourAreas[i] = contourHeights[i] * contourWidths[i];

        List<SmartFilterData> validatedSegments = new Vector<>(arrayLength);

        if (RobotMap.USE_RELATIVE_AREA)
            contourAreaRatio(contourAreas, validatedSegments);

        if (RobotMap.USE_RELATIVE_POSITION_CHECK)
            contourRelativePosition(contourXs, contourYs, contourHeights, contourWidths, validatedSegments); //TODO


        return findBestContour(validatedSegments);

    }

    /**
     * Checks to make sure that a contour has another contour with about double/half of it's area.
     * <p>
     * This is used in {@link #smartFilterContours()}
     * </p>
     * Converted to use {@link SmartFilterData} TODO test
     */
    private void contourAreaRatio(double[] contourAreas, List<SmartFilterData> filterData) {

        for (int i = 0; i < contourAreas.length; ++i) { //Loops through the filter values to check

            for (int j = 0; j < contourAreas.length; ++j) { //Loops through the areas to compare to the filter value

                if (i != j) { //Don't check a value against itself

                    if ((contourAreas[i] * 2) * (1 - RobotMap.ALLOWABLE_ERROR) <= contourAreas[j] &&
                        contourAreas[j] <= (contourAreas[i] * 2) * (1 + RobotMap.ALLOWABLE_ERROR)) { //Within error
                        // range

                        filterData.get(i)
                                  .addBestError(SmartFilterData.AREA_RATIO_KEY, contourAreas[i] / contourAreas[j],
                                                RobotMap.HIGH_GOAL_AREA_RATIO); //Adds area if its the low target

                    } else if ((contourAreas[i] / 2) * (1 - RobotMap.ALLOWABLE_ERROR) <= contourAreas[j] &&
                            contourAreas[j] <= (contourAreas[i] / 2) * (1 + RobotMap.ALLOWABLE_ERROR)) {

                        filterData.get(i).addBestError(SmartFilterData.AREA_RATIO_KEY,
                                                       contourAreas[i] / contourAreas[j],
                                                       RobotMap.HIGH_GOAL_TOP_AREA_RATIO); //adds area if its high
                        // target

                    }

                }

            }

        }

    }

    /**
     * Compares the positions of contours to ensure that they are within the desired range of each other as specified
     * by {@link this#contourYOffsetRatio}
     *
     * @param contourXs      X axis positions of contours
     * @param contourYs      Y axis positions of contours
     * @param contourHeights Heights of contours
     * @param contourWidths  Widths of contours
     * TODO convert to SmartFilterData format
     */
    private void contourRelativePosition(double[] contourXs, double[] contourYs, double[] contourHeights,
                                         double[] contourWidths, List<SmartFilterData> filterData) {

//        SmartFilterData[] validatedIndexes = new SmartFilterData[contourXs.length];
//
//        for (int i = 0; i < contourXs.length; ++i) {
//
//            boolean isMatch = false;
//            double XOffset = contourWidths[i] * contourXOffsetRatio;
//            double YOffset = contourHeights[i] * contourYOffsetRatio;
//
//            if (XAxisOffsetCheck) //TODO check if I'm mathing correctly here
//                for (double xCenter : contourXs)
//                    isMatch = (isMatch || contourXs[i] - (contourXs[i] * RobotMap.ALLOWABLE_ERROR) < xCenter &&
//                                          xCenter < contourXs[i] + (contourXs[i] * RobotMap.ALLOWABLE_ERROR));
//
//            if (YAxisOffsetCheck)
//                for (double yCenter : contourYs)
//                    isMatch = (isMatch || contourYs[i] - (contourYs[i] * RobotMap.ALLOWABLE_ERROR) < yCenter &&
//                                          yCenter < contourYs[i] + (contourYs[i] * RobotMap.ALLOWABLE_ERROR));
//
//            validatedIndexes[i] = new SmartFilterData(isMatch, i);
//
//            if (RobotMap.CHECK_FOR_BEST_CONTOUR) {
//                //TODO finish adding error here
//                //validatedIndexes[i].addError(SmartFilterData.RELATIVE_POSITION_X_KEY, contourXs[i]);
//
//            }
//
//        }
//
//        return validatedIndexes;

    }

    /**
     * Determines which contour has the smallest amount of error
     *
     * @param sections A list of every section of {@link #smartFilterContours()}
     * @return Index of the contour with the lowest percent error on everything
     */
    private int findBestContour(List<SmartFilterData> sections) {

        int bestMatch = 0;

        for (int i = 1; i < sections.size(); ++i) //Iterates through data to compare
            bestMatch = sections.get(bestMatch).compareError(sections.get(i));

        return bestMatch;

    }

    ///////////////////////////////////////////////// Speed Buffering //////////////////////////////////////////////////

    /**
     * Buffers speeds to avoid sudden spikes or drops
     * <p>
     * If {@link RobotMap#BUFFER_ACCELERATION} is true, this will also accelerate towards the new value if it is not
     * within the buffer range
     * </p>
     *
     * @param data Data to process with the buffer
     */
    private void bufferSpeeds(Map<String, Double> data) {

        int[] bufferSpeedCheck = speedWithinBuffer(data);

        { //This adds the latest speeds to the buffer and removes the last entry
            double[] latestSpeeds = new double[2];
            latestSpeeds[RobotMap.LEFT_INDEX] = data.get(RobotMap.SPEED_LEFT_KEY);
            latestSpeeds[RIGHT_INDEX] = data.get(RobotMap.SPEED_RIGHT_KEY);
            speedBuffer.add(0, latestSpeeds); //Adds the latest speed to the array
            speedBuffer.remove(RobotMap.SPEED_BUFFER_SIZE - 1); //Removes the last entry
        }

        if (RobotMap.BUFFER_ACCELERATION) {

            double modifiedLeftSpeed = data.get(RobotMap.SPEED_LEFT_KEY),
                    modifiedRightSpeed = data.get(RobotMap.SPEED_RIGHT_KEY);

            modifiedLeftSpeed += bufferSpeedCheck[RobotMap.LEFT_INDEX] * RobotMap.BUFFER_ACCELERATION_AMOUNT;
            modifiedRightSpeed += bufferSpeedCheck[RIGHT_INDEX] * RobotMap.BUFFER_ACCELERATION_AMOUNT;

            data.replace(RobotMap.SPEED_LEFT_KEY, modifiedLeftSpeed);
            data.replace(RobotMap.SPEED_RIGHT_KEY, modifiedRightSpeed);

        } else {

            if (bufferSpeedCheck[RIGHT_INDEX] != 0 || bufferSpeedCheck[RobotMap.LEFT_INDEX] != 0)
                data = lastValidSpeed;

        }

        lastValidSpeed = data;

    }

    /**
     * Checks if the speeds within data are within the constraint set by {@link RobotMap#MAX_BUFFER_DIFFERENCE} when
     * compared to {@link #speedBuffer}
     *
     * @param data Data to check against the buffer
     * @return An array for the right and left speeds. -1 = speed below range, 1 = speed above range, 0 = speed in range
     */
    private int[] speedWithinBuffer(Map<String, Double> data) {

        int goodCount = 0, rightHighCount = 0, leftHighCount = 0, rightLowCount = 0, leftLowCount = 0;

        double dataLeft = data.get(RobotMap.SPEED_LEFT_KEY), dataRight = data.get(RobotMap.SPEED_RIGHT_KEY);

        for (double[] i : speedBuffer) {

            boolean good = false;

            if (greaterThanRange(i[0], dataRight))
                ++rightHighCount;
            else if (lessThanRange(i[0], dataRight))
                ++rightLowCount;
            else
                good = true;

            if (greaterThanRange(i[1], dataLeft))
                ++leftHighCount;
            else if (lessThanRange(i[1], dataLeft))
                ++leftLowCount;
            else if (good)
                ++goodCount;

        }

        int[] returnValue = new int[2]; //TODO there is a better way to do this

        if (goodCount < rightHighCount && rightHighCount > rightLowCount)
            returnValue[RIGHT_INDEX] = 1;
        else if (goodCount < rightLowCount && rightLowCount > rightHighCount)
            returnValue[RIGHT_INDEX] = -1;

        if (goodCount < leftHighCount && leftHighCount > leftLowCount)
            returnValue[RobotMap.LEFT_INDEX] = 1;
        else if (goodCount < leftLowCount && leftLowCount > leftHighCount)
            returnValue[RobotMap.LEFT_INDEX] = -1;

        return returnValue;

    }

    /**
     * Determines if a number is greater than the allowable difference from a buffer value
     *
     * @param bufferValue Buffer value to check from
     * @param dataValue   Value to compare
     * @return If the value is greater than the max allowed by the buffer value
     */
    private boolean greaterThanRange(double bufferValue, double dataValue) {

        return dataValue > bufferValue + RobotMap.MAX_BUFFER_DIFFERENCE;

    }

    /**
     * Determines if a number is less than the allowable difference from a buffer value
     *
     * @param bufferValue Buffer value to check from
     * @param dataValue   Value to compare
     * @return If the value is less than the min allowed by the buffer value
     */
    private boolean lessThanRange(double bufferValue, double dataValue) {

        return dataValue < bufferValue - RobotMap.MAX_BUFFER_DIFFERENCE;

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
        data.put(RobotMap.SMARTFILTER_PASS_KEY, (double) index);

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

    /**
     *
     */
    public void printSmartFilterArrays(List<boolean[]> sections, boolean[] workingArray) {

        System.out.println("Smart Filter data");

        for (boolean[] i : sections) {

            for (boolean j : i)
                System.out.print(((j) ? 1 : 0) + " ");

            System.out.println();

        }

        for (boolean j : workingArray)
            System.out.print(((j) ? 1 : 0) + " ");

        System.out.println();

    }

    ////////////////////////////////////////////////// Other Methods ///////////////////////////////////////////////////

    /**
     * Checks if a bunch of numbers are equal
     *
     * @param numbers the numbers to check
     * @return If the numbers are equal
     */
    private boolean checkEquality(int... numbers) {

        for (int number : numbers) {

            if (number == 0)
                return false;

            for (int x : numbers)
                if (number != x)
                    return false;

        }

        return true;

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

}

/**
 * Class to hold and handle data from {@link NetworkVisionProcessing#smartFilterContours()}.
 * This class exists solely for this purpose. If more methods need to be added, keys for their data also need to be
 * added to this class.
 * <p>
 * Note: It is intended that each contour only has ONE of these to go along with it. This will need to be passed
 * around to all methods that deal with contour data.
 * </p>
 */
class SmartFilterData {

    //////////////////////////////////////////////// Keys for Test Data ////////////////////////////////////////////////
    /**
     * Key for x-axis data from
     * {@link NetworkVisionProcessing#contourRelativePosition(double[], double[], double[], double[])}
     */
    final static String RELATIVE_POSITION_X_KEY = "relativePositionX";
    /**
     * Key for y-axis data from
     * {@link NetworkVisionProcessing#contourRelativePosition(double[], double[], double[], double[])}
     */
    final static String RELATIVE_POSITION_Y_KEY = "relativePositionY";
    /** Key for data from  */
    final static String AREA_RATIO_KEY          = "areaRatio";
    ////////////////////////////////////////////////// Class Variables /////////////////////////////////////////////////
    /** Index of the contour used to determine  */
    private int                 index;
    /** All percent errors from target value for any smart filter methods */
    private Map<String, Double> percentError;

    /**
     * Default constructor
     */
    SmartFilterData() {

        index = ILLEGAL_INT;

    }

    /**
     * Instantiates a SmartFilterData object
     *
     * @param match Whether or not SmartFilter
     */
    SmartFilterData(boolean match, int index) {

        this.index = index;
        percentError = new HashMap<>();

    }

    /**
     * Instantiates a SmartFilterData object
     *
     * @param match Whether or not SmartFilter
     */
    SmartFilterData(boolean match, int index, double error) {

        this.index = index;
        percentError = new HashMap<>();

    }

    /**
     * @return Value of {@link #index}
     */
    public int getIndex() {

        return index;
    }

    /**
     * Adds a new error to {@link #percentError}
     *
     * @param value  Value related to this contour
     * @param target Exact value of that is desired to be reached
     */
    public void addError(String key, double value, double target) {

        percentError.put(key, Math.abs(1 - value / target));

    }

    /**
     * Adds a new error to {@link #percentError} if it is the lowest error for that key
     *
     * @param key    Key associated with the error
     * @param value  Calculated value related to the error
     * @param target Target value to reach
     */
    void addBestError(String key, double value, double target) {

        double error = (Math.abs(1 - value / target));

        if (error < percentError.get(key))
            percentError.replace(key, error);

    }

    /**
     * Compares the error of another SmartFilterData object to this one and attempts to find which one has less error
     *
     * @param otherData Data to compare to this one
     * @return Whichever data has less error
     */
    int compareError(SmartFilterData otherData) {

        int otherDataAdvantage = 0;
        int thisDatasAdvantage = 0;

        for (Map.Entry<String, Double> entry : percentError.entrySet()) {

            if (entry.getValue() < otherData.percentError.get(entry.getKey())) {

                ++thisDatasAdvantage;

            } else if (Objects.equals(entry.getValue(), otherData.percentError.get(entry.getKey()))) {

                ++thisDatasAdvantage;
                ++otherDataAdvantage;

            } else {

                ++otherDataAdvantage;

            }

        }

        return (thisDatasAdvantage >= otherDataAdvantage) ? index : otherData.index;

    }

}

