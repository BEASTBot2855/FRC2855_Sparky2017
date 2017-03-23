
package org.usfirst.frc2855.Sparky.subsystems;

import org.usfirst.frc2855.Sparky.Robot;
import org.usfirst.frc2855.Sparky.RobotMap;
import org.usfirst.frc2855.Sparky.commands.GetPixyData;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class PixyArduino extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private final static DigitalInput left = RobotMap.arduinoInLeft;
	private final static DigitalInput right = RobotMap.arduinoInRight;
	public static int DirectionVal;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new GetPixyData());
    }
    
    public static void getDirection() {
    	if (left.get() == true && right.get() == false) {
    		DirectionVal = 1;
    		DriverStation.reportWarning("PIN 9 High", false);
    	} else if (right.get() == true && left.get() == false) {
    		DirectionVal = 2;
    		DriverStation.reportWarning("PIN 8 High", false);
    	} else {
    		DirectionVal = 0;
    		DriverStation.reportWarning("No Pin High", false);
    	}
    	
    }
    
    public static int giveDirection() {
    	return DirectionVal;
    }
}

