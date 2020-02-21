package frc.robot.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants.VisionConstants;

public class VisionHandler
{
    private static VisionHandler visionHandler = null;
    private static NetworkTable table;
    private static NetworkTableEntry tx, ty;
    private static double x, y;

    public static VisionHandler getInstance()
    {
        if (visionHandler == null)
        {
            visionHandler = new VisionHandler();
        }
        return visionHandler;
        
    }

    private VisionHandler()
    {
        //Initialize NetworkTables and entries
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        x = 0;
        y = 0;
    }

    
    /**
     * Returns the target's current X position, which is from -30 to 30 degrees
     * @return Position in degrees
     */
    public double getX()
    {
        return x;
    }

    /**
     * Returns the target's current Y position, which is from -25 to 25 degrees
     * @return Position in degrees
     */
    public double getY()
    {
        return y;
    }
    /**
     * The target's distance from the shooter
     * @return The distance in meters
     */
    public double getDistance()
    {
        return VisionConstants.deltaHeight/(Math.tan(Math.toRadians(VisionConstants.camAngle + x)));
    }

    public void run()
    {
        x = tx.getDouble(0);
        y = ty.getDouble(0);
    }

    public void setNormalView()
    {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
    }
}