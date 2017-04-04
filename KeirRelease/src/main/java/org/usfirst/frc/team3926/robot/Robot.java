package org.usfirst.frc.team3926.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.commands.Autonomous.AutoPlaceGear;
import org.usfirst.frc.team3926.robot.commands.Autonomous.AutoShoot;
import org.usfirst.frc.team3926.robot.commands.Autonomous.DoNothing;
import org.usfirst.frc.team3926.robot.commands.Autonomous.DriveForward;
import org.usfirst.frc.team3926.robot.commands.HighGoal.AgitatorIdle;
import org.usfirst.frc.team3926.robot.subsystems.Climber;
import org.usfirst.frc.team3926.robot.subsystems.DriveControl;
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
 *      TODO Add distance modifier for autonomous straight correction distance
 *      TODO Add distance modifier for the distance from the wall
 *      TODO Add shooting speed modifier
 *      TODO Add agitator delay modifier
 *      TODO Add agitator speed modifier
 *      TODO Add climber speed modifier
 *      TODO Add ball collection speed modifier
 *      TODO Add autonomous speed modifier
 *      TODO Add dashboard options for motor types
 **********************************************************************************************************************/
@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class Robot extends IterativeRobot {

    /**
     * Positions that the robot can start in.
     * <p>
     * These are used so that we can correct for autonomous starting positions within the command and we don't
     * need 6 different commands for every action
     * </p>
     */
    public enum StartPositions {
        BlueLeft,
        BlueCenter,
        BlueRight,
        RedLeft,
        RedCenter,
        RedRight
    }

    /** Represents where the robot starts */
    public static StartPositions startPosition;
    public        double         actualAllowableError;

    ////////////////////////////////////////// Instances of Subsystem Classes //////////////////////////////////////////
    /** Instance of DriveControl to allow driving of the robot's base */
    public final static DriveControl driveControl = new DriveControl();
    /** Subsystem to control the robot's shooter */
    //public final static ShooterSubsystem shooter      = new ShooterSubsystem(); No more PID
    public final static SimpleMotor shooter;
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
        shooter = new SimpleMotor<>(RobotMap.SHOOTER_USE_CAN_TALON ? new CANTalon(RobotMap.SHOOTER_CAN_ID) : new
                Talon(RobotMap.SHOOTER_PWM_ID));

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
                                       new Talon(RobotMap.GEAR_PLACEMENT_PWM_PORT)); // This motor is no longer needed

        ///// Agitator Initialization /////
        agitator = new SimpleMotor<>(RobotMap.AGITATOR_USE_CAN_TALON ?
                                     new CANTalon(RobotMap.AGITATOR_CAN_ID) :
                                     new Talon(RobotMap.AGITATOR_PWM_PORT));
        agitator.createDefaultCommand(new AgitatorIdle());

    }

    ///////////////////////////////////////// User Interface and Control ///////////////////////////////////////////////
    /** Operator interface class */
    public static OI                              oi;
    /** Chooser for selecting the autonomous command */
    private       SendableChooser<Command>        autoCommandChooser;
    /** Chooser for selecting where the robot is starting */
    private       SendableChooser<StartPositions> startPositionChooser;
    /** Command to run during autonomous (selected with {@link #autoCommandChooser} */
    private       Command                         autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        ////////// User Interface and Control Initialization //////////
        oi = new OI();
        ///// Auto Command Chooser /////
        autoCommandChooser = new SendableChooser<>();
        autoCommandChooser.addDefault("Do Nothing", new DoNothing());
        autoCommandChooser.addObject("Drive Forward", new DriveForward(RobotMap.AUTONOMOUS_DRIVE_FORWARD_DISTANCE));
        autoCommandChooser.addObject("Place Gear", new AutoPlaceGear());
        autoCommandChooser.addObject("Shoot", new AutoShoot());
        SmartDashboard.putData("Autonomous mode", autoCommandChooser);
        ///// Start Position Chooser /////
        startPositionChooser = new SendableChooser<>();
        startPositionChooser.addDefault("Blue Center", StartPositions.BlueCenter);
        startPositionChooser.addObject("Blue Left", StartPositions.BlueLeft);
        startPositionChooser.addObject("Blue Right", StartPositions.BlueRight);
        startPositionChooser.addObject("Red Center", StartPositions.RedCenter);
        startPositionChooser.addObject("Red Left", StartPositions.RedLeft);
        startPositionChooser.addObject("Red Right", StartPositions.RedRight);
        SmartDashboard.putData("Starting Position", startPositionChooser);
        ///// Vision Init /////
        driveControl.initNetworkTables();

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

    public double getActualAllowableError() {

        return actualAllowableError;
    }

    /**
     * TODO test if constructing things here will fix the instantiation issue
     */
    @Override
    public void disabledPeriodic() {

        Scheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the autoCommandChooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * autoCommandChooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the autoCommandChooser code and uncomment the
     * getString code to get the auto name from the text box below the Gyro
     * <p>
     * You can add additional auto modes by adding additional commands to the
     * autoCommandChooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings and commands.
     */
    @Override
    public void autonomousInit() {

        autonomousCommand = autoCommandChooser.getSelected();
        startPosition = startPositionChooser.getSelected();

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

        gearPlacer.set(0);

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
     * Currently this has the shooter's and the agitator's PID loops
     */
    @Override
    public void testPeriodic() {

        LiveWindow.run();
    }

}
