//Do not use this as an actual command, copy this code into another command file and change class name
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TurretSubsystem;


public class TurnTurretTo extends CommandBase
{
    double angleToTurnTo = 0;
    TurretSubsystem turretSubsystem;
    boolean isFinished = false;
    boolean turningRight = true;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public TurnTurretTo(double angle)
    {
        angleToTurnTo = angle;
        turretSubsystem = TurretSubsystem.getInstance();
        
    }
    
    @Override
    public void initialize()
    {
        isFinished = false;
        if (-turretSubsystem.getTurretRotation() < angleToTurnTo)
        {
            turningRight = true;
        }
        else if (-turretSubsystem.getTurretRotation() > angleToTurnTo)
        {
            turningRight = false;
        }
    }

    @Override
    public void execute()
    {
        if (angleToTurnTo > -turretSubsystem.getTurretRotation() && turningRight)
        {
            turretSubsystem.turretRotate(-.5);
        }
        else if (angleToTurnTo < -turretSubsystem.getTurretRotation() && !turningRight)
        {
            turretSubsystem.turretRotate(.5);
        }
        else
        {
            isFinished = true;
            turretSubsystem.turretRotate(0);
        }

    }

    @Override
    public void end(boolean interrupted)
    {
        turretSubsystem.turretRotate(0);
    }

    @Override
    public boolean isFinished()
    {
        return isFinished;
    }
}