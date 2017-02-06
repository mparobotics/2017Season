package org.usfirst.frc.team3926.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.DriveCommand;

/**
 * drives the robot
 */
public class DriveSystem extends Subsystem {

    private RobotDrive robotDrive;

    /**
     * declares driving motors and tank drive
     */
    public DriveSystem() {

        CANTalon TalonSRX_FR = new CANTalon(RobotMap.FR_MOTOR);
        CANTalon TalonSRX_BR = new CANTalon(RobotMap.BR_MOTOR);
        CANTalon TalonSRX_FL = new CANTalon(RobotMap.FL_MOTOR);
        CANTalon TalonSRX_BL = new CANTalon(RobotMap.BL_MOTOR);

        robotDrive = new RobotDrive(TalonSRX_BL, TalonSRX_BR, TalonSRX_FL, TalonSRX_FR);

    }

    @Override

    /**
     * if it can it will call the drive command
     */
    protected void initDefaultCommand() {

        new DriveCommand();
    }

    /**
     * driving with vision tracking
     */
    public void SetSpeed(double LSpeed, double RSpeed) {

        Robot.visionTrackingSystem.visionDriving();
        robotDrive.tankDrive(LSpeed, RSpeed);

    }

    /** tank drive */
    public void TankDrive(double speedR, double speedL) {

        robotDrive.tankDrive(speedL, speedR);

    }

}
