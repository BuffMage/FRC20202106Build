package frc.robot.auto.routines;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auto.routines.trajectories.BankLeft;
import frc.robot.auto.routines.trajectories.DriveForward;
import frc.robot.commands.ResetHood;
import frc.robot.commands.ResetPose;
import frc.robot.commands.ResetTurret;
import frc.robot.commands.TurnTurretTo;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.TurretSubsystem;

public class DriveAndShoot
{
    
    public static Command getAutoCommand()
    {

        SequentialCommandGroup driveAndShoot = new SequentialCommandGroup(
            new ParallelCommandGroup(
                new SequentialCommandGroup(
                    new ParallelCommandGroup(new ResetTurret(), new ResetHood(), new ResetPose()),
                    new TurnTurretTo(100)
                ),
                DriveForward.getTrajectory()
            ),
            TurretSubsystem.getInstance().aimAndShoot()
        );
        
        return driveAndShoot;
    }
}