package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {

  private static DriveSubsystem m_robotDrive = null;
  private static SpeedControllerGroup m_leftMotors;
  private static SpeedControllerGroup m_rightMotors;
  private static CANSparkMax m_leftMotor1;
  private static CANSparkMax m_leftMotor2;
  private static CANSparkMax m_rightMotor1;
  private static CANSparkMax m_rightMotor2;
  private static DifferentialDrive m_drive;
  private static Gyro m_gyro;
  private static CANEncoder m_leftEncoder;
  private static CANEncoder m_rightEncoder;
  private static DifferentialDriveOdometry m_odometry;

  public static DriveSubsystem getInstance()
  {
    if (m_robotDrive == null)
    {
      m_robotDrive = new DriveSubsystem();
    }
    return m_robotDrive;
  }

  private DriveSubsystem() {
    m_leftMotor1 = new CANSparkMax(DriveConstants.kLeftMotor1Port, MotorType.kBrushless);
    m_leftMotor2 = new CANSparkMax(DriveConstants.kLeftMotor2Port, MotorType.kBrushless);
    m_rightMotor1 = new CANSparkMax(DriveConstants.kRightMotor1Port, MotorType.kBrushless);
    m_rightMotor2 = new CANSparkMax(DriveConstants.kRightMotor2Port, MotorType.kBrushless);

    m_leftMotors = new SpeedControllerGroup(m_leftMotor1, m_leftMotor2);
    m_rightMotors = new SpeedControllerGroup(m_rightMotor1, m_rightMotor2);
    m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);
    m_gyro = new ADXRS450_Gyro();

    m_leftEncoder = m_leftMotor1.getEncoder();
    m_rightEncoder = m_rightMotor1.getEncoder();
    m_leftEncoder.setPositionConversionFactor(DriveConstants.kEncoderDistancePerPulse);
    m_leftEncoder.setVelocityConversionFactor(DriveConstants.kEncoderDistancePerPulse);
    m_rightEncoder.setPositionConversionFactor(DriveConstants.kEncoderDistancePerPulse);
    m_rightEncoder.setVelocityConversionFactor(DriveConstants.kEncoderDistancePerPulse);

    resetEncoders();

    m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
    
  }

  @Override
  public void periodic() {
    m_odometry.update(Rotation2d.fromDegrees(getHeading()), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());//Put a negative on one of these since we cant invert hall effect encoders.

  }

  
  
  public DifferentialDriveWheelSpeeds getWheelSpeeds()
  {
    
    return new DifferentialDriveWheelSpeeds(m_leftEncoder.getVelocity(), m_rightEncoder.getVelocity());
  }

  public Pose2d getPose()
  {
    return m_odometry.getPoseMeters();
  }

  public void tankDriveVolts(double leftVolts, double rightVolts)
  {
    m_leftMotors.setVoltage(leftVolts * (DriveConstants.kInvertedDrivetrain ? -1.0 : 1.0));
    m_rightMotors.setVoltage(-rightVolts * (DriveConstants.kInvertedDrivetrain ? -1.0 : 1.0));
    m_drive.feed();
  }

  public void resetEncoders()
  {
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);
  }

  public double getHeading()
  {
    return Math.IEEEremainder(m_gyro.getAngle(), 360) * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }

  public void zeroHeading()
  {
    m_gyro.reset();
  }

  public double getTurnRate()
  {
    return m_gyro.getRate() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }
  
  public double getAverageEncoderDistance()
  {
    return (m_rightEncoder.getPosition() + m_leftEncoder.getPosition()) / 2.0;
  }

  public void setMaxOutput(double maxOutput)
  {
    m_drive.setMaxOutput(maxOutput);
  }

  public void arcadeDrive(double fwd, double rot)
  {
    m_drive.arcadeDrive(-fwd, -rot);
  }

  public void resetOdometry(Pose2d pose)
  {
    resetEncoders();
    m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
  }

  public void invertDrivetrain()
  {
    DriveConstants.kGyroReversed = true;
    DriveConstants.kInvertedDrivetrain = true;
  }

  public void unInvertDrivetrain()
  {
    DriveConstants.kGyroReversed = false;
    DriveConstants.kInvertedDrivetrain = false;
  }

  public double getLeftEncoderDistance()
  {
    return m_leftEncoder.getPosition();
  }

  public double getRightEncoderDistance()
  {
    return m_rightEncoder.getPosition();
  }

}
