package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;


public class UninvertDrivetrain extends CommandBase
{
    DriveSubsystem driveSubsystem;
    boolean isFinished;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public UninvertDrivetrain()
    {
        driveSubsystem = DriveSubsystem.getInstance();
        isFinished = false;
    }
    
    @Override
    public void initialize()
    {
        driveSubsystem.unInvertDrivetrain();
        isFinished = true;
    }

    @Override
    public void end(boolean interrupted)
    {
        System.out.println("Uninverted Drivetrain");
    }

    @Override
    public boolean isFinished()
    {
        return isFinished;
    }
}