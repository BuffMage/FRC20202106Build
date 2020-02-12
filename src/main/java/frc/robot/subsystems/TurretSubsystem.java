package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SystemConstants;

public class TurretSubsystem extends SubsystemBase {

  private static TalonSRX turretMotor;
  private static TurretSubsystem turretSubsystem = null;

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
    turretMotor.set(ControlMode.PercentOutput, value);
  }
}
