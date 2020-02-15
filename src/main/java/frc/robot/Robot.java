/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//Created by David Dick and James DeLoach
package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.util.ServoHandler;
import frc.robot.util.VisionHandler;


public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  private VisionHandler visionHandler;
  private ServoHandler servoHandler;
  private Joystick joy;
  private TalonSRX shooter1;
  private TalonSRX shooter2;
  private TalonSRX conveyor;
  private TalonSRX kicker;
  private TalonSRX turret;


  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    turret = new TalonSRX(4);
    conveyor = new TalonSRX(2);
    kicker = new TalonSRX(0);
    shooter1 = new TalonSRX(1);
    shooter2 = new TalonSRX(5);
    turret.configFactoryDefault();
    conveyor.configFactoryDefault();
    kicker.configFactoryDefault();
    shooter1.configFactoryDefault();
    shooter2.configFactoryDefault();
    shooter2.follow(shooter1);
    m_robotContainer = new RobotContainer();
    visionHandler = VisionHandler.getInstance();
    servoHandler = ServoHandler.getInstance();
    joy = new Joystick(3);
    SmartDashboard.putNumber("Target Angle", 0);

  }

  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    servoHandler.run();
    SmartDashboard.putNumber("Target X", visionHandler.getX());
    SmartDashboard.putNumber("Target Y", visionHandler.getY());
    SmartDashboard.putNumber("Target Distance", visionHandler.getDistance());
    SmartDashboard.putNumber("Servo Angle", servoHandler.getAngle());
    servoHandler.tempPID(SmartDashboard.getNumber("Target Angle", 0));
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void autonomousInit() {
    m_robotContainer.resetPose();
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    //m_robotContainer.drive();

    if (joy.getRawButton(1))
    {
      shooter1.set(ControlMode.PercentOutput, 1);
      //servoHandler.setSpeed(.2);
    }
    else if (joy.getRawButton(3))
    {
      //servoHandler.setSpeed(-.2);
      shooter1.set(ControlMode.PercentOutput, -1);
    }
    else
    {
      //servoHandler.setSpeed(0);
      shooter1.set(ControlMode.PercentOutput, 0);
    }

    if (joy.getRawButton(6))
    {
      servoHandler.setSpeed(.2);
    }
    else if (joy.getRawButton(7))
    {
      servoHandler.setSpeed(-.2);
    }
    else
    {
      servoHandler.setSpeed(0);
    }

    if (joy.getRawButton(2))
    {
      kicker.set(ControlMode.PercentOutput, 1);
    }
    else
    {
      kicker.set(ControlMode.PercentOutput, 0);
    }
    if (joy.getRawButton(4))
    {
      conveyor.set(ControlMode.PercentOutput, -.5);
    }
    else
    {
      conveyor.set(ControlMode.PercentOutput, 0);
    }

    if (joy.getRawButton(8))
    {
      turret.set(ControlMode.PercentOutput, .25);
    }
    else if (joy.getRawButton(9))
    {
      turret.set(ControlMode.PercentOutput, -.25);
    }
    else
    {
      turret.set(ControlMode.PercentOutput, 0);
    }
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }


  @Override
  public void testPeriodic() {
  }
}
