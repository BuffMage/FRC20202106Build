package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SystemConstants;

public class ConveyorSubsystem extends SubsystemBase {

  private static TalonSRX conveyorMotor;
  private static TalonSRX kickerMotor;
  private static ConveyorSubsystem conveyorSubsystem = null;

  public static ConveyorSubsystem getInstance()
  {
    if (conveyorSubsystem == null)
    {
      conveyorSubsystem = new ConveyorSubsystem();
    }
    return conveyorSubsystem;
  }

  private ConveyorSubsystem() {
    conveyorMotor = new TalonSRX(SystemConstants.kConveyorMotorID);
    kickerMotor = new TalonSRX(SystemConstants.kKickerMotorID);
    conveyorMotor.configFactoryDefault();
  }

  @Override
  public void periodic() {
    
    if (LimitSwitchSubsystem.conveyorSwitch.get())
    {
      runConveyor(.5);
    }
    else
    {
      runConveyor(0);
    }

  }

  public void runConveyor(double value)
  {
    value = clampValue(-value);
    conveyorMotor.set(ControlMode.PercentOutput, value);
  }

  public void runKicker()
  {
    kickerMotor.set(ControlMode.PercentOutput, 1);
  }

  public void stopKicker()
  {
    kickerMotor.set(ControlMode.PercentOutput, 0);
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
}
