package frc.robot.auto.routines;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auto.routines.trajectories.BackAndShoot;
import frc.robot.auto.routines.trajectories.BankLeft;
import frc.robot.auto.routines.trajectories.DriveForward;
import frc.robot.auto.routines.trajectories.FromForwardLeft;
import frc.robot.auto.routines.trajectories.GoGetThree;
import frc.robot.auto.routines.trajectories.GoShootThree;
import frc.robot.commands.InvertDrivetrain;
import frc.robot.commands.ResetHood;
import frc.robot.commands.ResetPose;
import frc.robot.commands.ResetTurret;
import frc.robot.commands.RunIntakeContinuous;
import frc.robot.commands.TurnTurretTo;
import frc.robot.commands.UninvertDrivetrain;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.TurretSubsystem;



public class SixBallAuto
{
    

    public static Command getAutoCommand()
    {
        /*
        SequentialCommandGroup sixBallAuto = new SequentialCommandGroup(
            new ResetPose(),
            new InvertDrivetrain(),
            BackAndShoot.getTrajectory(),
            new UninvertDrivetrain(),
            GoGetThree.getTrajectory(),
            new InvertDrivetrain(),
            GoShootThree.getTrajectory()
        );
        */
        SequentialCommandGroup sixBallAuto = new SequentialCommandGroup(
            new InvertDrivetrain(),
            new ParallelCommandGroup(
                new SequentialCommandGroup(new InstantCommand(() -> TurretSubsystem.getInstance().resetTurretEncoder()),
                    new ResetHood(),
                    new ResetPose(),
                    new TurnTurretTo(-30)),
                BackAndShoot.getTrajectory()),
            TurretSubsystem.getInstance().aimAndShootBetter(),
            new InstantCommand(() -> IntakeSubsystem.getInstance().dropIntake()),
            new UninvertDrivetrain(),
            new ParallelDeadlineGroup(
                GoGetThree.getTrajectory(),
                new RunIntakeContinuous()),
            new InvertDrivetrain(),
            GoShootThree.getTrajectory(),
            TurretSubsystem.getInstance().aimAndShootBetter());
        
        return sixBallAuto;
    }
}