package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;


public class TestDriveForward extends CommandBase
{
    private DriveSubsystem driveSubsystem;
    private int counter;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public TestDriveForward()
    {
        driveSubsystem = DriveSubsystem.getInstance();
    }
    
    @Override
    public void initialize()
    {
        driveSubsystem.arcadeDrive(-.25, 0);
        counter = 0;
    }

    @Override
    public void execute()
    {
        counter++;
    }

    @Override
    public void end(boolean interrupted)
    {
        driveSubsystem.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished()
    {
        if (counter >= 50)
        {
            return true;
        }
        return false;
    }
}