package frc.robot.auto.routines;

import java.util.Set;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.auto.routines.trajectories.BankLeft;

public class FromLeft
{
    

    public static Command getAutoCommand()
    {
        
        SequentialCommandGroup fromLeft = new SequentialCommandGroup(
            BankLeft.getCommand()
        );
        return fromLeft;
    }
}