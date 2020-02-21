package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimitSwitchSubsystem;
import frc.robot.util.ServoHandler;


public class ResetHood extends CommandBase
{

    ServoHandler servoHandler;
    boolean isFinished;
    int counter = 0;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public ResetHood()
    {
        servoHandler = ServoHandler.getInstance();
    }
    
    @Override
    public void initialize()
    {
        isFinished = false;
        counter = 0;
        ServoHandler.isResetting = true;
    }

    @Override
    public void execute()
    {

        if (counter >= 10)
        {
            isFinished = true;
        }
        else if (LimitSwitchSubsystem.hoodSwitch.get())
        {
            servoHandler.setSpeed(0);
            counter++;
            
        }
        else
        {
            servoHandler.setSpeed(.5);
        }

    }

    @Override
    public void end(boolean interrupted)
    {
        ServoHandler.isResetting = false;
        servoHandler.setSpeed(0);
        servoHandler.setOffset(servoHandler.getRawAngle());
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