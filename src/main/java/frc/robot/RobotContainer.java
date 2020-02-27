/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.SystemConstants;
import frc.robot.auto.AutoSelector;
import frc.robot.commands.AimTurret;
import frc.robot.commands.SetCannonSpeed;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimitSwitchSubsystem;
import frc.robot.subsystems.TurretSubsystem;
import frc.robot.util.ControllerInputs;
import frc.robot.util.ParametricCalculator;
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
  private static JoystickButton setServoAngleTop;
  private static JoystickButton setServoAngleMin;
  private static JoystickButton setCannonSpeed;


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

  public void robotInit()
  {
    m_intakeSubsystem.pickupIntake();
  }

  public void drive()
  {
    m_robotDrive.arcadeDrive(m_controllerInputs.getLeftJoystick().getY(), m_controllerInputs.getRightJoystick().getX());
  }

  public void periodic()
  {
    visionHandler.run();
    updateSmartdashboard();
    buttonManager();
    

  }

  public void updateSmartdashboard()
  {
    SmartDashboard.putNumber("Left Encoder", m_robotDrive.getLeftEncoderDistance());
    SmartDashboard.putNumber("Right Encoder", m_robotDrive.getRightEncoderDistance());
    SmartDashboard.putNumber("Heading", m_robotDrive.getHeading());
    SmartDashboard.putNumber("Gyro Rate", m_robotDrive.getTurnRate());
    SmartDashboard.putNumber("Left Vel", m_robotDrive.getWheelSpeeds().leftMetersPerSecond);
    SmartDashboard.putNumber("Right Vel", m_robotDrive.getWheelSpeeds().rightMetersPerSecond);

    SmartDashboard.putNumber("Target X", visionHandler.getX());
    SmartDashboard.putNumber("Target Y", visionHandler.getY());
    SmartDashboard.putNumber("Target Distance", visionHandler.getDistance());
    SmartDashboard.putNumber("Servo Angle", servoHandler.getAngle());
    SmartDashboard.putNumber("Supposed Angle", ParametricCalculator.getHoodAngle(visionHandler.getDistance()));
    SmartDashboard.putNumber("Servo Target Angle", ServoHandler.target);
    SmartDashboard.putNumber("Calculated Angle", 80 - ParametricCalculator.getHoodAngle(visionHandler.getDistance()));
    SmartDashboard.putNumber("Initial Velocity", ParametricCalculator.getInitialVelocity(visionHandler.getDistance()));
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    /*
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

    setServoAngleMin = new JoystickButton(m_controllerInputs.getRightJoystick(), 1);
    setServoAngleMin.whenPressed(() -> servoHandler.setAngle(430), servoHandler);

    setServoAngleTop = new JoystickButton(m_controllerInputs.getRightJoystick(), 3);
    setServoAngleTop.whenPressed(() -> servoHandler.setAngle(0), servoHandler);

    setCannonSpeed = new JoystickButton(m_controllerInputs.getRightJoystick(), 2);
    setCannonSpeed.whenPressed(() -> m_turretSubsystem.cannonSpinPID(3500));
    
    */
    aimAndShootButton = new JoystickButton(m_controllerInputs.getController(), 4);
    aimAndShootButton.whenPressed(m_turretSubsystem.aimAndShoot());

    aimButton = new JoystickButton(m_controllerInputs.getController(), 1);
    aimButton.whenPressed(new AimTurret());
  }

  public void buttonManager()
  {
    //Create some temporary buttons and stuff
    if(DriverStation.getInstance().isOperatorControl() || SystemConstants.commandRunning)
    {
      if (m_controllerInputs.getIntakeDown()) m_intakeSubsystem.dropIntake();
      if (m_controllerInputs.getIntakeUp()) m_intakeSubsystem.pickupIntake();
      //if (m_controllerInputs.getLeftJoystick().getRawButton(1)) m_turretSubsystem.cannonSpinPID(4500);
      if (m_controllerInputs.getLeftJoystick().getRawButton(1)) servoHandler.setAngle(435);
      if (m_controllerInputs.getLeftJoystick().getRawButton(2)) m_turretSubsystem.cannonSpinPID(3500);
      if (m_controllerInputs.getLeftJoystick().getRawButton(3)) m_turretSubsystem.cannonSpin(0);
      if (m_controllerInputs.getLeftJoystick().getRawButton(3)) servoHandler.setAngle(0);;
      

      if (m_controllerInputs.getRunIntake())
      {
        m_intakeSubsystem.runIntakeForward();
      }
      else if(m_controllerInputs.getController().getRawButton(11))
      {
        m_intakeSubsystem.runIntakeReverse();
      }
      else
      {
        
        m_intakeSubsystem.stopIntake();
      }

      if (m_controllerInputs.getRunConveyorForward())
      {
        m_conveyorSubsystem.runConveyor(.5);;
      }
      else if (m_controllerInputs.getRunConveyorBackward())
      {
        m_conveyorSubsystem.runConveyor(-.5);;
      }
      else
      {
        m_conveyorSubsystem.runConveyor(0);
      }

      if (m_controllerInputs.getRunKicker())
      {
        m_conveyorSubsystem.runKicker();
      }
      else if (!SystemConstants.isShooting)
      {
        m_conveyorSubsystem.stopKicker();
      }

      if (!SystemConstants.isShooting)
      {
        if (-m_controllerInputs.getController().getRawAxis(2) > .2)
        {
          m_turretSubsystem.turretRotate(.2);
        }
        else if (-m_controllerInputs.getController().getRawAxis(2) < -.2)
        {
          m_turretSubsystem.turretRotate(-.2);
        }
        else
        {
          m_turretSubsystem.turretRotate(-m_controllerInputs.getController().getRawAxis(2));
        }
      }

    }
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
