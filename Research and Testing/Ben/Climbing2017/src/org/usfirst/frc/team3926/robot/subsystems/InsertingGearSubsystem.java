package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 *
 */
public class InsertingGearSubsystem extends Subsystem {

    private Talon   gearInserter;
    private Encoder gearInsertionEncoder;
    public  int     distanceTraveledByGearInserter;

    public void initDefaultCommand() {

        gearInserter = new Talon(RobotMap.GEAR_INSERTION_MOTOR_ID);
        gearInsertionEncoder = new Encoder(RobotMap.GEAR_INSERTION_ENCODER_PORT_A,
                                           RobotMap.GEAR_INSERTION_ENCODER_PORT_B, false, Encoder.EncodingType.k4X);

    }

    public void insertingGear() {

        gearInserter.set(RobotMap.GEAR_INSERTION_SPEED);
        distanceTraveledByGearInserter = gearInsertionEncoder.get();

    }

    public boolean hasGearBeenInserted() {

        return distanceTraveledByGearInserter >= RobotMap.GEAR_INSERTION_MAX_MOVEMENT;

    }

    public void resetGearInsertionEncoder() {

        gearInsertionEncoder.reset();

    }

}

