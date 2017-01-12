
package org.usfirst.frc.team3926.robot;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	private static int TEST_MOTOR_PWM = 1;
	private static int ENC_PWN = 2;
	private static int ANALOG_ACCELEROMETER_PWM = 3;
	private static int RANGE_FINDER_PWM = 4;
	
	Talon testMotor;
	Encoder enc;
	AnalogAccelerometer accel;
	Ultrasonic rangeFinder; 

	
    public void robotInit() {
    	enc.setMaxPeriod(.1);
    	enc.setMinRate(10);
    	enc.setDistancePerPulse(5);
    	enc.setReverseDirection(true);
    	enc.setSamplesToAverage(7);
    	
    	accel.setSensitivity(.018);
    	accel.setZero(2.5);
    	
    	rangeFinder.setAutomaticMode(true);
    }
    
    public void teleopPeriodic() {
    	int speed = 1;
    	testMotor.set(speed);
        double distance_enc = enc.getDistance();
        double acceleration = accel.getAcceleration();
        double distance_RF = rangeFinder.getRangeInches();
        
        if (distance_RF >= 5){
        	testMotor.set(-1*speed);
        	
        }
        else if(distance_enc >= 12){
        	testMotor.set(speed - .15);
        	
        }
        
        if (acceleration == 1){
        	testMotor.set(speed - .01);
        	
        }
    }
}
