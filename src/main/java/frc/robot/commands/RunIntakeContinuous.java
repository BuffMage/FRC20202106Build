//Do not use this as an actual command, copy this code into another command file and change class name
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.SystemConstants;
import frc.robot.subsystems.IntakeSubsystem;


public class RunIntakeContinuous extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public RunIntakeContinuous()
    {

    }
    
    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        if (SystemConstants.stopIntake)
        {
            IntakeSubsystem.getInstance().stopIntake();
        }
        else
        {
            IntakeSubsystem.getInstance().runIntakeForward();
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        IntakeSubsystem.getInstance().stopIntake();
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}