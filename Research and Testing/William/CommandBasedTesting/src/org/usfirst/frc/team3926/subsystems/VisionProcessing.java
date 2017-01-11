package org.usfirst.frc.team3926.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.vision.VisionThread;
import org.opencv.core.MatOfPoint;
import org.usfirst.frc.team3926.GripPipelineContour;

import java.util.ArrayList;

/**
 * Class to contain vision processing code
 */
public class VisionProcessing extends Subsystem {

    /** The width to use for the camera */
    private static final int IMG_WIDTH = 320;
    /** The height to use for the camera */
    private static final int IMG_HEIGHT = 240;
    private final Object imgLock = new Object();
    private double[] defaultValue = new double[0];
    //private GripPipeline gripPipeline;
    private NetworkTable lineTable;
    private UsbCamera camera;
    private VisionThread visionThread;
    private ArrayList<MatOfPoint> detectedContours;

    /**
     * Constructs the VisionProcessing task.
     * Starts the thread image processing.
     * @param tableName The name of the NetworkTable to look for
     */
    public VisionProcessing(String tableName) {
        System.out.println("Constructed VisionProcessing");
        camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(IMG_WIDTH, IMG_HEIGHT);

        visionThread = new VisionThread(camera, new GripPipelineContour(),
                pipeline -> {
            if (!pipeline.filterContoursOutput().isEmpty()) {
                ArrayList<MatOfPoint> r = pipeline.filterContoursOutput();
                synchronized (this) {
                    this.detectedContours = r;
                }
            }
        });
        visionThread.start();

        lineTable = NetworkTable.getTable(tableName);
    }

    /**
     *
     */
    public void testThreadingStuff() {
        synchronized (this) {
            if (!detectedContours.isEmpty()) {
                System.out.println("Testing threads: " + detectedContours.get(0).toString());
            }
        }
    }

    /**
     *
     */
    public void FollowTarget() {
        //synchronized (this) {
        if (!detectedContours.isEmpty() && detectedContours.size() <= 2) {
            //follow it m8!
        } else {
            String errorMessage = (detectedContours.isEmpty()) ? "No contours" :
                    "Too many contours";
            System.out.println(errorMessage);
        }
        //}
    }

    /**
     *
     */
    private void DebugLineTable() {
        double[] x1s = lineTable.getNumberArray("x1", defaultValue);
        double[] y1s = lineTable.getNumberArray("y1", defaultValue);
        double[] x2s = lineTable.getNumberArray("x2", defaultValue);
        double[] y2s = lineTable.getNumberArray("y2", defaultValue);
        double[] lengths = lineTable.getNumberArray("length", defaultValue);
        double[] angles  = lineTable.getNumberArray("angle", defaultValue);
        for (int i = 0; i < x1s.length; ++i) {
            System.out.println("Line" + i);
            System.out.println("\tX1: " + x1s[i]);
            System.out.println("\tY1: " + y1s[i]);
            System.out.println("\tX2: " + x2s[i]);
            System.out.println("\tY2: " + y2s[i]);
            System.out.println("\tLength: " + lengths[i]);
            System.out.println("\tAngle: " + angles[i]);
        }
    }

    /**
     * Finds the necessary movement to center the target with the robot
     */
    private void CenterCorrect() {

    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

