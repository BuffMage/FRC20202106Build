package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.util.ParametricCalculator;
import frc.robot.util.ServoHandler;
import frc.robot.util.VisionHandler;


public class SetHoodAngle extends CommandBase
{
    //The range of our servo in degrees
    private static double servoRange = 430;
    private static double hoodAngleRange = 30;
    private static double servoDegreesPerHoodDegree = servoRange / hoodAngleRange;
    private static double initialHoodAngle = 30;

    private static VisionHandler visionHandler;
    private static ServoHandler servoHandler;

    private static boolean isFinished;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public SetHoodAngle()
    {
        isFinished = false;
        visionHandler = VisionHandler.getInstance();
        servoHandler = ServoHandler.getInstance();
    }
    
    @Override
    public void initialize()
    {
        servoHandler.setAngle((ParametricCalculator.getHoodAngle(visionHandler.getDistance()) - initialHoodAngle) * servoDegreesPerHoodDegree);
    }

    @Override
    public void execute()
    {
        if (servoHandler.atAngle())
        {
            isFinished = true;
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        if (interrupted)
        {
            System.out.println("Warning! Hood angle not set");
        }
        else
        {
            System.out.println("Hood angle set!");
        }
    }

    @Override
    public boolean isFinished()
    {
        return isFinished;
    }
}