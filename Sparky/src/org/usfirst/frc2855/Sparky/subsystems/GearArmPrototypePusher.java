package org.usfirst.frc2855.Sparky.subsystems;

import org.usfirst.frc2855.Sparky.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearArmPrototypePusher extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private final Solenoid gearPushSolenoid = RobotMap.gearArmPrototypeGearPushSolenoid;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void gearPush() {
    	gearPushSolenoid.set(true);
    	Timer.delay(0.75);
    	gearPushSolenoid.set(false);
    }
    public void gearPushNull() {
    	gearPushSolenoid.set(false);
    }
}

