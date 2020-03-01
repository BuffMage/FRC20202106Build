package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.auto.routines.DriveAndShoot;
import frc.robot.auto.routines.FromLeft;

public class AutoSelector
{

    

    public static Command getAutoCommand()
    {
        return FromLeft.getAutoCommand();
    }
}