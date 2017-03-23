package org.usfirst.frc2855.Sparky.commands;

import org.usfirst.frc2855.Sparky.Robot;
import org.usfirst.frc2855.Sparky.subsystems.PixyArduino;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GetPixyData extends Command {
	
    public GetPixyData() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	PixyArduino.getDirection();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
