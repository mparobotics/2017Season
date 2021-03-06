
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static final ShootingSystem          shootingSystem          = new ShootingSystem();
    public static final DriveSystem             driveSystem             = new DriveSystem();
    public static final ClimbingSystem          climbingSystem          = new ClimbingSystem();
    public static final VisionTrackingSystem    visionTrackingSystem    = new VisionTrackingSystem();
    public static final PIDEncoder              PIDencoder              = new PIDEncoder();
    public static final RangeFinderBackupSystem rangeFinderBackupSystem = new RangeFinderBackupSystem();

    public static OI oi;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

        SmartDashboard.putBoolean("turn right", false);
        SmartDashboard.putBoolean("turn left", false);

    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    public void disabledInit() {

    }

    public void disabledPeriodic() {

        Scheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
     * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
     * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
     * below the Gyro
     * <p>
     * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
     * or additional comparisons to the switch structure below with additional strings & commands.
     */
    public void autonomousInit() {

        boolean right = SmartDashboard.getBoolean("turn right", false);
        boolean left = SmartDashboard.getBoolean("turn left", false);

        Robot.rangeFinderBackupSystem.rightOrLeft(right, left);

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

        Scheduler.getInstance().run();

    }

    public void teleopInit() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

        Scheduler.getInstance().run();

    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {

        LiveWindow.run();

    }
}
