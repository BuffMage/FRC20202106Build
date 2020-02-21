package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SystemConstants;

public class IntakeSubsystem extends SubsystemBase {

  private static TalonSRX intakeMotor;
  private static DoubleSolenoid intakePiston;
  private static IntakeSubsystem intakeSubsystem = null;

  public static IntakeSubsystem getInstance()
  {
    if (intakeSubsystem == null)
    {
      intakeSubsystem = new IntakeSubsystem();
    }
    return intakeSubsystem;
  }

  private IntakeSubsystem() {
    intakeMotor = new TalonSRX(SystemConstants.kIntakeMotorID);
    intakePiston = new DoubleSolenoid(SystemConstants.kIntakeSolenoidForward, SystemConstants.kIntakeSolenoidReverse);
    pickupIntake();
  }

  @Override
  public void periodic() {

  }

  public void runIntakeForward()
  {
    intakeMotor.set(ControlMode.PercentOutput, -1);
  }

  public void runIntakeReverse()
  {
    intakeMotor.set(ControlMode.PercentOutput, 1);
  }

  public void dropIntake()
  {
    intakePiston.set(Value.kForward);
  }

  public void pickupIntake()
  {
    intakePiston.set(Value.kReverse);
  }

  public void stopIntake()
  {
    intakeMotor.set(ControlMode.PercentOutput, 0);
  }
}
