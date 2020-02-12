package frc.robot.auto.routines;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auto.routines.trajectories.BankLeft;
import frc.robot.commands.AimTurret;
import frc.robot.commands.ResetTurret;

public class FromLeft
{
    

    public static Command getAutoCommand()
    {
        
        SequentialCommandGroup fromLeft = new SequentialCommandGroup(
            new ParallelCommandGroup(BankLeft.getTrajectory(), new ResetTurret()),
            new AimTurret()
        );
        return fromLeft;
    }
}