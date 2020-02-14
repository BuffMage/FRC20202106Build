package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.LimitSwitch;

public class LimitSwitchSubsystem extends SubsystemBase
{
    private static LimitSwitchSubsystem limitSwitchSubsystem = null;
    public static LimitSwitch turretSwitch;

    public static LimitSwitchSubsystem getInstance()
    {
        if (limitSwitchSubsystem == null)
        {
            limitSwitchSubsystem = new LimitSwitchSubsystem();
        }
        return limitSwitchSubsystem;
    }

    private LimitSwitchSubsystem()
    {
        turretSwitch = new LimitSwitch(0);
    }

    @Override
    public void periodic()
    {
        turretSwitch.update();
    }
}