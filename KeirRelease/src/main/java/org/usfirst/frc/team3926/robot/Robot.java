package org.usfirst.frc.team3926.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.commands.Autonomous.DoNothing;
import org.usfirst.frc.team3926.robot.commands.Autonomous.DriveForward;
import org.usfirst.frc.team3926.robot.commands.HighGoal.AgitatorIdle;
import org.usfirst.frc.team3926.robot.subsystems.Climber;
import org.usfirst.frc.team3926.robot.subsystems.DriveControl;
import org.usfirst.frc.team3926.robot.subsystems.ShooterSubsystem;
import org.usfirst.frc.team3926.robot.subsystems.SimpleMotor;

/***********************************************************************************************************************
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * @author William Kluge
 *      <p>
 *      Contact: klugewilliam@gmail.com
 *      </p>
 * @author Joe Brooksbank
 *      TODO gear placement command group
 *      TODO collector reverse
 **********************************************************************************************************************/
@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class Robot extends IterativeRobot {

    ////////////////////////////////////////// Instances of Subsystem Classes //////////////////////////////////////////
    /** Instance of DriveControl to allow driving of the robot's base */
    public final static DriveControl     driveControl = new DriveControl();
    /** Subsystem to control the robot's shooter */
    public final static ShooterSubsystem shooter      = new ShooterSubsystem();
    /** Subsystem to control the robot's climbing mechanism */
    public final static Climber     climber;
    /** Subsystem to control the robot's ball collection mechanism */
    public final static SimpleMotor ballCollector;
    /** Subsystem to control the robot's gear placement mechanism */
    public final static SimpleMotor gearPlacer;
    /** Subsystem to control the robot's agitator and prevents balls form getting stuck and feeds the shooter */
    public final static SimpleMotor agitator;

    static { //Static initialization for subsystems

        ///// Shooter Initialization ///

        ///// Climber Initialization /////
        climber = new Climber<>(RobotMap.CLIMBER_USE_CAN_TALON ?
                                new CANTalon[] {new CANTalon(RobotMap.CLIMBER_CAN_ID),
                                                new CANTalon(RobotMap.CLIMBER_SECOND_CAN_ID)} :
                                new Talon[] {new Talon(RobotMap.CLIMBER_PWM_PORT),
                                             new Talon(RobotMap.CLIMBER_SECOND_PWM_PORT)});

        ///// Ball Collector Initialization /////
        ballCollector = new SimpleMotor<>(RobotMap.BALL_COLLECTION_USE_CAN_TALON ?
                                          new CANTalon(RobotMap.BALL_COLLECTION_CAN_ID) :
                                          new Talon(RobotMap.BALL_COLLECTION_PWM_PORT));

        ///// Gear Placer Initialization /////
        gearPlacer = new SimpleMotor<>(RobotMap.GEAR_PLACEMENT_USE_CAN_TALON ?
                                       new CANTalon(RobotMap.GEAR_PLACEMENT_CAN_ID) :
                                       new Talon(RobotMap.GEAR_PLACEMENT_PWM_PORT));

        ///// Agitator Initialization /////
        agitator = new SimpleMotor<>(RobotMap.AGITATOR_USE_CAN_TALON ?
                                     new CANTalon(RobotMap.AGITATOR_CAN_ID) :
                                     new Talon(RobotMap.AGITATOR_PWM_PORT));
        agitator.createDefaultCommand(new AgitatorIdle());

    }

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

        ////////// User Interface and Control Initialization //////////
        oi = new OI();
        chooser = new SendableChooser<>();
        chooser.addDefault("Do Nothing", new DoNothing());
        chooser.addObject("Drive Forward", new DriveForward(RobotMap.AUTONOMOUS_DRIVE_FORWARD_DISTANCE));
        SmartDashboard.putData("Autonomous mode", chooser);

        driveControl.initNetworkTables(RobotMap.TABLE_HIGH_GOAL_NAME);

    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     * "When your code is done..."
     */
    @Override
    public void disabledInit() {

        agitator.set(0);

    }

    /**
     * TODO test if constructing things here will fix the instantiation issue
     */
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
     * to the switch structure below with additional strings and commands.
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

    /**
     * Stops the autonomous command from continuing to run
     */
    @Override
    public void teleopInit() {

        if (autonomousCommand != null)
            autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {

        //TODO remove debugging
        if (oi.driverPrimaryStick.getRawButton(11))
            gearPlacer.set(0.5);
        else
            gearPlacer.set(0);

        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     * Currently this has the shooter's and the agitator's PID loops
     */
    @Override
    public void testPeriodic() {

        LiveWindow.run();
    }

}
