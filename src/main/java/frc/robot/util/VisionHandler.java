package frc.robot.util;

public class VisionHandler
{
    private VisionHandler visionHandler = null;

    public VisionHandler getInstance()
    {
        if (visionHandler == null)
        {
            visionHandler = new VisionHandler();
        }
        return visionHandler;
        
    }

    private VisionHandler()
    {
        //Initialize NetworkTables and keys
    }

    
    /**
     * Returns the target's current X position, which is from -30 to 30 degrees
     * @return
     */
    public double getX()
    {
        return 0;
    }

    /**
     * Returns the target's current Y position, which is from -25 to 25 degrees
     * @return 
     */
    public double getY()
    {
        return 0;
    }
    /**
     * The target's distance from the shooter
     * @return The distance in inches
     */
    public double getDistance()
    {
        return 0;
    }
}