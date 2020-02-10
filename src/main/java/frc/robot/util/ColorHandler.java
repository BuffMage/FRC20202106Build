package frc.robot.util;

public class ColorHandler
{
    private ColorHandler colorHandler = null;
    

    public ColorHandler getInstance()
    {
        if(colorHandler == null)
        {
            colorHandler = new ColorHandler();
        }

        return colorHandler;
    }

    private ColorHandler()
    {
        
    }
         
    /**
    Gets color currently being read by the sensor 
    */
    public String getColor()
    {
        return "";
    }

}