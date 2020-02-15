/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class DriveConstants
    {
        public static final int kLeftMotor1Port = 2;
        public static final int kLeftMotor2Port = 5;
        public static final int kRightMotor1Port = 1;
        public static final int kRightMotor2Port = 3;
        
        public static final boolean kLeftEncoderReversed = false;
        public static final boolean kRightEncoderReversed = true;

        public static final double kTrackwidthMeters = 0.6096;
        public static final DifferentialDriveKinematics kDriveKinematics =
            new DifferentialDriveKinematics(kTrackwidthMeters);

        public static final double kEncoderCPR = 42 * 11.21;
        public static final double kWheelDiameterMeters = 0.1524;
        public static final double kEncoderDistancePerPulse =
            (kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;

        public static boolean kGyroReversed = true;
        public static boolean kInvertedDrivetrain = false;

        public static final double ksVolts = 0.09;
        public static final double kvVoltSecondsPerMeter = 2.88;
        public static final double kaVoltSecondsSquaredPerMeter = 0.353;

        public static final double kPDriveVel = 2.5;

        
        public static final DifferentialDriveVoltageConstraint autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(ksVolts, 
            kvVoltSecondsPerMeter, 
            kaVoltSecondsSquaredPerMeter), 
            kDriveKinematics, 
            10);
    }

    public static final class AutoConstants
    {
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
        public static final double kRamseteB = 2.0;
        public static final double kRamseteZeta = .7;
    }

    public static final class ControlConstants
    {
        public static final int RIGHT_JOYSTICK_ID = 0;
        public static final int LEFT_JOYSTICK_ID = 1;
        public static final int CONTROLLER_ID = 2;
    }

    public static final class SystemConstants
    {
        public static final int kTurretMotorID = 0;
    }

    public static final class TurretPIDConstants
    {
        public static final double kP = 0.0;
        public static final double kI = 0.0;
        public static final double kD = 0.0;
    }

    public static final class VisionConstants
    {
        public static final double deltaHeight = 4-.3;//Difference in height between the target's height and the height of the camera.
        public static final double camAngle = 45;//Camera tilt angle in degrees;
    }
}
