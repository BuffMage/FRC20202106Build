package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;


public class InvertDrivetrain extends CommandBase
{
    DriveSubsystem driveSubsystem;
    boolean isFinished;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public InvertDrivetrain()
    {
        driveSubsystem = DriveSubsystem.getInstance();
        isFinished = false;
    }
    
    @Override
    public void initialize()
    {
        driveSubsystem.invertDrivetrain();
        isFinished = true;
    }

    @Override
    public void end(boolean interrupted)
    {
        System.out.println("Inverted Drivetrain");
    }

    @Override
    public boolean isFinished()
    {
        return isFinished;
    }
}