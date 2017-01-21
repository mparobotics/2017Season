package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.Robot;
import org.usfirst.frc.team3926.robot.RobotMap;
import org.usfirst.frc.team3926.robot.commands.DriveCommand;

public class DriveSystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public RobotDrive robotDrive;

    public DriveSystem() {
        CANTalon TalonSRX_FR = new CANTalon(RobotMap.FR_MOTOR);
        CANTalon TalonSRX_BR = new CANTalon(RobotMap.BR_MOTOR);
        CANTalon TalonSRX_FL = new CANTalon(RobotMap.FL_MOTOR);
        CANTalon TalonSRX_BL = new CANTalon(RobotMap.BL_MOTOR);

        robotDrive = new RobotDrive(TalonSRX_BL, TalonSRX_BR, TalonSRX_FL, TalonSRX_FR);

    }

    @Override
    protected void initDefaultCommand() {
        new DriveCommand();
    }

    public void robotSpeed(double LSpeed, double RSpeed) {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());



    }

}