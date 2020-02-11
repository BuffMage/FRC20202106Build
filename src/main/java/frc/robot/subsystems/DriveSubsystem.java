package frc.robot.subsystems;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {

  private static DriveSubsystem m_robotDrive = null;
  

  public static DriveSubsystem getInstance()
  {
    if (m_robotDrive == null)
    {
      m_robotDrive = new DriveSubsystem();
    }
    return m_robotDrive;
  }

  private DriveSubsystem() {

  }

  @Override
  public void periodic() {

  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds()
  {
    return new DifferentialDriveWheelSpeeds(0, 0);
  }

  public Pose2d getPose()
  {
    return new Pose2d();
  }

  public void tankDriveVolts(double leftVolts, double rightVolts)
  {
    
  }
}
