package frc.robot.commands;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;


public class ResetPose extends CommandBase
{
    DriveSubsystem driveSubsystem;
    boolean isFinished;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public ResetPose()
    {
        driveSubsystem = DriveSubsystem.getInstance();
        isFinished = false;
    }
    
    @Override
    public void initialize()
    {
        driveSubsystem.resetOdometry(new Pose2d(0, 0, new Rotation2d(0)));
        isFinished = true;
    }

    @Override
    public void execute()
    {

    }

    @Override
    public void end(boolean interrupted)
    {
        System.out.println("Reset Pose");
    }

    @Override
    public boolean isFinished()
    {
        return isFinished;
    }
}