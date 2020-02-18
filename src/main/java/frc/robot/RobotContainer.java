/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auto.AutoSelector;
import frc.robot.commands.AimTurret;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimitSwitchSubsystem;
import frc.robot.subsystems.TurretSubsystem;
import frc.robot.util.ControllerInputs;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.util.ServoHandler;
import frc.robot.util.VisionHandler;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...


  private static ControllerInputs m_controllerInputs;
  private static DriveSubsystem m_robotDrive;
  private static LimitSwitchSubsystem m_limitSwitchSubsystem;
  private static TurretSubsystem m_turretSubsystem;
  private static IntakeSubsystem m_intakeSubsystem;
  private static ConveyorSubsystem m_conveyorSubsystem;
  private VisionHandler visionHandler;
  private ServoHandler servoHandler;

  private static JoystickButton runTurretButton;
  private static JoystickButton runConveyorForwardButton;
  private static JoystickButton runConveyorBackwardButton;
  private static JoystickButton runKickerButton;
  private static JoystickButton aimButton;
  private static JoystickButton aimAndShootButton;


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_robotDrive = DriveSubsystem.getInstance();
    m_controllerInputs = ControllerInputs.getInstance();
    m_limitSwitchSubsystem = LimitSwitchSubsystem.getInstance();
    m_turretSubsystem = TurretSubsystem.getInstance();
    m_intakeSubsystem = IntakeSubsystem.getInstance();
    m_conveyorSubsystem = ConveyorSubsystem.getInstance();
    visionHandler = VisionHandler.getInstance();
    servoHandler = ServoHandler.getInstance();

    // Configure the button bindings
    configureButtonBindings();
  }

  public void drive()
  {
    m_robotDrive.arcadeDrive(m_controllerInputs.getControllerLeftY(), m_controllerInputs.getControllerRightX());
  }

  public void periodic()
  {
    servoHandler.run();
    visionHandler.run();
    updateSmartdashboard();

  }

  public void updateSmartdashboard()
  {
    SmartDashboard.putNumber("Left Encoder", m_robotDrive.getLeftEncoderDistance());
    SmartDashboard.putNumber("Right Encoder", m_robotDrive.getRightEncoderDistance());
    SmartDashboard.putNumber("Heading", m_robotDrive.getHeading());
    SmartDashboard.putNumber("Left Vel", m_robotDrive.getWheelSpeeds().leftMetersPerSecond);
    SmartDashboard.putNumber("Right Vel", m_robotDrive.getWheelSpeeds().rightMetersPerSecond);

    SmartDashboard.putNumber("Target X", visionHandler.getX());
    SmartDashboard.putNumber("Target Y", visionHandler.getY());
    SmartDashboard.putNumber("Target Distance", visionHandler.getDistance());
    SmartDashboard.putNumber("Servo Angle", servoHandler.getAngle());
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    runTurretButton = new JoystickButton(m_controllerInputs.getLeftJoystick(), 1);
    runTurretButton.whenActive(() -> m_turretSubsystem.cannonSpin(1), m_turretSubsystem);
    runTurretButton.whenInactive(() -> m_turretSubsystem.cannonSpin(0), m_turretSubsystem);

    runConveyorForwardButton = new JoystickButton(m_controllerInputs.getLeftJoystick(), 2);
    runConveyorForwardButton.whenActive(() -> m_conveyorSubsystem.runConveyor(.5), m_conveyorSubsystem);
    runConveyorForwardButton.whenInactive(() -> m_conveyorSubsystem.runConveyor(0), m_conveyorSubsystem);

    runKickerButton = new JoystickButton(m_controllerInputs.getLeftJoystick(), 4);
    runKickerButton.whenActive(() -> m_conveyorSubsystem.runKicker(), m_conveyorSubsystem);
    runKickerButton.whenInactive(() -> m_conveyorSubsystem.stopKicker(), m_conveyorSubsystem);

    runConveyorBackwardButton = new JoystickButton(m_controllerInputs.getLeftJoystick(), 3);
    runConveyorBackwardButton.whenActive(() -> m_conveyorSubsystem.runConveyor(-.5), m_conveyorSubsystem);
    runConveyorBackwardButton.whenInactive(() -> m_conveyorSubsystem.runConveyor(0), m_conveyorSubsystem);

    aimButton = new JoystickButton(m_controllerInputs.getLeftJoystick(), 8);
    aimButton.whenPressed(new AimTurret());

    aimAndShootButton = new JoystickButton(m_controllerInputs.getLeftJoystick(), 9);
    aimAndShootButton.whenPressed(m_turretSubsystem.aimAndShoot());

  }


  public void resetPose()
  {
    m_robotDrive.resetOdometry(new Pose2d(0, 0, new Rotation2d(0)));
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return AutoSelector.getAutoCommand();
  }
}
