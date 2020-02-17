package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.TurretPIDConstants;
import frc.robot.subsystems.TurretSubsystem;
import frc.robot.util.VisionHandler;


public class AimTurret extends CommandBase
{
    private PIDController turretPID;
    private TurretSubsystem turretSubsystem;
    private VisionHandler visionHandler;
    private double setpoint;
    private double tolerance;
    private int counter;
    private double max;
    private double min;
    private double timeout;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public AimTurret()
    {
        turretPID = new PIDController(
            TurretPIDConstants.kP, 
            TurretPIDConstants.kI, 
            TurretPIDConstants.kD);

        turretSubsystem = TurretSubsystem.getInstance();
        visionHandler = VisionHandler.getInstance();
        setpoint = 0;//Since we want to center the turret on the target
        tolerance = .25;// within half a degree of acurracy
        //Maximum and minimum values for turret output
        max = .2;
        min = -.2;
        //How long should we wait to see if we are at our setpoint
        timeout = 1;
    }
    
    @Override
    public void initialize()
    {
        //Start PID
        turretPID.setSetpoint(setpoint);
        turretPID.setTolerance(tolerance);
    }

    @Override
    public void execute()
    {
        double output = turretPID.calculate(visionHandler.getX());
        //Make sure our output is within our bounds (PIDS can produce values greater than 1 and less than -1)
        if (output > max)
        {
            output = max;
        }
        else if (output < min)
        {
            output = min;
        }
        //Calculates the PID based on error, feeds the output to the turretRotate function
        turretSubsystem.turretRotate(output);
        
    }

    @Override
    public void end(boolean interrupted)
    {
        //Reset PID and stop motor
        turretPID.reset();
        turretSubsystem.turretRotate(0);
        counter = 0;
    }

    @Override
    public boolean isFinished()
    {
        
        if (turretPID.atSetpoint())
        {
            counter++;
        }
        else
        {
            counter = 0;
        }
        if (counter >= (int)(timeout / .02))
        {
            return true;
        }
        return false;
    }
}