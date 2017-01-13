
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team3926.commands.ControlTank;
import org.usfirst.frc.team3926.subsystems.DriveControl;
import org.usfirst.frc.team3926.subsystems.TestMotor;
import org.usfirst.frc.team3926.subsystems.VisionProcessing;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final DriveControl driveControl = new DriveControl();
	public static OI oi;
    /** The system to use for controlling the drive base in a tank configuration */
	private ControlTank controlTank;

	private VisionProcessing visionProcessing;

	private TestMotor testMotor;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
		visionProcessing = new VisionProcessing("GRIP/LinesReport");
		testMotor = new TestMotor();
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
        driveControl.reset();
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented
     * example) or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        visionProcessing.followTarget();
    }

    public void teleopInit() {
        controlTank = new ControlTank();
        controlTank.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        visionProcessing.testThreadingStuff();
        testMotor.RunTestMotor();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
        visionProcessing.debugContourImage();;
    }
}
