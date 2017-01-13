
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
    private static int FR_CAN = 1;
    private static int FL_CAN = 2;
    private static int BR_CAN = 3;
    private static int BL_CAN = 4;
    private static int ENCODER_PWM = 5;
    private static int RANGE_FINDER_PWM = 6;
    
    CANTalon talonSRX_FR;
    CANTalon talonSRX_FL;
    CANTalon talonSRX_BR;
    CANTalon talonSRX_BL;
    
    RobotDrive driveSystem; 
    
    Encoder enc;
    Ultrasonic RF; 
      
    public void robotInit() {
    	driveSystem = new RobotDrive(talonSRX_FR, talonSRX_FL, talonSRX_BR, talonSRX_BL);
    	
    	enc.setMaxPeriod(.1);
    	enc.setMinRate(10);
    	enc.setDistancePerPulse(5);
    	enc.setReverseDirection(true);
    	enc.setSamplesToAverage(7);
    	
    	RF.setAutomaticMode(true);
    }
   
    public void teleopPeriodic() {
    	int Lspeed = 1;
    	int Rspeed = 1;
    	double enc_distance = enc.getDistance();
    	double RF_in = RF.getRangeInches();
    	
    	if (RF_in >= 10){
    		
    		Lspeed = 0;
    		Rspeed = 0;
    	}
    	
    	driveSystem.tankDrive(Lspeed, Rspeed);
    	
    }
    
}
