package org.usfirst.frc.team3926.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.RobotMap;

import java.util.*;

import static org.usfirst.frc.team3926.robot.RobotMap.LEFT_INDEX;
import static org.usfirst.frc.team3926.robot.RobotMap.RIGHT_INDEX;

/**
 * TODO make a new class for this. I don't like the way this is working
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
 * (1/26/2017:4:05PM) I created the (removed) function so that we can use a less strict contour
 * finding algorithm and filter for contours that are close together. This should let us detect in many different
 * different situations with (basically) guaranteed same results
 * </p>
 * <p>
 * (2/7/2017:4:48 PM) I moved everything relating to the SmartFilter outside of this class. It will be a lot cleaner
 * that way. Now, this class only relates to driving the robot autonomously, and
 * {@link org.usfirst.frc.team3926.UserClasses.SmartFilter} decides which contour to drive based off of.
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
    private boolean      contoursFound, moveRight, moveLeft, centered;
    /** Keeps track of the last few speeds to ensure returned values aren't spikes due to temporary interruptions */
    private List<double[]>      speedBuffer;
    /** Last valid speed from the buffer */
    private Map<String, Double> lastValidSpeed;

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
        xOffsetRatio = xOffsetRatio;
        yOffsetRatio = yOffsetRatio;
    }

    ///////////////////////////////////////////////// Moving the Robot /////////////////////////////////////////////////

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
            checkIndex = smartFilterContours();
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
        SmartDashboard.putNumber("Driving Based on Contour: ", checkIndex);

        return returnValue;

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

    ////////////////////////////////////////////////// Debugging Data //////////////////////////////////////////////////

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

    ////////////////////////////////////////////////// Other Methods ///////////////////////////////////////////////////

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

}

