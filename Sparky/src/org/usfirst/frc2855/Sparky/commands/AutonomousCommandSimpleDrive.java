package org.usfirst.frc2855.Sparky.commands;

import org.usfirst.frc2855.Sparky.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonomousCommandSimpleDrive extends Command {

	private static int a;
    public AutonomousCommandSimpleDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	a = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.driveRobot(-0.75, -0.75);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (a >= 75) {
        	return true;
        } else {
        	a++;
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
