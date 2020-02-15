package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimitSwitchSubsystem;
import frc.robot.util.ServoHandler;


public class ResetHood extends CommandBase
{

    ServoHandler servoHandler;
    boolean isFinished;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public ResetHood()
    {
        servoHandler = ServoHandler.getInstance();
    }
    
    @Override
    public void initialize()
    {
        isFinished = false;
    }

    @Override
    public void execute()
    {
        servoHandler.setSpeed(.5);
        if (LimitSwitchSubsystem.hoodSwitch.get())
        {
            isFinished = true;
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        servoHandler.setSpeed(0);
        servoHandler.setOffset(servoHandler.getAngle());
        if (interrupted)
        {
            System.out.println("Hood did not reset! Use caution");
        }
        else
        {
            System.out.println("Hood reset");
        }
    }

    @Override
    public boolean isFinished()
    {
        return isFinished;
    }
}