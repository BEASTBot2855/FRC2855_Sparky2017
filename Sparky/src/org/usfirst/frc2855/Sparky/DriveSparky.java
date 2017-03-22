package org.usfirst.frc2855.Sparky;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.MotorSafetyHelper;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.hal.HAL;

import static java.util.Objects.requireNonNull;

/**
 * Utility class for handling Robot drive based on a definition of the motor configuration. The
 * robot drive class handles basic driving for a robot. Currently, 2 and 4 motor tank and mecanum
 * drive trains are supported. In the future other drive types like swerve might be implemented.
 * Motor channel numbers are supplied on creation of the class. Those are used for either the drive
 * function (intended for hand created drive code, such as autonomous) or with the Tank/Arcade
 * functions intended to be used for Operator Control driving.
 */
public class DriveSparky implements MotorSafety {

  protected static MotorSafetyHelper m_safetyHelper;

  /**
   * The location of a motor on the robot for the purpose of driving.
   */
  public enum MotorType {
    kFrontLeft(0), kFrontRight(1), kRearLeft(2), kRearRight(3);

    @SuppressWarnings("MemberName")
    public final int value;

    private MotorType(int value) {
      this.value = value;
    }
  }

  public static final double kDefaultExpirationTime = 0.1;
  public static final double kDefaultSensitivity = 0.5;
  public static final double kDefaultMaxOutput = 1.0;
  protected static final int kMaxNumberOfMotors = 4;
  protected static double m_sensitivity;
  protected static double m_maxOutput;
  protected static SpeedController m_frontLeftMotor;
  protected static SpeedController m_frontRightMotor;
  protected static SpeedController m_rearLeftMotor;
  protected static SpeedController m_rearRightMotor;
  protected boolean m_allocatedSpeedControllers;
  protected static boolean kArcadeRatioCurve_Reported = false;
  protected static boolean kTank_Reported = false;
  protected static boolean kArcadeStandard_Reported = false;
  protected static boolean kMecanumCartesian_Reported = false;
  protected static boolean kMecanumPolar_Reported = false;

  /**
   * Constructor for RobotDrive with 2 motors specified with channel numbers. Set up parameters for
   * a two wheel drive system where the left and right motor pwm channels are specified in the call.
   * This call assumes Talons for controlling the motors.
   *
   * @param leftMotorChannel  The PWM channel number that drives the left motor.
   * @param rightMotorChannel The PWM channel number that drives the right motor.
 * @return 
   */
  public void RobotDrive(final int leftMotorChannel, final int rightMotorChannel) {
    m_sensitivity = kDefaultSensitivity;
    m_maxOutput = kDefaultMaxOutput;
    m_frontLeftMotor = null;
    m_rearLeftMotor = new Talon(leftMotorChannel);
    m_frontRightMotor = null;
    m_rearRightMotor = new Talon(rightMotorChannel);
    m_allocatedSpeedControllers = true;
    setupMotorSafety();
    drive(0, 0);
  }

  /**
   * Constructor for RobotDrive with 4 motors specified with channel numbers. Set up parameters for
   * a four wheel drive system where all four motor pwm channels are specified in the call. This
   * call assumes Talons for controlling the motors.
   *
   * @param frontLeftMotor  Front left motor channel number
   * @param rearLeftMotor   Rear Left motor channel number
   * @param frontRightMotor Front right motor channel number
   * @param rearRightMotor  Rear Right motor channel number
 * @return 
   */
  public void RobotDrive(final int frontLeftMotor, final int rearLeftMotor, final int frontRightMotor,
                    final int rearRightMotor) {
    m_sensitivity = kDefaultSensitivity;
    m_maxOutput = kDefaultMaxOutput;
    m_rearLeftMotor = new Talon(rearLeftMotor);
    m_rearRightMotor = new Talon(rearRightMotor);
    m_frontLeftMotor = new Talon(frontLeftMotor);
    m_frontRightMotor = new Talon(frontRightMotor);
    m_allocatedSpeedControllers = true;
    setupMotorSafety();
    drive(0, 0);
  }

  /**
   * Constructor for RobotDrive with 2 motors specified as SpeedController objects. The
   * SpeedController version of the constructor enables programs to use the RobotDrive classes with
   * subclasses of the SpeedController objects, for example, versions with ramping or reshaping of
   * the curve to suit motor bias or dead-band elimination.
   *
   * @param leftMotor  The left SpeedController object used to drive the robot.
   * @param rightMotor the right SpeedController object used to drive the robot.
 * @return 
   */
  public void RobotDrive(SpeedController leftMotor, SpeedController rightMotor) {
    if (leftMotor == null || rightMotor == null) {
      m_rearLeftMotor = m_rearRightMotor = null;
      throw new NullPointerException("Null motor provided");
    }
    m_frontLeftMotor = null;
    m_rearLeftMotor = leftMotor;
    m_frontRightMotor = null;
    m_rearRightMotor = rightMotor;
    m_sensitivity = kDefaultSensitivity;
    m_maxOutput = kDefaultMaxOutput;
    m_allocatedSpeedControllers = false;
    setupMotorSafety();
    drive(0, 0);
  }

