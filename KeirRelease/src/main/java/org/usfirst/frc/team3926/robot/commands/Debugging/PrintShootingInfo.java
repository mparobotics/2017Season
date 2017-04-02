//package org.usfirst.frc.team3926.robot.commands.Debugging;
//
//import edu.wpi.first.wpilibj.command.Command;
//import org.usfirst.frc.team3926.robot.Robot;
//
///**
// *
// *
// *
// *
// */
//public class PrintShootingInfo extends Command {
//
//    public PrintShootingInfo() {
//
//        requires(Robot.shooter);
//    }
//
//    // Called just before this Command runs the first time
//    protected void initialize() {
//
//        System.out.println("Encoder Rate: " + Robot.shooter.encoder.getRate());
//        System.out.println("Rangefinder Voltage: " + Robot.driveControl.rangefinder.getVoltage() + " volts");
//        System.out.println("Contour Area: " + Robot.driveControl.getContourAreas());
//        System.out.println("Speed: " + Robot.shooter.testSpeed);
//
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//
//    }
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//
//        return true;
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//
//    }
//
//}
