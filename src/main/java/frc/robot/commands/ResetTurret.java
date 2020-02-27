package frc.robot.commands;

import frc.robot.Constants.SystemConstants;
import frc.robot.subsystems.LimitSwitchSubsystem;
import frc.robot.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;


/**
 * An example command that uses an example subsystem.
 */
public class ResetTurret extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private TurretSubsystem m_turretSubsystem;
  private boolean isFinished;

  public ResetTurret()
  {
    m_turretSubsystem = TurretSubsystem.getInstance();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isFinished = false;
      if(LimitSwitchSubsystem.turretSwitch.get())
      {
        isFinished = true;
      }
    SystemConstants.isTurretResetting = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (LimitSwitchSubsystem.turretSwitch.get())
    {
      isFinished = true;
      m_turretSubsystem.turretRotate(0);
    }
    else
    {
      m_turretSubsystem.turretRotate(.1);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_turretSubsystem.turretRotate(0);
    m_turretSubsystem.resetTurretEncoder();
    SystemConstants.isTurretResetting = false;
    if (interrupted)
    {
      System.out.println("Turret did not reset! Use caution");
    }
    else
    {
      System.out.println("Turret reset!");
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }


}