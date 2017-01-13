package org.usfirst.frc.team3926.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.VisionThread;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;
import org.usfirst.frc.team3926.GripPipelineContour;
import org.usfirst.frc.team3926.VideoServer;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;
import java.util.ArrayList;
import java.util.List;

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
    /** The video server for sending images to the smart dashboard */
    private VideoServer videoServer;
    /** The mat of the contour image */
    private Mat contourImage;

    /**
     * Constructs the VisionProcessing task.
     * Starts the thread image processing.
     *
     * @param tableName The name of the NetworkTable to look for
     */
    public VisionProcessing(String tableName) {

        System.out.println("Constructed VisionProcessing");
        camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(IMG_WIDTH, IMG_HEIGHT);

        try {
            videoServer = new VideoServer(80);
            videoServer.start();
        } catch (Exception IO) {
            System.out.println("Could not start video server for OpenCV stream");
        }

        visionThread = new VisionThread(camera, new GripPipelineContour(),
                pipeline -> {
                    if (!pipeline.filterContoursOutput().isEmpty()) {
                        ArrayList<MatOfPoint> r = pipeline.filterContoursOutput();
                        synchronized (this) {
                            this.detectedContours = r;
                            for (int i = 0; i < r.size(); ++i)
                                Imgproc.drawContours(this.contourImage, r, i, new Scalar(255, 255, 255), -1);
                        }
                    }
                });
        visionThread.start();

    }

    /**
     * Displays if the robot is detecting any contours through the vision thread
     */
    public void testThreadingStuff() {

        synchronized (this) {

            if (!detectedContours.isEmpty())
                System.out.println("Testing threads: " + detectedContours.get(0).toString());

        }

    }

    /**
     * Moves towards the vision target
     */
    public void followTarget() {
        //synchronized (this) {
        if (!detectedContours.isEmpty() && detectedContours.size() <= 2) {

            double[] offset = contourOffset(0);

            double rightSpeed, leftSpeed;

            if (offset[0] < 0) {
                rightSpeed = RobotMap.AUTONOMOUS_SPEED;
                leftSpeed  = RobotMap.AUTONOMOUS_SPEED * (offset[0] * -1 / SCREEN_CENTER[0]);
            } else if (offset[0] > 0) {
                rightSpeed = RobotMap.AUTONOMOUS_SPEED * (offset[0] / SCREEN_CENTER[0]);
                leftSpeed  = RobotMap.AUTONOMOUS_SPEED;
            } else {
                rightSpeed = RobotMap.AUTONOMOUS_SPEED;
                leftSpeed  = RobotMap.AUTONOMOUS_SPEED;
            }

            Robot.driveControl.setSpeed(rightSpeed, leftSpeed);

        } else {
            String errorMessage = (detectedContours.isEmpty()) ? "No contours" :
                    "Too many contours";
            System.out.println(errorMessage);
        }
        //}
    }

    /**
     * Displays the info about a contour
     */
    public void debugContourImage() {

        if (videoServer.isRunning()) {
            synchronized (this) {
                try {
                    videoServer.sendImage(contourImage);
                } catch (Exception IO) {
                    System.out.println("Could not send contour image to smart dashboard");
                }
            }
        }
    }

    /**
     * Finds the necessary movement to center the target with the robot
     */
    private void centerCorrect() {


    }

    /**
     * Determines which side the robot needs to move towards so that the contour can be in the center
     */
    private void determineSideCorrection() {



    }

    /**
     * Gets the offset of a contour from the center of the camera's image
     * @param index The index of the contour from detectedContours
     */
    private double[] contourOffset(int index) {

        List<Point> test = null;

        Converters.Mat_to_vector_Point(detectedContours.get(index), test);

        double[] contourPoint = {test.get(index).x, test.get(index).y};

        return new double[] {SCREEN_CENTER[0] - contourPoint[0], SCREEN_CENTER[1] - contourPoint[1]};

    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

