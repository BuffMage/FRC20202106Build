package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.SystemConstants;
import frc.robot.Constants.TurretPIDConstants;
import frc.robot.subsystems.TurretSubsystem;
import frc.robot.util.VisionHandler;


public class RunningAim extends CommandBase
{
    private PIDController turretPID;
    private TurretSubsystem turretSubsystem;
    private VisionHandler visionHandler;
    private double setpoint;
    private double tolerance;
    private double max;
    private double min;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public RunningAim()
    {
        turretPID = new PIDController(
            TurretPIDConstants.kP, 
            TurretPIDConstants.kI, 
            TurretPIDConstants.kD);

        turretSubsystem = TurretSubsystem.getInstance();
        visionHandler = VisionHandler.getInstance();
        setpoint = 0;//Since we want to center the turret on the target
        tolerance = .5;// within half a degree of acurracy
        //Maximum and minimum values for turret output
        max = .2;
        min = -.2;
    }
    
    @Override
    public void initialize()
    {
        //Start PID
        turretPID.setSetpoint(setpoint);
        turretPID.setTolerance(tolerance);
        SystemConstants.isShooting = true;
        visionHandler.setVisionProcessingView();
    }

    @Override
    public void execute()
    {
        double output = turretPID.calculate(visionHandler.getX());
        //Make sure our output is within our bounds (PIDS can produce values greater than 1 and less than -1, so we must clamp the value)
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
        if (interrupted)
        {
            SystemConstants.isShooting = false;
            System.out.println("Warning! RunningAim has stopped unexpectedly");
            visionHandler.setNormalView();
        }
        else
        {
            //System.out.println("Aimed! Proceed with distance and RPM calculations");
        }
        turretPID.reset();
        turretSubsystem.turretRotate(0);
        
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}