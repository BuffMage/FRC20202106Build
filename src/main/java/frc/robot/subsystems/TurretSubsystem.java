package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CannonPIDConstants;
import frc.robot.Constants.SystemConstants;

public class TurretSubsystem extends SubsystemBase {

  private static TalonSRX turretMotor;
  private static TurretSubsystem turretSubsystem = null;
  private static TalonSRX shooterMaster;
  private static TalonSRX shooterFollower;
  private int pidCounter;

  public static TurretSubsystem getInstance()
  {
    if (turretSubsystem == null)
    {
      turretSubsystem = new TurretSubsystem();
    }
    return turretSubsystem;
  }

  private TurretSubsystem() {

    turretMotor = new TalonSRX(SystemConstants.kTurretMotorID);
    shooterMaster = new TalonSRX(SystemConstants.kShooterMasterID);
    shooterFollower = new TalonSRX(SystemConstants.kShooterFollowerID);
    
    //Reset to factory settings and set second shooter motor to mimic master motor
    turretMotor.configFactoryDefault();
    shooterMaster.configFactoryDefault();
    shooterFollower.configFactoryDefault();
    shooterFollower.follow(shooterMaster);

    shooterMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, CannonPIDConstants.kPIDSlotX, CannonPIDConstants.kTimeout);
    shooterMaster.setSensorPhase(false);
    shooterMaster.setInverted(false);
    shooterMaster.config_kF(CannonPIDConstants.kPIDSlotX, CannonPIDConstants.kF, CannonPIDConstants.kTimeout);
    shooterMaster.config_kP(CannonPIDConstants.kPIDSlotX, CannonPIDConstants.kP, CannonPIDConstants.kTimeout);
    shooterMaster.config_kI(CannonPIDConstants.kPIDSlotX, CannonPIDConstants.kI, CannonPIDConstants.kTimeout);
    shooterMaster.config_kD(CannonPIDConstants.kPIDSlotX, CannonPIDConstants.kD, CannonPIDConstants.kTimeout);
    shooterMaster.setSelectedSensorPosition(0);
    pidCounter = 0;
  }

  @Override
  public void periodic() {

  }

  /**
   * Rotates the turret motor at a specified value
   * @param value The percent output to be sent to the motor
   */

  public void turretRotate(double value)
  {
    value = clampValue(value);
    turretMotor.set(ControlMode.PercentOutput, value);
  }

  /**
   * Sets the RPM of the shooter wheel given a percent output
   * @param value The percent output to be sent to the shooter motors
   */
  public void cannonSpin(double value)
  {
    value = clampValue(value);
    shooterMaster.set(ControlMode.PercentOutput, value);
  }

  public void cannonSpinPID(double setpoint)
  {
    shooterMaster.set(ControlMode.Velocity, setpoint * 4096);
  }

  private double clampValue(double value)
  {
    if (value > 1)
    {
      value = 1;
    }
    else if (value < -1)
    {
      value = -1;
    }
    return value;
  }

  public boolean atPIDRPM()
  {
    if (pidCounter >= 10)
    {
      return true;
    }
    else if (shooterMaster.getClosedLoopError() < 100 && shooterMaster.getClosedLoopError() > -100)
    {
      pidCounter++;
    }
    return false;
  }
}
