
package org.usfirst.frc2855.Sparky.subsystems;

import org.usfirst.frc2855.Sparky.Robot;
import org.usfirst.frc2855.Sparky.RobotMap;
import org.usfirst.frc2855.Sparky.commands.GetPixyData;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    	boolean pin9 = left.get();
    	boolean pin8 = right.get();
    	
    	SmartDashboard.putBoolean("PIN 9", pin9);
    	SmartDashboard.putBoolean("PIN 8", pin8);
    	
    	if (pin9 == true && pin8 == false) {
    		DirectionVal = 1;
    		DriverStation.reportWarning("PIN 9 High", false);
    	} else if (pin8 == true && pin9 == false) {
    		DirectionVal = 2;
    		DriverStation.reportWarning("PIN 8 High", false);
    	} else if (pin9 == true && pin8 == true){
    		DriverStation.reportError("BOTH PINS HIGH", false);
    	} else {
    		DirectionVal = 0;
    		DriverStation.reportWarning("No Pin High", false);
    	}
    	
    }
    
    public static int giveDirection() {
    	return DirectionVal;
    }
}

