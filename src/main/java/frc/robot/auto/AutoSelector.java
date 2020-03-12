package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.auto.routines.DriveAndShoot;
import frc.robot.auto.routines.SixBallAuto;
import frc.robot.auto.routines.trajectories.BankLeft;

public class AutoSelector
{

    

    public static Command getAutoCommand()
    {
        //return DriveAndShoot.getAutoCommand();
        return DriveAndShoot.getAutoCommand();
    }
}