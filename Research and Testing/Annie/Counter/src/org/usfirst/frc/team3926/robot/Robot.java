
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    Counter count;
    double distance;
    double period;
    double rate;
    boolean direction;
    
    public void robotInit() {
    count = new Counter();	
    	
    }
       
    public void teleopPeriodic() {
      
    	distance = count.getDistance();
    	period = count.getPeriod();
    	rate = count.getRate();
    	direction = count.getDirection();
    
    }
}