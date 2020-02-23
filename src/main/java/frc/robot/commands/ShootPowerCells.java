package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.SystemConstants;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.TurretSubsystem;
import frc.robot.util.ServoHandler;
import frc.robot.util.VisionHandler;


public class ShootPowerCells extends CommandBase
{
    private static double fiveCellShotTime = 3.5;
    private static ConveyorSubsystem conveyorSubsystem;
    private static IntakeSubsystem intakeSubsystem;
    private static TurretSubsystem turretSubsystem;
    private static ServoHandler servoHandler;
    private static VisionHandler visionHandler;
    private static int counter;

    private boolean isFinished;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public ShootPowerCells()
    {
        conveyorSubsystem = ConveyorSubsystem.getInstance();
        intakeSubsystem = IntakeSubsystem.getInstance();
        turretSubsystem = TurretSubsystem.getInstance();
        servoHandler = ServoHandler.getInstance();
        visionHandler = VisionHandler.getInstance();
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
        counter++;
        if (counter < 12)
        {
            conveyorSubsystem.runConveyor(-.5);
        }
        else if (counter >= 12)
        {
            conveyorSubsystem.runConveyor(.5);
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
        intakeSubsystem.stopIntake();
        conveyorSubsystem.stopKicker();
        conveyorSubsystem.runConveyor(0);
        turretSubsystem.cannonSpin(0);
        servoHandler.setAngle(0);
        visionHandler.setNormalView();
        if (interrupted)
        {
            System.out.println("Warning! Power cell shooting has unexpectedly stopped");
        }
        else
        {
            System.out.println("Power cells have been shot!");
        }
        SystemConstants.isShooting = false;
    }

    @Override
    public boolean isFinished()
    {
        return isFinished;
    }
}