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
import frc.robot.subsystems.ClimbSubsystem;
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
  private static TurretSubsystem m_turretSubsystem;
  private static IntakeSubsystem m_intakeSubsystem;
  private static ConveyorSubsystem m_conveyorSubsystem;
  private static ClimbSubsystem m_climbSubsystem;
  private VisionHandler visionHandler;
  private ServoHandler servoHandler;

  private static JoystickButton aimAndShootButton;

  public static boolean manualConveyor;



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_robotDrive = DriveSubsystem.getInstance();
    m_controllerInputs = ControllerInputs.getInstance();
    m_turretSubsystem = TurretSubsystem.getInstance();
    m_intakeSubsystem = IntakeSubsystem.getInstance();
    m_conveyorSubsystem = ConveyorSubsystem.getInstance();
    m_climbSubsystem = ClimbSubsystem.getInstance();
    visionHandler = VisionHandler.getInstance();
    servoHandler = ServoHandler.getInstance();
    LimitSwitchSubsystem.initialize();

    // Configure the button bindings
    configureButtonBindings();
    manualConveyor = false;
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
    SmartDashboard.putNumber("Heading", m_robotDrive.getHeading());
    SmartDashboard.putNumber("Gyro Rate", m_robotDrive.getTurnRate());
    SmartDashboard.putNumber("Target X", visionHandler.getX());
    SmartDashboard.putNumber("Target Y", visionHandler.getY());
    SmartDashboard.putNumber("Target Distance", visionHandler.getDistance());
    SmartDashboard.putNumber("Servo Angle", servoHandler.getAngle());
    SmartDashboard.putNumber("Servo Target Angle", ServoHandler.target);
    SmartDashboard.putNumber("Calculated Angle", 80 - ParametricCalculator.getHoodAngle(visionHandler.getDistance()));
    SmartDashboard.putNumber("Initial Velocity", ParametricCalculator.getInitialVelocity(visionHandler.getDistance()));
    
    Boolean [] switches = new Boolean[3];
    switches[0] = LimitSwitchSubsystem.turretSwitch.get();
    switches[1] = LimitSwitchSubsystem.hoodSwitch.get();
    switches[2] = LimitSwitchSubsystem.conveyorSwitch.get();
    SmartDashboard.putBooleanArray("Limit Switches (Turret, Hood, Conveyor):", switches);
    
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    aimAndShootButton = new JoystickButton(m_controllerInputs.getController(), 4);
    aimAndShootButton.whenPressed(m_turretSubsystem.aimAndShoot());
  }

  public void buttonManager()
  {
    
    if(DriverStation.getInstance().isOperatorControl() || !SystemConstants.commandRunning)
    {
      //Put the intake up or down
      if (m_controllerInputs.getIntakeDown()) m_intakeSubsystem.dropIntake();
      if (m_controllerInputs.getIntakeUp()) m_intakeSubsystem.pickupIntake();
      //Run the intake in forward or reverse
      if (m_controllerInputs.getRunIntake())
      {
        m_intakeSubsystem.runIntakeForward();
      }
      else if(m_controllerInputs.getRightJoystick().getRawButton(2))
      {
        m_intakeSubsystem.runIntakeReverse();
      }
      else
      {
        
        m_intakeSubsystem.stopIntake();
      }
      //Run the conveyor in forward or reverse
      if (m_controllerInputs.getRunConveyorForward())
      {
        m_conveyorSubsystem.runConveyor(.5);
        manualConveyor = true;
      }
      else if (m_controllerInputs.getRunConveyorBackward())
      {
        m_conveyorSubsystem.runConveyor(-.5);
        manualConveyor = true;
      }
      else
      {
        manualConveyor = false;
      }
      //Rotate the turret manually, but only when we are not shooting
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

      //Temporary Climb Buttons
      if (m_controllerInputs.getPutElevatorUp()) m_climbSubsystem.putElevatorUp();
      if (m_controllerInputs.getPutElevatorDown()) m_climbSubsystem.putElevatorDown();
      if (m_controllerInputs.getRunElevatorUp())
      {
        m_climbSubsystem.runElevatorUp();
      }
      else if (m_controllerInputs.getRunElevatorDown())
      {
        m_climbSubsystem.runElevatorDown();
      }
      else
      {
        m_climbSubsystem.stopElevator();
      }

      if (m_controllerInputs.getRunWinchForward())
      {
        m_climbSubsystem.runWinchForward();
      }
      else if (m_controllerInputs.getRunWinchReverse())
      {
        m_climbSubsystem.runWinchReverse();
      }
      else
      {
        m_climbSubsystem.stopWinch();
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
