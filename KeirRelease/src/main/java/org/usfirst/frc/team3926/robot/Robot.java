package org.usfirst.frc.team3926.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3926.robot.commands.Autonomous.DoNothing;
import org.usfirst.frc.team3926.robot.commands.Autonomous.DriveForward;
import org.usfirst.frc.team3926.robot.commands.HighGoal.AgitatorIdle;
import org.usfirst.frc.team3926.robot.subsystems.BallCollector;
import org.usfirst.frc.team3926.robot.subsystems.Climber;
import org.usfirst.frc.team3926.robot.subsystems.DriveControl;
import org.usfirst.frc.team3926.robot.subsystems.PIDControlledActuator;

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
 ***********************************************************************************************************************/
@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class Robot extends IterativeRobot {

    ////////////////////////////////////////// Instances of Subsystem Classes //////////////////////////////////////////
    /** Instance of DriveControl to allow driving of the robot's base */
    public final static DriveControl driveControl = new DriveControl();
    /** Subsystem to control the robot's shooter */
    public final static PIDControlledActuator shooter;
    /** Subsystem to control the robot's agitator and prevents balls form getting stuck and feeds the shooter */
    public final static PIDControlledActuator agitator;
    /** Subsystem to control the robot's climbing mechanism */
    public final static Climber               climber;
    /** Subsystem to control the robot's */
    public final static BallCollector         ballCollector;

    static { //Static initialization for subsystems

        ///// Shooter Initialization ////
        shooter = new PIDControlledActuator<>
                ("Shooter PID Control", (RobotMap.SHOOTER_USE_CAN_TALON) ? new CANTalon(RobotMap.SHOOTER_CAN_ID) :
                                        new Talon(RobotMap.SHOOTER_CAN_ID),
                 new Encoder(RobotMap.SHOOTER_ENCODER_A_CHANNEL, RobotMap.SHOOTER_ENCODER_B_CHANNEL),
                 PIDSourceType.kRate, RobotMap.SHOOTER_SETPOINT, RobotMap.SHOOTER_PROPORTIONAL,
                 RobotMap.SHOOTER_INTEGRAL, RobotMap.SHOOTER_DERIVATIVE, RobotMap.SHOOTER_ABSOLUTE_TOLERANCE);

        ///// Agitator Initialization /////
        agitator = new PIDControlledActuator<>
                ("Agitator PID Control", (RobotMap.AGITATOR_USE_CAN_TALON) ? new CANTalon(RobotMap.AGITATOR_CAN_ID) :
                                         new Talon(RobotMap.AGITATOR_PWM_PORT),
                 new Encoder(RobotMap.AGITATOR_ENCODER_A_CHANNEL, RobotMap.AGITATOR_ENCODER_B_CHANNEL),
                 PIDSourceType.kRate, RobotMap.AGITATOR_FEED_SETPOINT, RobotMap.AGITATOR_PROPORTIONAL,
                 RobotMap.AGITATOR_INTEGRAL, RobotMap.AGITATOR_DERIVATIVE, RobotMap.AGITATOR_ABSOLUTE_TOLERANCE);
        agitator.createDefaultCommand(new AgitatorIdle());

        ///// Climber Initialization /////
        climber = new Climber<>(new DigitalInput(RobotMap.CLIMBER_LIMIT_SWITCH_PORT),
                                (RobotMap.CLIMBER_USE_CAN_TALON) ?
                                new CANTalon[] {new CANTalon(RobotMap.CLIMBER_CAN_ID),
                                                new CANTalon(RobotMap.CLIMBER_SECOND_CAN_ID)} :
                                new Talon[] {new Talon(RobotMap.CLIMBER_PWM_PORT),
                                             new Talon(RobotMap.CLIMBER_SECOND_PWM_PORT)});

        ///// Ball Collector Initialization /////
        ballCollector = new BallCollector<>(RobotMap.BALL_COLLECTION_USE_CAN_TALON ?
                                            new CANTalon(RobotMap.BALL_COLLECTION_CAN_ID) :
                                            new Talon(RobotMap.BALL_COLLECTION_PWM_PORT));

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
        chooser.addObject("Drive Forward", new DriveForward());
        SmartDashboard.putData("Autonomous mode", chooser);

        try {
            driveControl.initNetworkTables(RobotMap.TABLE_HIGH_GOAL_NAME);
        } catch (Exception e) {
            System.out.print("umm...don't worry about it dude");
        }

        ///// System Preparation /////
        agitator.enable(); //Enables the agitator PID loop

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
