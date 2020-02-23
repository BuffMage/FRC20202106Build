package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SystemConstants;

public class ConveyorSubsystem extends SubsystemBase {

  private static TalonSRX conveyorMotor;
  private static TalonSRX kickerMotor;
  private static ConveyorSubsystem conveyorSubsystem = null;
  private static IntakeSubsystem intakeSubsystem;
  public static boolean isRunningConveyor = false;
  private static int conveyorCounter = 0;

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
    intakeSubsystem = IntakeSubsystem.getInstance();
    conveyorMotor.configFactoryDefault();
  }

  @Override
  public void periodic() {

    if ((LimitSwitchSubsystem.conveyorSwitch.get() && conveyorCounter == 0) && !SystemConstants.isShooting)
    {
      runConveyor(.5);
      intakeSubsystem.stopIntake();
      isRunningConveyor = true;
    }
    else if (conveyorCounter <= 15 && !SystemConstants.isShooting)
    {
      conveyorCounter++;
    }
    /*
    else if (LimitSwitchSubsystem.conveyorSwitch.get() && !SystemConstants.isShooting)
    {
      runConveyor(0);
    }*/
    else if (!SystemConstants.isShooting)
    {
      runConveyor(0);
      isRunningConveyor = false;
      conveyorCounter = 0;
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
