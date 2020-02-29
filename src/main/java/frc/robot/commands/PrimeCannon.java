package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.CannonPIDConstants;
import frc.robot.Constants.SystemConstants;
import frc.robot.subsystems.TurretSubsystem;
import frc.robot.util.VisionHandler;


public class PrimeCannon extends CommandBase
{
    private TurretSubsystem turretSubsystem;
    private VisionHandler visionHandler;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public PrimeCannon()
    {
        turretSubsystem = TurretSubsystem.getInstance();
        visionHandler = VisionHandler.getInstance();
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
            SystemConstants.isShooting = false;
            visionHandler.setNormalView();
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