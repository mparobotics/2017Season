package org.usfirst.frc.team3926.robot;

import java.beans.Encoder;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
    public static int SHOOTING_MOTOR = 1;
    public static int CLIMBING_MOTOR = 8;
    public static int RIGHT_STICK = 0;
    public static int LEFT_STICK = 1;
    public static int FR_MOTOR = 2;
    public static int FL_MOTOR = 3;
    public static int BR_MOTOR = 4;
    public static int BL_MOTOR = 5;
    public static int A_CHANNEL_ENC = 6;
    public static int B_CHANNEL_ENC = 7;
    public static int DESRIRED_RPM = 200;
    public static double ADDED_AMOUNT = .01;
    public static int CLIMBING_LIMIT_SWITCH = 9;

}
