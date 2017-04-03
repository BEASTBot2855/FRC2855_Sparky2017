// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2855.Sparky.subsystems;

import org.usfirst.frc2855.Sparky.DriveSparky;
import org.usfirst.frc2855.Sparky.Robot;
import org.usfirst.frc2855.Sparky.RobotMap;
import org.usfirst.frc2855.Sparky.commands.*;
import org.usfirst.frc2855.Sparky.SPIGyro.ADXRS453Gyro;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class Drive extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final CANTalon cANTalon1 = RobotMap.driveCANTalon1;
    private final CANTalon cANTalon2 = RobotMap.driveCANTalon2;
    private final CANTalon cANTalon3 = RobotMap.driveCANTalon3;
    private final CANTalon cANTalon4 = RobotMap.driveCANTalon4;
    private final RobotDrive robotDrive41 = RobotMap.driveRobotDrive41;
    private final Ultrasonic ultrasonic1 = RobotMap.driveUltrasonic1;
    private final ADXRS453Gyro gyro = RobotMap.gyro;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new DriveRobot());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
    	
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    public void driveRobot (Double left, Double right) {
    	robotDrive41.tankDrive(left, right);
    }
    
    
    public void stop () {
    	robotDrive41.tankDrive(0.0, 0.0);
    }
    public void calibrateGyro () {
    	gyro.calibrate();
    }
}

