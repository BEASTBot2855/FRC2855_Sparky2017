package org.usfirst.frc2855.Sparky.subsystems;

import org.usfirst.frc2855.Sparky.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterRun extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private final SpeedController ShooterTalon = RobotMap.shooterTalon;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void runShooter() {
    	ShooterTalon.set(-1.0);
    }
    public void stopShooter() {
    	ShooterTalon.set(0.0);
    }
    public void reverseShooter() {
    	ShooterTalon.set(0.1);
    }
}

