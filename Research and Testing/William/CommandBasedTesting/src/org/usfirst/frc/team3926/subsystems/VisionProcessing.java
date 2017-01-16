package org.usfirst.frc.team3926.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.usfirst.frc.team3926.Vision.GripPipelineContourII;
import org.usfirst.frc.team3926.robot.RobotMap;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.opencv.imgproc.Imgproc.boundingRect;

/**
 * Class to contain vision processing code
 */
public class VisionProcessing extends Subsystem {

    /** The width to use for the camera */
    private static final int IMG_WIDTH = 320;
    /** The height to use for the camera */
    private static final int IMG_HEIGHT = 240;
    /** The center of the screen */
    private static final double[] SCREEN_CENTER = {IMG_WIDTH/2, IMG_HEIGHT/2};
    /** The camera to use for vision */
    private UsbCamera camera;
    /** The thread to handle vision processing */
    private VisionThread visionThread;
    /** The contours detected by vision processing */
    private ArrayList<MatOfPoint> detectedContours;
    /** The speed last set to the robot, helps to make sure if we lose detection for a bit it don't stop instantly */
    private double lastRightSpeed, lastLeftSpeed;
    /** The amount of time (in milliseconds) that the robot has been in its current state of detection */
    private double detectionStateTime;
    /** The time that detection started */
    private LocalDateTime startDetection;
    /**  */
    private long frameCount;
    /**  */
    private boolean moveLeft, moveRight, contoursDetected = false;
    /**  */
    private final Object imageLock = new Object();
    /**  */
    double[] contourOffset;

    /**
     * Constructs the VisionProcessing task.
     * Starts the thread image processing.
     *
     */
    public VisionProcessing() {

        camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
        camera.setFPS(30);

        detectedContours = new ArrayList<>();

        visionThread = new VisionThread(camera, new GripPipelineContourII(),
                pipeline -> {
                    if (!pipeline.filterContoursOutput().isEmpty()) {
                        ArrayList<MatOfPoint> r = pipeline.filterContoursOutput();
                        synchronized (imageLock) {
                            this.contourOffset = contourOffset(r, 0);
                            if (this.contourOffset.length == 0)
                                this.contourOffset = new double[] {0, 0};
                        }
                    }
                });
        visionThread.start();

        lastRightSpeed = 0;
        lastLeftSpeed  = 0;
        detectionStateTime = 0;

        startDetection = LocalDateTime.now();
        frameCount = 0;

    }

    /**
     * Moves towards the vision target
     */
    public void followTarget() {

        //++frameCount;

        //SmartDashboard.putNumber("FPS: ", frameCount/(LocalDateTime.now()-startDetection.))

//        synchronized (imageLock) {
//            this.detectedContours =
//        }

        double[] offset;

        synchronized (imageLock) {
            offset = this.contourOffset;
        }

        if (offset.length > 0) {

            System.out.println("Moving towards contour...");

            contoursDetected = true;

            //double[] offset = contourOffset(contours, 0);

            double rightSpeed, leftSpeed;

            if (offset[0] < 0) {
                rightSpeed = RobotMap.AUTONOMOUS_SPEED;
                leftSpeed  = RobotMap.AUTONOMOUS_SPEED * (offset[0] * -1 / SCREEN_CENTER[0]);
                moveRight = true;
                moveLeft = false;
            } else if (offset[0] > 0) {
                rightSpeed = RobotMap.AUTONOMOUS_SPEED * (offset[0] / SCREEN_CENTER[0]);
                leftSpeed  = RobotMap.AUTONOMOUS_SPEED;
                moveLeft = true;
                moveRight = false;
            } else {
                rightSpeed = RobotMap.AUTONOMOUS_SPEED;
                leftSpeed  = RobotMap.AUTONOMOUS_SPEED;
                moveLeft = false;
                moveRight = false;
            }

            //Robot.driveControl.autonomousTank(rightSpeed, leftSpeed);

            SmartDashboard.putBoolean("Move Right", moveRight);
            SmartDashboard.putBoolean("Move Left", moveLeft);

            System.out.println("\tSetting speed to right: " + rightSpeed + " left: " + leftSpeed);

        } else {
            String errorMessage = (offset.length == 0) ? "No contours detected" :
                    "Too many contours";
            System.out.println(errorMessage);

            contoursDetected = false;
        }

        SmartDashboard.putBoolean("Contours Detected", contoursDetected);

    }

    /**
     * Gets the offset of a contour from the center of the camera's image
     * @param index The index of the contour from detectedContours
     */
    private double[] contourOffset(ArrayList<MatOfPoint> contours, int index) {

        System.out.println("\tDetecting offset in contour");

        try {

            Point[] specificContour = contours.get(index).toArray();
            printContourDebug(index);

            double[] averagePointPosition = new double[2];

            for (Point aSpecificContour : specificContour) {
                averagePointPosition[0] += aSpecificContour.x;
                averagePointPosition[1] += aSpecificContour.y;
            }

            averagePointPosition[0] /= specificContour.length;
            averagePointPosition[1] /= specificContour.length;

            System.out.println("\t\tAverage X: " + averagePointPosition[0] + " Average Y: " + averagePointPosition[1]);

            return new double[] {SCREEN_CENTER[0] - averagePointPosition[0], SCREEN_CENTER[1] - averagePointPosition[1]};

        } catch (Exception e) {
            System.out.println("Error in finding contour offset");
            System.err.println("\t" + e.toString());
        }

        return new double[] {0, 0};

    }

    /**
     * Prints the debug info of a detected contour to the console
     * @param index The index of the contour to print data for
     */
    private void printContourDebug(int index) {

        System.out.println("\t\tPrinting contour data");

        System.out.println("\t\t\tPoints:");

        for (Point contourPoint : detectedContours.get(index).toArray())
            System.out.println("\t\t\t\tX: " + contourPoint.x + " Y: " + contourPoint.y);

        System.out.println("\t\t\tArea: " + boundingRect(detectedContours.get(index)).area());

    }

    /**
     * Determines if the current state of detection is correct
     * @return
     * TODO finish
     */
    private boolean goodDetection() {
        return true;
    }

    /**
     * Set the default command for a subsystem here.
     */
    public void initDefaultCommand() {

    }

}

