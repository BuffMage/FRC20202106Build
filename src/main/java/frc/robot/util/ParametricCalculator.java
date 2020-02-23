package frc.robot.util;

import frc.robot.Constants.CannonPIDConstants;

public class ParametricCalculator
{
    private static double vertVel = 2 * Math.sqrt(4.9 * CannonPIDConstants.kCannonToTargetHeight);
    private static double horVel = 0;

    /**
     * Calculates the required hood angle based on our distance from the target
     * @param distance The distance in meters
     * @return The hood angle in degrees
     */
    public static double getHoodAngle(double distance)
    {
        calculateHorizontalVelocity(distance);
        return Math.toDegrees(Math.tanh(vertVel / horVel));
    }

    /**
     * Calculates the required initial velocity needed to accelerate the power cell
     * @param distance The distance from the target in meters
     * @return The initial velocity in meters per second
     */
    public static double getInitialVelocity(double distance)
    {
        calculateHorizontalVelocity(distance);
        return Math.sqrt(Math.pow(vertVel, 2) + Math.pow(horVel, 2));
    }

    private static void calculateHorizontalVelocity(double distance)
    {
        horVel = (9.8 * distance) / vertVel;
    }
}