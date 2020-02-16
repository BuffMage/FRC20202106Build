package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.CannonPIDConstants;
import frc.robot.subsystems.TurretSubsystem;


public class PrimeCannon extends CommandBase
{
    private TurretSubsystem turretSubsystem;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public PrimeCannon()
    {
        turretSubsystem = TurretSubsystem.getInstance();
    }
    
    @Override
    public void initialize()
    {
        turretSubsystem.cannonSpinPID(CannonPIDConstants.kCannonRestingRPM);
    }

    @Override
    public void end(boolean interrupted)
    {
        if (interrupted)
        {
            System.out.println("Warning! Cannon prime canceled. Use caution");
        }
        else
        {
            System.out.println("Cannon Primed, proceed with adjustments");
        }
    }

    @Override
    public boolean isFinished()
    {
        return turretSubsystem.atPIDRPM();
    }
}