package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SystemConstants;

public class ClimbSubsystem extends SubsystemBase {

  private static TalonSRX elevatorMotor;
  private static DoubleSolenoid climbPiston;
  private static CANSparkMax winchMotor;
  private static ClimbSubsystem climbSubsystem = null;

  public static ClimbSubsystem getInstance()
  {
    if (climbSubsystem == null)
    {
      climbSubsystem = new ClimbSubsystem();
    }
    return climbSubsystem;
  }

  private ClimbSubsystem() {
    elevatorMotor = new TalonSRX(SystemConstants.kElevatorMotorID);
    winchMotor = new CANSparkMax(SystemConstants.kWinchMotorID, MotorType.kBrushless);
    climbPiston = new DoubleSolenoid(SystemConstants.kClimbSolenoidForward, SystemConstants.kClimbSolenoidReverse);
  }

  @Override
  public void periodic() {

  }

  public void putElevatorUp()
  {
    climbPiston.set(Value.kForward);
  }

  public void putElevatorDown()
  {
    climbPiston.set(Value.kReverse);
  }

  public void runElevatorUp()
  {
    elevatorMotor.set(ControlMode.PercentOutput, 1);
  }

  public void runElevatorDown()
  {
    elevatorMotor.set(ControlMode.PercentOutput, -1);
  }

  public void stopElevator()
  {
    elevatorMotor.set(ControlMode.PercentOutput, 0);
  }

  public void runWinchForward()
  {
    winchMotor.set(1);
  }

  public void runWinchReverse()
  {
    winchMotor.set(-1);
  }

  public void stopWinch()
  {
    winchMotor.set(0);
  }
}
