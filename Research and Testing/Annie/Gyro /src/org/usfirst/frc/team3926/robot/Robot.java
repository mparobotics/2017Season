
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

    AnalogGyro gyro;
    double angle;
    double offSet;
    double rate;

    public void robotInit() {

        gyro = new AnalogGyro(1);

    }

    public void teleopPeriodic() {

        angle = gyro.getAngle();
        offSet = gyro.getOffset();
        rate = gyro.getRate();

        SmartDashboard.putNumber("angle", angle);
        SmartDashboard.putNumber("off set", offSet);
        SmartDashboard.putNumber("rate", rate);

    }
}    
    
