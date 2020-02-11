package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {

  private static DriveSubsystem m_robotDrive = null;
  private static SpeedControllerGroup m_leftMotors;
  private static SpeedControllerGroup m_rightMotors;
  

  public static DriveSubsystem getInstance()
  {
    if (m_robotDrive == null)
    {
      m_robotDrive = new DriveSubsystem();
    }
    return m_robotDrive;
  }

  private DriveSubsystem() {

    //m_leftMotors = new SpeedControllerGroup(speedController, speedControllers)

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
