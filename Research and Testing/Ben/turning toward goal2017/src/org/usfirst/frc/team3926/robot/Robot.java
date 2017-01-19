
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {

    public void robotInit() {
    
     

    }
    Talon talonFR = new Talon(0);
	Talon talonFL = new Talon(1);
	Talon talonBR = new Talon(2);
	Talon talonBL = new Talon(3);
	AnalogGyro positionSensor = new AnalogGyro(0);
	Encoder distanceTesting = new Encoder(0,1,false, Encoder.EncodingType.k4X);

    public void autonomousInit() {
    	positionSensor.setSensitivity(10);
    	distanceTesting.setDistancePerPulse(5);
    }

   
    public void autonomousPeriodic() {
    	
		double currentInches = distanceTesting.getDistance();
		double currentAngle = positionSensor.getAngle();
		
		if(currentInches < 93.25){
			talonFR.set(1);
			talonFL.set(1);
			talonBR.set(1);
			talonBL.set(1);
		}
		if (currentInches >= 93.25 && currentAngle < 135  ){
			talonFR.set(1);
			talonBR.set(1);
			talonFL.set(-1);
			talonBL.set(-1);
			
		}
    }

  
    public void teleopPeriodic() {
        
    }
    
    public void testPeriodic() {
    
    }
    
}
