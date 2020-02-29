package frc.robot.auto.routines;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auto.routines.trajectories.BankLeft;
import frc.robot.commands.ResetPose;



public class FromLeft
{
    

    public static Command getAutoCommand()
    {
        
        SequentialCommandGroup fromLeft = new SequentialCommandGroup(
            new ResetPose(),
            BankLeft.getTrajectory()
        );
        
        return fromLeft;
    }
}