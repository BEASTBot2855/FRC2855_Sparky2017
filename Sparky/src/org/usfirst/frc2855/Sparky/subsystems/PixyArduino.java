
package org.usfirst.frc2855.Sparky.subsystems;

import org.usfirst.frc2855.Sparky.Robot;
import org.usfirst.frc2855.Sparky.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class PixyArduino extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private final static DigitalInput left = RobotMap.arduinoInLeft;
	private final static DigitalInput right = RobotMap.arduinoInRight;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public static int getDirection() {
    	if (left.get() == true && right.get() == false) {
    		return 1;
    	} else if (right.get() == true && left.get() == false) {
    		return 2;
    	} else {
    		return 0;
    	}
    }
}

