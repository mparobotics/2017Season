//package org.usfirst.frc.team3926.robot.commands.Debugging;
//
//import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import org.usfirst.frc.team3926.robot.Robot;
//
///**
// *
// */
//public class ShooterTesting extends Command {
//
//    boolean justIncreased = false;
//    boolean justDecreated = false;
//
//    public ShooterTesting() {
//
//        requires(Robot.shooter);
//        Robot.shooter.disable();
//    }
//
//    // Called just before this Command runs the first time
//    protected void initialize() {
//
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//
//        Robot.shooter.shooterMotor.set(Robot.shooter.testSpeed);
//
//        if (Robot.oi.decreaseShooterSpeed.get() && !justDecreated) {
//
//            Robot.shooter.decreaseTestSpeed();
//
//            justDecreated = true;
//
//        } else if (!Robot.oi.decreaseShooterSpeed.get()) {
//
//            justDecreated = false;
//
//        }
//
//        if (Robot.oi.increaseShooterSpeed.get() && !justIncreased) {
//            Robot.shooter.increaseTestSpeed();
//            justIncreased = true;
//        } else if (!Robot.oi.increaseShooterSpeed.get())
//            justIncreased = false;
//
//        SmartDashboard.putNumber("Shooter Testing Speed:", Robot.shooter.testSpeed);
//
//    }
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//
//        return false;
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//
//        Robot.shooter.shooterMotor.set(0);
//
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//
//        Robot.shooter.shooterMotor.set(0);
//
//    }
//
//}
