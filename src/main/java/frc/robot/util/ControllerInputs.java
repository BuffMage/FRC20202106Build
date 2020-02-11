package frc.robot.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;

public class ControllerInputs
{
    private ControllerInputs controllerInputs = null;

    private Joystick rightJoystick;
    private Joystick leftJoystick;
    private XboxController controller;

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
        controller = new XboxController(Constants.CONTROLLER_ID);
    }

    /**
     *Returns the Y of the right joystick
     */
    public double getRightY()
    {
        return rightJoystick.getY();
    }

    /**
     *Returns the Y of the left joystick
     */
    public double getLeftY()
    {
        return leftJoystick.getY();
    }

    /**
     * Returns X axis of controller
     */    
    public double getControllerX()
    {
        return controller.getX();
    }

    /**
     * Returns Z axis of controller
     */
    public double getControllerZ()
    {
        return controller.getRawAxis(2);
    }

    

}