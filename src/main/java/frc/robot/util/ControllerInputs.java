package frc.robot.util;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants;

public class ControllerInputs
{
    private ControllerInputs controllerInputs = null;

    private Joystick rightJoystick;
    private Joystick leftJoystick;

    public ControllerInputs getInstance()
    {
        if(controllerInputs == null)
        {
            controllerInputs = new ControllerInputs();
        }

        return controllerInputs;
    }

    private ControllerInputs()
    {
        rightJoystick = new Joystick(Constants.RIGHT_JOYSTICK_ID);
        leftJoystick = new Joystick(Constants.LEFT_JOYSTICK_ID);
        
    }

    /**
     *Returns the Y of the right joystick
     */
    public double getRightY()
    {
        return 0.0;
    }

    /**
     *Returns the Y of the left joystick
     */
    public double getLeftY()
    {
        return 0.0;
    }

    /**
     * Returns X axis of controller
     */    
    public double getTurnShooter()
    {
        return 0.0;
    }
}