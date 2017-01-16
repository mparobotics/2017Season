package org.usfirst.frc.team3926.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.VisionThread;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.usfirst.frc.team3926.Vision.GripPipelineContourII;

import java.util.ArrayList;

/**
 *
 */
public class ContourDetectionSubsystem extends Subsystem {

    /**  */
    private UsbCamera camera;
    /**  */
    private VisionThread visionThread;
    /** */
    private double centerX;

    ContourDetectionSubsystem() {
        camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(320, 240);

        visionThread = new VisionThread(camera, new GripPipelineContourII(), pipeline -> {
            ArrayList<MatOfPoint> r = pipeline.filterContoursOutput();
            if (!r.isEmpty()) {
                Point[] points = r.get(0).toArray();
                synchronized (centerX) {
                    this.centerX =
                }
            }
        }
        );
    }

    private Point average(Point[] points) {

        Point total = new Point();

        for (Point i: points) {
            total.x += i.x;
            total.y += i.y;
        }

        return new Point(total.x/points.length, total.y/points.length);

    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

