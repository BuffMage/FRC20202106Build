package frc.robot.util;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitch
{
    private DigitalInput limitSwitch;
    private boolean previous;
    private boolean current;

    public LimitSwitch(int channel)
    {
        limitSwitch = new DigitalInput(channel);
        previous = false;
        current = false;
    }

    public void update()
    {
        previous = current;
        current = limitSwitch.get();
    }

    public boolean whenPressed()
    {
        if (previous != current && current == true)
        {
            return true;
        }
        return false;
    }

    public boolean whenReleased()
    {
        if (previous != current && current == false)
        {
            return true;
        }
        return false;
    }

    public boolean get()
    {
        return !current;
    }
}