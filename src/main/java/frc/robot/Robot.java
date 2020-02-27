/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//Created by David Dick and James DeLoach
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.ResetHood;
import frc.robot.commands.ResetTurret;
import frc.robot.util.VisionHandler;


public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private Command m_hoodTestCommand;
  private Command m_resetTurret;

  private RobotContainer m_robotContainer;
  private VisionHandler visionHandler;


  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    visionHandler = VisionHandler.getInstance();
    m_robotContainer.robotInit();
    visionHandler.setNormalView();
  }

  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
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
    m_hoodTestCommand = new ResetHood();
    m_hoodTestCommand.schedule();
    m_resetTurret = new ResetTurret();
    m_resetTurret.schedule();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    visionHandler.setNormalView();
  }

  @Override
  public void teleopPeriodic() {
    m_robotContainer.drive();
    m_robotContainer.periodic();
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }


  @Override
  public void testPeriodic() {
  }
}
