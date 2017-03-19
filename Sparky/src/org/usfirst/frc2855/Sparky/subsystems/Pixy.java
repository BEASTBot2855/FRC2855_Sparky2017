package org.usfirst.frc2855.Sparky.subsystems;

import org.usfirst.frc2855.Sparky.I2CPixy.TPixy;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pixy extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public static short[][] getData() {
    	short[][] b = TPixy.getBlocks();
    	return b;
    }
}

