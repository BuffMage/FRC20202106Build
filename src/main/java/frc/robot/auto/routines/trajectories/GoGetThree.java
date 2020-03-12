//Copy this file and rename it to create another trajectory

package frc.robot.auto.routines.trajectories;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.ResetPose;
import frc.robot.subsystems.DriveSubsystem;

public class GoGetThree//Change this to match file name
{
    public static Command getTrajectory()
    {
        
        DriveSubsystem m_robotDrive = DriveSubsystem.getInstance();
        Trajectory trajectory = null;
        String trajectoryJSON = "paths/GetThreeMore.wpilib.json";//Change this to our specified path
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
            //Sets our initial pose as the point of origin, otherwise robot will think it first needs to travel to initial position
            trajectory = trajectory.relativeTo(trajectory.getInitialPose());
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }
        
        
        RamseteCommand ramseteCommand = new RamseteCommand(
            trajectory,
            m_robotDrive::getPose,
            new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
            new SimpleMotorFeedforward(DriveConstants.ksVolts,
                DriveConstants.kvVoltSecondsPerMeter,
                    DriveConstants.kaVoltSecondsSquaredPerMeter),
            DriveConstants.kDriveKinematics,
            m_robotDrive::getWheelSpeeds,
            new PIDController(DriveConstants.kPDriveVel, 0, 0),
            new PIDController(DriveConstants.kPDriveVel, 0, 0),
            // RamseteCommand passes volts to the callback
            m_robotDrive::tankDriveVolts,
            m_robotDrive
        );
        return ramseteCommand.andThen(() -> m_robotDrive.tankDriveVolts(0, 0)).andThen(new ResetPose());
    }
}