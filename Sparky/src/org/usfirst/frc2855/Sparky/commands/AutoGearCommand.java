package org.usfirst.frc2855.Sparky.commands;

import org.usfirst.frc2855.Sparky.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoGearCommand extends Command {

	private static int t = 0;
    public AutoGearCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.driveRobot(-0.6, -0.7);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (t >= 450) { // stop at 4 secs
        	return true;
        } else {
        	t += 5;
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
