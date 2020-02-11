package frc.robot.util;

import frc.robot.Constants;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class ColorHandler
{
    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    private ColorHandler colorHandler = null;
    private I2C.Port i2cPort;
    private ColorSensorV3 m_colorSensor;
    private ColorMatch m_colorMatcher;

    
    
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
        i2cPort = I2C.Port.kOnboard;
        m_colorSensor = new ColorSensorV3(i2cPort);
        m_colorMatcher = new ColorMatch();
    }
         
    /**
    Gets color currently being read by the sensor 
    */
    public String getColor()
    {
        Color detectedColor = m_colorSensor.getColor();
        String colorString;
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget) 
        {
            colorString = "Blue";
        } 
        else if (match.color == kRedTarget) 
        {
            colorString = "Red";
        }
        else if (match.color == kGreenTarget) 
        {
            colorString = "Green";
        } 
        else if (match.color == kYellowTarget) 
        {
            colorString = "Yellow";
        } 
        else 
        {
            colorString = "Unknown";
        }

        return colorString;
    }

}