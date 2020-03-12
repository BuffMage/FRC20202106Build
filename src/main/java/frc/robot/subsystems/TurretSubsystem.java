package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CannonPIDConstants;
import frc.robot.Constants.SystemConstants;
import frc.robot.commands.AimTurret;
import frc.robot.commands.ApproximateHoodAndCannon;
import frc.robot.commands.PrimeCannon;
import frc.robot.commands.RunningAim;
import frc.robot.commands.SetCannonSpeed;
import frc.robot.commands.SetHoodAngle;
import frc.robot.commands.ShootPowerCells;
import frc.robot.util.ParametricCalculator;

public class TurretSubsystem extends SubsystemBase {

  private static TalonSRX turretMotor;
  private static TurretSubsystem turretSubsystem = null;
  private static TalonSRX shooterMaster;
  private static TalonSRX shooterFollower;
  private int pidCounter;
  private double turretPosition;

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
    turretPosition = turretMotor.getSelectedSensorPosition() / 4096;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Cannon Velocity (RPM)", (shooterMaster.getSelectedSensorVelocity() / 409.6) * 60);
    SmartDashboard.putNumber("Turret Rotation", turretMotor.getSelectedSensorPosition() / 4096);
    SmartDashboard.putNumber("Cannon Calculated RPM", ParametricCalculator.getRPM());
    turretPosition = turretMotor.getSelectedSensorPosition() / 4096;
  }

  public double getTurretRotation()
  {
    return turretPosition;
  }

  /**
   * Rotates the turret motor at a specified value
   * @param value The percent output to be sent to the motor
   */

  public void turretRotate(double value)
  {
    value = clampValue(value);
    
    if ((-turretMotor.getSelectedSensorPosition() / 4096 <= -55 && value > 0) && !SystemConstants.isTurretResetting)
    {
      value = 0;
    }
    else if ((-turretMotor.getSelectedSensorPosition() / 4096 >= 140 && value < 0) && !SystemConstants.isTurretResetting)
    {
      value = 0;
    }
    
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

  /**
   * Set a PID setpoint for the shooter wheel
   * @param setpoint The desired RPM for the shooter wheel
   */
  public void cannonSpinPID(double setpoint)
  {
    if (setpoint > 4500)
    {
      setpoint = 4500;
    }
    setpoint = (setpoint * 4096) / 600;
    shooterMaster.set(ControlMode.Velocity, setpoint);
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

  /**
   * Checks to see if we are at the setpoint RPM for our PID
   * @return Whether we are at our setpoint or not
   */
  public boolean atPIDRPM()
  {
    if (pidCounter >= 10)
    {
      return true;
    }
    else if ((shooterMaster.getClosedLoopError() / 409.6) * 60 < 100 && (shooterMaster.getClosedLoopError() / 409.6) * 60 > -100)
    {
      pidCounter++;
    }
    return false;
  }
/**
 * All in one command that aims, primes the cannon, sets the hood angle and cannon speed, and shoots the balls
 * @return The all in one command
 */
  public Command aimAndShoot()
  {
    Command aimAndShootCommand = new SequentialCommandGroup(
      new ParallelCommandGroup(
        new AimTurret(), 
        new PrimeCannon()),
      new ParallelCommandGroup(
        new SetHoodAngle(), 
        new SetCannonSpeed()),
      new ShootPowerCells());
    return aimAndShootCommand;
  }

  public Command aimAndShootBetter()
  {
    Command aimAndShootCommand = new SequentialCommandGroup(
      new ParallelDeadlineGroup(
        new AimTurret(),
        new ApproximateHoodAndCannon()),
      new ParallelDeadlineGroup(
        new ParallelCommandGroup(
          new SetHoodAngle(),
          new SetCannonSpeed()),
        new RunningAim()),
      new ParallelDeadlineGroup(
        new ShootPowerCells(), 
        new RunningAim())
    );
    return aimAndShootCommand;
  }

  public void resetTurretEncoder()
  {
    turretMotor.setSelectedSensorPosition(0);
    SystemConstants.hasTurretReset = true;
  }

  public void resetCannonPID()
  {
    pidCounter = 0;
  }
}
