package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team3926.robot.commands.AutonomousGearInsertionCommand;
import org.usfirst.frc.team3926.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    /** Instance of the climbSubsystem */
    public static ClimbSubsystem                 climbSubsystem;
    /** Instance of the driveSubsystem */
    public static DriveSubsytem                  driveSubsystem;
    /** Instance of the shootingSubsystem */
    public static ShootingSubsystem              shootingSubsystem;
    /** Instance of the visionTrackingSubsystem */
    public static VisionTrackingSubsystem        visionTrackingSubsystem;
    /** Instance of the PIDSubystem */
    public static PIDSubsystem                   pidSubsystem;
    /** Instance of AutonomousGearInsetionClass */
    public static AutonomousGearInsertionCommand autonomousGearInsertionCommand;
    /** Instance of the OI class */
    public static OI                             oi;

    /**
     * Constructs an instance of io and the subsystems and the autonomousGearInsertionCommand
     */
    public void robotInit() {

        oi = new OI();
        pidSubsystem = new PIDLoopSubsystem();
        visionTrackingSubsystem = new VisionTrackingSubsystem();
        shootingSubsystem = new ShootingSubsystem();
        driveSubsystem = new DriveSubsytem();
        climbSubsystem = new ClimbSubsystem();
        autonomousGearInsertionCommand = new AutonomousGearInsertionCommand();

    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    public void disabledInit() {

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

        autonomousGearInsertionCommand.start();

    }

    public void teleopInit() {

    }

    public void disabledPeriodic() {

        Scheduler.getInstance().run();

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

        Scheduler.getInstance().run();

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
