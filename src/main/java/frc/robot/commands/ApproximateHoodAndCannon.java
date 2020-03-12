//Do not use this as an actual command, copy this code into another command file and change class name
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TurretSubsystem;
import frc.robot.util.ParametricCalculator;
import frc.robot.util.ServoHandler;
import frc.robot.util.VisionHandler;


public class ApproximateHoodAndCannon extends CommandBase
{
    private static TurretSubsystem m_turretSubsystem;
    private static VisionHandler m_visionHandler;
    private static ServoHandler m_servoHandler;
    private static double velocityToRPMConversionFactor = 4500 / 7.55;

    private static double servoRange = 435;
    private static double hoodAngleRange = 20;
    private static double servoDegreesPerHoodDegree = servoRange / hoodAngleRange;
    private static double initialHoodAngle = 80;
    private static double angleToSetTo = 0;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public ApproximateHoodAndCannon()
    {
        m_turretSubsystem = TurretSubsystem.getInstance();
        m_visionHandler = VisionHandler.getInstance();
        m_servoHandler = ServoHandler.getInstance();
    }
    
    @Override
    public void initialize()
    {
        m_turretSubsystem.cannonSpinPID(ParametricCalculator.getInitialVelocity(m_visionHandler.getDistance()) * velocityToRPMConversionFactor);
        angleToSetTo = initialHoodAngle - ParametricCalculator.getHoodAngle(m_visionHandler.getDistance());
        m_servoHandler.setAngle(angleToSetTo * servoDegreesPerHoodDegree);
        
    }

    @Override
    public void execute()
    {
        initialize();
    }

    @Override
    public void end(boolean interrupted)
    {
        m_servoHandler.resetPID();
        m_turretSubsystem.resetCannonPID();
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}