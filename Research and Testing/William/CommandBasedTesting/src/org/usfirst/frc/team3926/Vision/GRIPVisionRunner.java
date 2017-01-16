package org.usfirst.frc.team3926.Vision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * Created by wkluge17 on 1/16/17.
 */
public class GRIPVisionRunner implements Runnable {

    UsbCamera camera;

    /** The width to use for the camera */
    private static final int IMG_WIDTH = 320;
    /** The height to use for the camera */
    private static final int IMG_HEIGHT = 240;
    /** The center of the screen */
    private static final double[] SCREEN_CENTER = {IMG_WIDTH/2, IMG_HEIGHT/2};
    /**  */
    private GripPipelineContourII gripPipelineContourII;

    GRIPVisionRunner() {
        camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
        camera.setFPS(30);
        gripPipelineContourII = new GripPipelineContourII();
    }

    @Override
    public void run() {
        gripPipelineContourII.filterContoursOutput();
    }

}
