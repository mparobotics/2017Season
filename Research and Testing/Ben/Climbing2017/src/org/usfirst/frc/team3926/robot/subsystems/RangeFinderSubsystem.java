package org.usfirst.frc.team3926.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3926.robot.RobotMap;

/**
 *
 */
public class RangeFinderSubsystem extends Subsystem {

    AnalogInput rangeFinder;
    double rangeFinderScaleFactor;


    public RangeFinderSubsystem(){

        rangeFinder = new AnalogInput(1);


    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public boolean wallLessThenTenMetersAway(){

        return rangeFinder.getValue() * RobotMap.RANGE_FINDER_SENSITIVITY <= 10;

    }

}

