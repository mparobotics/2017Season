package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class VisionTrackingSystem extends Subsystem {

    private NetworkTable table;
    private double[]     defaultValue;
    private double[]     x;
    private double[]     y;
    private double[]     width;
    private double[]     height;
    private double       adjustmentSpeed;
    private double       yDistanceFromCenter;
    private boolean      turnRight;
    private boolean      turnLeft;
    private double[] returnValue = new double[0];
    private double leftSpeed;
    private double rightSpeed;

    public void initDefaultCommand() {

        table = NetworkTable.getTable(RobotMap.NETWORK_TABLE_KEY);

        defaultValue = new double[0];

    }

    /**
     * sets the speed to x position of contour from vision divided by the center of the screen times the set speed
     * turns towards the target then goes forward
     */
    public double[] visionDriving() {

        Map<String, Double> robotMap = new HashMap<>();

        x = table.getNumberArray(RobotMap.X_KEY, defaultValue);
        y = table.getNumberArray(RobotMap.Y_KEY, defaultValue);
        width = table.getNumberArray(RobotMap.WIDTH_KEY, defaultValue);
        height = table.getNumberArray(RobotMap.HIGHT_KEY, defaultValue);

        adjustmentSpeed = (x[0] / RobotMap.CENTER_OF_SCREEN_X) * RobotMap.VISION_DRIVING_SET_SPEED;

        leftSpeed = (adjustmentSpeed < RobotMap.CENTER_OF_SCREEN_X) ? adjustmentSpeed :
                    RobotMap.VISION_DRIVING_SET_SPEED;

        rightSpeed = (adjustmentSpeed < RobotMap.CENTER_OF_SCREEN_X) ? RobotMap.VISION_DRIVING_SET_SPEED :
                     adjustmentSpeed;

        turnRight = (adjustmentSpeed < RobotMap.CENTER_OF_SCREEN_X) ? false : true;
        turnLeft = (adjustmentSpeed < RobotMap.CENTER_OF_SCREEN_X) ? true : false;

        SmartDashboard.putBoolean("Turn Right: ", turnRight);
        SmartDashboard.putBoolean("Turn Left: ", turnLeft);

        returnValue[0] = leftSpeed;
        returnValue[1] = rightSpeed;

        robotMap.put(RobotMap.X_KEY, x[0]);
        robotMap.put(RobotMap.Y_KEY, y[0]);
        robotMap.put(RobotMap.HIGHT_KEY, height[0]);
        robotMap.put(RobotMap.WIDTH_KEY, width[0]);

        return returnValue;
    }

    /**
     *turns the robot toward the target
     */
    public double[] visionTurning() {

        if (turnRight) {

            leftSpeed *= -1;

        } else {

            rightSpeed *= -1;
        }

        return returnValue;
    }
}


