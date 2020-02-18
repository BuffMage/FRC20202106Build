package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;


public class ShootPowerCells extends CommandBase
{
    private static double fiveCellShotTime = 3.5;
    private static ConveyorSubsystem conveyorSubsystem;
    private static IntakeSubsystem intakeSubsystem;
    private static int counter;

    private static boolean isFinished;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public ShootPowerCells()
    {
        conveyorSubsystem = ConveyorSubsystem.getInstance();
        intakeSubsystem = IntakeSubsystem.getInstance();
    }
    
    @Override
    public void initialize()
    {
        isFinished = false;
        counter = 0;
        conveyorSubsystem.runKicker();
    }

    @Override
    public void execute()
    {
        conveyorSubsystem.runKicker();

        if (counter >= 12)
        {
            conveyorSubsystem.runConveyor(-.5);
            intakeSubsystem.runIntakeForward();
        }
        if (counter >= fiveCellShotTime / .02)
        {
            isFinished = true;
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        if (interrupted)
        {
            System.out.println("Warning! Power cell shooting has unexpectedly stopped");
        }
        else
        {
            System.out.println("Power cells have been shot!");
        }
    }

    @Override
    public boolean isFinished()
    {
        return isFinished;
    }
}