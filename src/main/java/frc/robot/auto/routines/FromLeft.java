package frc.robot.auto.routines;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auto.routines.trajectories.BankLeft;
import frc.robot.commands.AimTurret;
import frc.robot.commands.InvertDrivetrain;
import frc.robot.commands.ResetHood;
import frc.robot.commands.ResetPose;
import frc.robot.commands.ResetTurret;
import frc.robot.commands.TestDriveForward;
import frc.robot.commands.UninvertDrivetrain;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.TurretSubsystem;


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