package org.usfirst.frc2855.Sparky.commands;

import org.usfirst.frc2855.Sparky.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousDriveCommandGroup extends CommandGroup {

    public AutonomousDriveCommandGroup() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	requires(Robot.drive);
    	requires(Robot.gearArmPrototype);
    	
    	addSequential(new AutonomousCommandWithDrive());
    	addSequential(new AutonomousCommandReleaseGear());
    	addSequential(new AutonomousCommandReverseDrive());
    }
}
