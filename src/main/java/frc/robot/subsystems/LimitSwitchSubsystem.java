package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.LimitSwitch;

public class LimitSwitchSubsystem extends SubsystemBase
{
    private static LimitSwitchSubsystem limitSwitchSubsystem = null;
    public static LimitSwitch turretSwitch;
    public static LimitSwitch hoodSwitch;
    public static LimitSwitch conveyorSwitch;

    public static void initialize()
    {
        if (limitSwitchSubsystem == null)
        {
            limitSwitchSubsystem = new LimitSwitchSubsystem();
        }
        
    }

    private LimitSwitchSubsystem()
    {
        turretSwitch = new LimitSwitch(2);
        hoodSwitch = new LimitSwitch(1);
        conveyorSwitch = new LimitSwitch(3);
    }

    @Override
    public void periodic()
    {
        turretSwitch.update();
        hoodSwitch.update();
        conveyorSwitch.update();
    }
}