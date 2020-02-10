package frc.robot.util;

// Testing to see if this is working

public class ControllerInputs
{
    private ControllerInputs controllerInputs = null;


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

    }

    /**
     *Returns the Y of the right joystick
     */
    private double getRightY()
    {
        return 0.0;
    }

    /**
     *Returns the Y of the left joystick
     */
    private double getLeftY()
    {
        return 0.0;
    }

    /**
     * Returns X axis of controller
     */    
    private double getTurnShooter()
    {
        return 0.0;
    }
}