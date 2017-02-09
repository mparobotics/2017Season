package org.usfirst.frc.team3926.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.commands.Autonomous.DoNothing;
import org.usfirst.frc.team3926.robot.commands.Autonomous.DriveForward;
import org.usfirst.frc.team3926.robot.subsystems.DriveControl;
import org.usfirst.frc.team3926.robot.subsystems.PIDControlledActuator;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    ////////////////////////////////////////// Instances of Subsystem Classes //////////////////////////////////////////
    /** Instance of DriveControl to allow driving of the robot's base */
    public static final DriveControl driveControl = new DriveControl();
    /** TODO test if I'm allowed to put classes here like this */
    public static PIDControlledActuator    shooter;
    /**  */
    ///////////////////////////////////////// User Interface and Control ///////////////////////////////////////////////
    /** Operator interface class */
    public static OI                       oi;
    /** Chooser for selecting the autonomous command */
    private       SendableChooser<Command> chooser;
    /** Command to run during autonomous (selected with {@link #chooser} */
    private       Command                  autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        ////////// Subsystem Initialization //////////
        ///// Shooter Initialization /////
        if (RobotMap.SHOOTER_USE_CAN_TALON)
            shooter = new PIDControlledActuator<CANTalon, Encoder>
                    ("Shooter PID Control", new CANTalon(RobotMap.SHOOTER_CAN_ID),
                     new Encoder(RobotMap.SHOOTER_ENCODER_A_CHANNEL, RobotMap.SHOOTER_ENCODER_B_CHANNEL),
                     PIDSourceType.kRate, RobotMap.SHOOTER_SETPOINT, RobotMap.SHOOTER_PROPORTIONAL,
                     RobotMap.SHOOTER_INTEGRAL, RobotMap.SHOOTER_DERIVATIVE, RobotMap.SHOOTER_ABSOLUTE_TOLERANCE);
        else
            shooter = new PIDControlledActuator<Talon, Encoder>
                    ("Shooter PID Control", new Talon(RobotMap.SHOOTER_CAN_ID),
                     new Encoder(RobotMap.SHOOTER_ENCODER_A_CHANNEL, RobotMap.SHOOTER_ENCODER_B_CHANNEL),
                     PIDSourceType.kRate, RobotMap.SHOOTER_SETPOINT, RobotMap.SHOOTER_PROPORTIONAL,
                     RobotMap.SHOOTER_INTEGRAL, RobotMap.SHOOTER_DERIVATIVE, RobotMap.SHOOTER_ABSOLUTE_TOLERANCE);
        ///// Agitator Initialization /////
        if (RobotMap.AGITATOR_USE_CAN_TALON)


        ////////// User Interface and Control Initialization //////////
        oi = new OI();
        chooser = new SendableChooser<>();
        chooser.addDefault("Do Nothing", new DoNothing());
        chooser.addObject("Drive Forward", new DriveForward());
        SmartDashboard.putData("Autonomous mode", chooser);

    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {

        Scheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString code to get the auto name from the text box below the Gyro
     * <p>
     * You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {

        autonomousCommand = chooser.getSelected();

        // schedule the autonomous command (example)
        if (autonomousCommand != null)
            autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {

        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null)
            autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {

        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {

        LiveWindow.run();
    }
}
