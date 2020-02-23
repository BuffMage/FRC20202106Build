package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TurretSubsystem;
import frc.robot.util.ParametricCalculator;
import frc.robot.util.VisionHandler;


public class SetCannonSpeed extends CommandBase
{
    //For a certain number of RPM we get one meter per second
    private static double velocityToRPMConversionFactor = 4500 / 7.55;
    private static VisionHandler visionHandler;
    private static TurretSubsystem turretSubsystem;

    private boolean isFinished;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public SetCannonSpeed()
    {
        isFinished = false;
        visionHandler = VisionHandler.getInstance();
        turretSubsystem = TurretSubsystem.getInstance();
    }
    
    @Override
    public void initialize()
    {
        isFinished = false;
        turretSubsystem.cannonSpinPID(ParametricCalculator.getInitialVelocity(visionHandler.getDistance()) * velocityToRPMConversionFactor);
    }

    @Override
    public void execute()
    {
        if (turretSubsystem.atPIDRPM())
        {
            isFinished = true;
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        if (interrupted)
        {
            System.out.println("Warning! Cannon not at correct velocity");
        }
        else
        {
            System.out.println("Cannon has reached required velocity!");
        }
    }

    @Override
    public boolean isFinished()
    {
        return isFinished;
    }
}