  /**
   * Constructor for RobotDrive with 4 motors specified as SpeedController objects. Speed controller
   * input version of RobotDrive (see previous comments).
   *
   * @param frontLeftMotor  The front left SpeedController object used to drive the robot
   * @param rearLeftMotor   The back left SpeedController object used to drive the robot.
   * @param frontRightMotor The front right SpeedController object used to drive the robot.
   * @param rearRightMotor  The back right SpeedController object used to drive the robot.
 * @return 
   */
  public void RobotDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor,
                    SpeedController frontRightMotor, SpeedController rearRightMotor) {
    m_frontLeftMotor = requireNonNull(frontLeftMotor, "frontLeftMotor cannot be null");
    m_rearLeftMotor = requireNonNull(rearLeftMotor, "rearLeftMotor cannot be null");
    m_frontRightMotor = requireNonNull(frontRightMotor, "frontRightMotor cannot be null");
    m_rearRightMotor = requireNonNull(rearRightMotor, "rearRightMotor cannot be null");
    m_sensitivity = kDefaultSensitivity;
    m_maxOutput = kDefaultMaxOutput;
    m_allocatedSpeedControllers = false;
    setupMotorSafety();
    drive(0, 0);
  }

  /**
   * Drive the motors at "outputMagnitude" and "curve". Both outputMagnitude and curve are -1.0 to
   * +1.0 values, where 0.0 represents stopped and not turning. {@literal curve < 0 will turn left
   * and curve > 0} will turn right.
   *
   * <p>The algorithm for steering provides a constant turn radius for any normal speed range, both
   * forward and backward. Increasing sensitivity causes sharper turns for fixed values of curve.
   *
   * <p>This function will most likely be used in an autonomous routine.
   *
   * @param outputMagnitude The speed setting for the outside wheel in a turn, forward or backwards,
   *                        +1 to -1.
   * @param curve           The rate of turn, constant for different forward speeds. Set {@literal
   *                        curve < 0 for left turn or curve > 0 for right turn.} Set curve =
   *                        e^(-r/w) to get a turn radius r for wheelbase w of your robot.
   *                        Conversely, turn radius r = -ln(curve)*w for a given value of curve and
   *                        wheelbase w.
   */
  public void drive(double outputMagnitude, double curve) {
    final double leftOutput;
    final double rightOutput;

    if (!kArcadeRatioCurve_Reported) {
      HAL.report(tResourceType.kResourceType_RobotDrive, getNumMotors(),
          tInstances.kRobotDrive_ArcadeRatioCurve);
      kArcadeRatioCurve_Reported = true;
    }
    if (curve < 0) {
      double value = Math.log(-curve);
      double ratio = (value - m_sensitivity) / (value + m_sensitivity);
      if (ratio == 0) {
        ratio = .0000000001;
      }
      leftOutput = outputMagnitude / ratio;
      rightOutput = outputMagnitude;
    } else if (curve > 0) {
      double value = Math.log(curve);
      double ratio = (value - m_sensitivity) / (value + m_sensitivity);
      if (ratio == 0) {
        ratio = .0000000001;
      }
      leftOutput = outputMagnitude;
      rightOutput = outputMagnitude / ratio;
    } else {
      leftOutput = outputMagnitude;
      rightOutput = outputMagnitude;
    }
    setLeftRightMotorOutputs(leftOutput, rightOutput);
  }

  /**
   * Provide tank steering using the stored robot configuration. drive the robot using two joystick
   * inputs. The Y-axis will be selected from each Joystick object.
   *
   * @param leftStick  The joystick to control the left side of the robot.
   * @param rightStick The joystick to control the right side of the robot.
   */
  public void tankDrive(GenericHID leftStick, GenericHID rightStick) {
    if (leftStick == null || rightStick == null) {
      throw new NullPointerException("Null HID provided");
    }
    tankDrive(leftStick.getY(), rightStick.getY(), true);
  }

  /**
   * Provide tank steering using the stored robot configuration. drive the robot using two joystick
   * inputs. The Y-axis will be selected from each Joystick object.
   *
   * @param leftStick     The joystick to control the left side of the robot.
   * @param rightStick    The joystick to control the right side of the robot.
   * @param squaredInputs Setting this parameter to true decreases the sensitivity at lower speeds
   */
  public void tankDrive(GenericHID leftStick, GenericHID rightStick, boolean squaredInputs) {
    if (leftStick == null || rightStick == null) {
      throw new NullPointerException("Null HID provided");
    }
    tankDrive(leftStick.getY(), rightStick.getY(), squaredInputs);
  }

  /**
   * Provide tank steering using the stored robot configuration. This function lets you pick the
   * axis to be used on each Joystick object for the left and right sides of the robot.
   *
   * @param leftStick  The Joystick object to use for the left side of the robot.
   * @param leftAxis   The axis to select on the left side Joystick object.
   * @param rightStick The Joystick object to use for the right side of the robot.
   * @param rightAxis  The axis to select on the right side Joystick object.
   */
  public void tankDrive(GenericHID leftStick, final int leftAxis, GenericHID rightStick,
                        final int rightAxis) {
    if (leftStick == null || rightStick == null) {
      throw new NullPointerException("Null HID provided");
    }
    tankDrive(leftStick.getRawAxis(leftAxis), rightStick.getRawAxis(rightAxis), true);
  }

  /**
   * Provide tank steering using the stored robot configuration. This function lets you pick the
   * axis to be used on each Joystick object for the left and right sides of the robot.
   *
   * @param leftStick     The Joystick object to use for the left side of the robot.
   * @param leftAxis      The axis to select on the left side Joystick object.
   * @param rightStick    The Joystick object to use for the right side of the robot.
   * @param rightAxis     The axis to select on the right side Joystick object.
   * @param squaredInputs Setting this parameter to true decreases the sensitivity at lower speeds
   */
  public void tankDrive(GenericHID leftStick, final int leftAxis, GenericHID rightStick,
                        final int rightAxis, boolean squaredInputs) {
    if (leftStick == null || rightStick == null) {
      throw new NullPointerException("Null HID provided");
    }
    tankDrive(leftStick.getRawAxis(leftAxis), rightStick.getRawAxis(rightAxis), squaredInputs);
  }

  /**
   * Provide tank steering using the stored robot configuration. This function lets you directly
   * provide joystick values from any source.
   *
   * @param leftValue     The value of the left stick.
   * @param rightValue    The value of the right stick.
   * @param squaredInputs Setting this parameter to true decreases the sensitivity at lower speeds
   */
  public static void tankDrive(double leftValue, double rightValue, boolean squaredInputs) {

    if (!kTank_Reported) {
      HAL.report(tResourceType.kResourceType_RobotDrive, getNumMotors(),
          tInstances.kRobotDrive_Tank);
      kTank_Reported = true;
    }

    // square the inputs (while preserving the sign) to increase fine control
    // while permitting full power
    leftValue = limit(leftValue);
    rightValue = limit(rightValue);
    if (squaredInputs) {
      if (leftValue >= 0.0) {
        leftValue = leftValue * leftValue;
      } else {
        leftValue = -(leftValue * leftValue);
      }
      if (rightValue >= 0.0) {
        rightValue = rightValue * rightValue;
      } else {
        rightValue = -(rightValue * rightValue);
      }
    }
    setLeftRightMotorOutputs(leftValue, rightValue);
  }

  /**
   * Provide tank steering using the stored robot configuration. This function lets you directly
   * provide joystick values from any source.
   *
   * @param leftValue  The value of the left stick.
   * @param rightValue The value of the right stick.
   */
  public static void tankDrive(double leftValue, double rightValue) {
    tankDrive(leftValue, rightValue, true);
  }

  /**
   * Set the speed of the right and left motors. This is used once an appropriate drive setup
   * function is called such as twoWheelDrive(). The motors are set to "leftSpeed" and
   * "rightSpeed" and includes flipping the direction of one side for opposing motors.
   *
   * @param leftOutput  The speed to send to the left side of the robot.
   * @param rightOutput The speed to send to the right side of the robot.
   */
  public static void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
    if (m_rearLeftMotor == null || m_rearRightMotor == null) {
      throw new NullPointerException("Null motor provided");
    }

    if (m_frontLeftMotor != null) {
      m_frontLeftMotor.set(limit(leftOutput) * m_maxOutput);
    }
    m_rearLeftMotor.set(limit(leftOutput) * m_maxOutput);

    if (m_frontRightMotor != null) {
      m_frontRightMotor.set(-limit(rightOutput) * m_maxOutput);
    }
    m_rearRightMotor.set(-limit(rightOutput) * m_maxOutput);

    if (m_safetyHelper != null) {
      m_safetyHelper.feed();
    }
  }

  /**
   * Limit motor values to the -1.0 to +1.0 range.
   */
  protected static double limit(double num) {
    if (num > 1.0) {
      return 1.0;
    }
    if (num < -1.0) {
      return -1.0;
    }
    return num;
  }

  /**
   * Normalize all wheel speeds if the magnitude of any wheel is greater than 1.0.
   */
  protected static void normalize(double[] wheelSpeeds) {
    double maxMagnitude = Math.abs(wheelSpeeds[0]);
    for (int i = 1; i < kMaxNumberOfMotors; i++) {
      double temp = Math.abs(wheelSpeeds[i]);
      if (maxMagnitude < temp) {
        maxMagnitude = temp;
      }
    }
    if (maxMagnitude > 1.0) {
      for (int i = 0; i < kMaxNumberOfMotors; i++) {
        wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
      }
    }
  }

  /**
   * Rotate a vector in Cartesian space.
   */
  @SuppressWarnings("ParameterName")
  protected static double[] rotateVector(double x, double y, double angle) {
    double cosA = Math.cos(angle * (3.14159 / 180.0));
    double sinA = Math.sin(angle * (3.14159 / 180.0));
    double[] out = new double[2];
    out[0] = x * cosA - y * sinA;
    out[1] = x * sinA + y * cosA;
    return out;
  }

  /**
   * Invert a motor direction. This is used when a motor should run in the opposite direction as the
   * drive code would normally run it. Motors that are direct drive would be inverted, the drive
   * code assumes that the motors are geared with one reversal.
   *
   * @param motor      The motor index to invert.
   * @param isInverted True if the motor should be inverted when operated.
   */
  public void setInvertedMotor(MotorType motor, boolean isInverted) {
    switch (motor) {
      case kFrontLeft:
        m_frontLeftMotor.setInverted(isInverted);
        break;
      case kFrontRight:
        m_frontRightMotor.setInverted(isInverted);
        break;
      case kRearLeft:
        m_rearLeftMotor.setInverted(isInverted);
        break;
      case kRearRight:
        m_rearRightMotor.setInverted(isInverted);
        break;
      default:
        throw new IllegalArgumentException("Illegal motor type: " + motor);
    }
  }

  /**
   * Set the turning sensitivity.
   *
   * <p>This only impacts the drive() entry-point.
   *
   * @param sensitivity Effectively sets the turning sensitivity (or turn radius for a given value)
   */
  public void setSensitivity(double sensitivity) {
    m_sensitivity = sensitivity;
  }

  /**
   * Configure the scaling factor for using RobotDrive with motor controllers in a mode other than
   * PercentVbus.
   *
   * @param maxOutput Multiplied with the output percentage computed by the drive functions.
   */
  public void setMaxOutput(double maxOutput) {
    m_maxOutput = maxOutput;
  }

  /**
   * Free the speed controllers if they were allocated locally.
   */
  public void free() {
    if (m_allocatedSpeedControllers) {
      if (m_frontLeftMotor != null) {
        ((PWM) m_frontLeftMotor).free();
      }
      if (m_frontRightMotor != null) {
        ((PWM) m_frontRightMotor).free();
      }
      if (m_rearLeftMotor != null) {
        ((PWM) m_rearLeftMotor).free();
      }
      if (m_rearRightMotor != null) {
        ((PWM) m_rearRightMotor).free();
      }
    }
  }

  @Override
  public void setExpiration(double timeout) {
    m_safetyHelper.setExpiration(timeout);
  }

  @Override
  public double getExpiration() {
    return m_safetyHelper.getExpiration();
  }

  @Override
  public boolean isAlive() {
    return m_safetyHelper.isAlive();
  }

  @Override
  public boolean isSafetyEnabled() {
    return m_safetyHelper.isSafetyEnabled();
  }

  @Override
  public void setSafetyEnabled(boolean enabled) {
    m_safetyHelper.setSafetyEnabled(enabled);
  }

  @Override
  public String getDescription() {
    return "Robot Drive";
  }

  @Override
  public void stopMotor() {
    if (m_frontLeftMotor != null) {
      m_frontLeftMotor.stopMotor();
    }
    if (m_frontRightMotor != null) {
      m_frontRightMotor.stopMotor();
    }
    if (m_rearLeftMotor != null) {
      m_rearLeftMotor.stopMotor();
    }
    if (m_rearRightMotor != null) {
      m_rearRightMotor.stopMotor();
    }
    if (m_safetyHelper != null) {
      m_safetyHelper.feed();
    }
  }

  private void setupMotorSafety() {
    m_safetyHelper = new MotorSafetyHelper(this);
    m_safetyHelper.setExpiration(kDefaultExpirationTime);
    m_safetyHelper.setSafetyEnabled(true);
  }

  protected static int getNumMotors() {
    int motors = 0;
    if (m_frontLeftMotor != null) {
      motors++;
    }
    if (m_frontRightMotor != null) {
      motors++;
    }
    if (m_rearLeftMotor != null) {
      motors++;
    }
    if (m_rearRightMotor != null) {
      motors++;
    }
    return motors;
  }
}
