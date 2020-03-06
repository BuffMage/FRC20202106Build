package frc.robot.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.ControlConstants;

public class ControllerInputs
{
    private static ControllerInputs controllerInputs = null;

    private Joystick rightJoystick;
    private Joystick leftJoystick;
    private XboxController controller;
    private Joystick buttonBoard;

    public static ControllerInputs getInstance()
    {
        if(controllerInputs == null)
        {
            controllerInputs = new ControllerInputs();
        }

        return controllerInputs;
    }

    private ControllerInputs()
    {
        rightJoystick = new Joystick(ControlConstants.RIGHT_JOYSTICK_ID);
        leftJoystick = new Joystick(ControlConstants.LEFT_JOYSTICK_ID);
        controller = new XboxController(ControlConstants.CONTROLLER_ID);
        buttonBoard = new Joystick(ControlConstants.BUTTONBOARD_ID);
    }

    public XboxController getController()
    {
        return controller;
    }

    public Joystick getLeftJoystick()
    {
        return leftJoystick;
    }

    public Joystick getRightJoystick()
    {
        return rightJoystick;
    }

    public boolean getIntakeDown()
    {
        return leftJoystick.getRawButton(1);
    }

    public boolean getIntakeUp()
    {
        return leftJoystick.getRawButton(2);
    }

    public boolean getRunIntake()
    {
        return rightJoystick.getRawButton(1);
    }

    public boolean getRunConveyorForward()
    {
        return controller.getRawButton(6);
    }

    public boolean getRunConveyorBackward()
    {
        return controller.getRawButton(5);
    }

    public boolean getRunKicker()
    {
        return controller.getRawButton(7);
    }

    public boolean getAimAndShoot()
    {
        return controller.getRawButton(1);
    }


    //Temporary Climb Buttons

    public boolean getRunElevatorUp()
    {
        return buttonBoard.getRawButton(4);
    }

    public boolean getRunElevatorDown()
    {
        return buttonBoard.getRawButton(3);
    }

    public boolean getPutElevatorUp()
    {
        return buttonBoard.getRawButtonPressed(6);
    }

    public boolean getPutElevatorDown()
    {
        return buttonBoard.getRawButtonPressed(5);
    }

    public boolean getRunWinchForward()
    {
        return buttonBoard.getRawButton(1);
    }

    public boolean getRunWinchReverse()
    {
        return buttonBoard.getRawButton(2);
    }

}