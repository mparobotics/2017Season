
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team3926.commands.ControlTank;
import org.usfirst.frc.team3926.commands.DriveToVisionTarget;
import org.usfirst.frc.team3926.subsystems.DriveControl;
import org.usfirst.frc.team3926.subsystems.NetworkVisionProcessing;
import org.usfirst.frc.team3926.subsystems.TestMotor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    /** Instance of DriveControl to use for control of the robot chassis */
    public static final DriveControl            driveControl     = new DriveControl();
    /** Instance of NetworkVisionProcessing to use for autonomous actions */
    public static final NetworkVisionProcessing visionProcessing = new NetworkVisionProcessing();
    /** Instance of OI to use for operator control */
    public static  OI                  oi;
    /* These are here temporatily for debugging */
    private        ControlTank         controlTank;
    private        TestMotor           testMotor;
    private static DriveToVisionTarget driveToVisionTarget;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

        oi = new OI();
        testMotor = new TestMotor();
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    public void disabledInit() {

        driveControl.reset();
    }

    /**
     *
     */
    public void disabledPeriodic() {

        Scheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
     * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
     * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
     * below the Gyro
     * <p>
     * You can add additional auto modes by adding additional commands to the chooser code above (like the commented
     * example) or additional comparisons to the switch structure below with additional strings & commands.
     * </p>
     */
    public void autonomousInit() {

        driveToVisionTarget = new DriveToVisionTarget();
        driveToVisionTarget.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

        Scheduler.getInstance().run();
    }

    /**
     * Called at the start of tele-oporation
     */
    public void teleopInit() {

        controlTank = new ControlTank();
        controlTank.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

        Scheduler.getInstance().run();
        testMotor.RunTestMotor();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {

        LiveWindow.run();
    }

}
