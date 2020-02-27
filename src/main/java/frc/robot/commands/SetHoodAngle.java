package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.util.ControllerInputs;
import frc.robot.util.ParametricCalculator;
import frc.robot.util.ServoHandler;
import frc.robot.util.VisionHandler;


public class SetHoodAngle extends CommandBase
{
    //The range of our servo in degrees
    private static double servoRange = 435;
    private static double hoodAngleRange = 20;
    private static double servoDegreesPerHoodDegree = servoRange / hoodAngleRange;
    private static double initialHoodAngle = 80;
    private static double angleToSetTo = 0;

    //private static ControllerInputs controllerInputs;
    private static VisionHandler visionHandler;
    private static ServoHandler servoHandler;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    public SetHoodAngle()
    {
        visionHandler = VisionHandler.getInstance();
        servoHandler = ServoHandler.getInstance();
        //controllerInputs = ControllerInputs.getInstance();
    }
    
    @Override
    public void initialize()
    {
        angleToSetTo = initialHoodAngle - ParametricCalculator.getHoodAngle(visionHandler.getDistance());
        servoHandler.setAngle(angleToSetTo * servoDegreesPerHoodDegree);
        servoHandler.resetPID();
    }

    @Override
    public void execute()
    {
        //servoHandler.setAngle((ParametricCalculator.getHoodAngle(visionHandler.getDistance()) - initialHoodAngle) * servoDegreesPerHoodDegree);
        /*
        if (servoHandler.atAngle())
        {
            isFinished = true;
        }
        if (controllerInputs.getRightJoystick().getRawButton(1))
        {
            isFinished = true;
        }*/

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
        return servoHandler.atAngle();
    }
}