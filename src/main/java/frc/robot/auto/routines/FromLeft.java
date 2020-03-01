package frc.robot.auto.routines;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auto.routines.trajectories.BankLeft;
import frc.robot.auto.routines.trajectories.DriveForward;
import frc.robot.auto.routines.trajectories.FromForwardLeft;
import frc.robot.commands.InvertDrivetrain;
import frc.robot.commands.ResetHood;
import frc.robot.commands.ResetPose;
import frc.robot.commands.ResetTurret;
import frc.robot.commands.TurnTurretTo;
import frc.robot.subsystems.TurretSubsystem;



public class FromLeft
{
    

    public static Command getAutoCommand()
    {
        
        SequentialCommandGroup fromLeft = new SequentialCommandGroup(
            new ParallelCommandGroup(
                new SequentialCommandGroup(
                    new ParallelCommandGroup(new ResetTurret(), new ResetHood(), new ResetPose()),
                    new TurnTurretTo(50)
                ),
                FromForwardLeft.getTrajectory()
            ),
            TurretSubsystem.getInstance().aimAndShoot()
        );
        
        return fromLeft;
    }
}