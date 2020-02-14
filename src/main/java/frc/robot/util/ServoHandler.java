package frc.robot.util;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.Servo;

public class ServoHandler
{
    private static ServoHandler servoHandler = null;
    private static Servo hoodServo;
    private static DutyCycle positionFeedback;
    private static double currAngle;
    private static double actualAngle;
    private static double theta;
    private static double thetaP;
    private static double unitsPerRev;//Degrees in a circle
    private static double scaleFactor;
    //Max and min duty cycles of the pulses
    private static double minDC;
    private static double maxDC;

    private static double offset;
    private static double rotations;

    private static double prevSpeed;
    private static double currSpeed;

    private static double prevAngle;

    private static boolean flag;
    private static int cycles;

    public static ServoHandler getInstance()
    {
        if (servoHandler == null)
        {
            servoHandler = new ServoHandler();
        }
        return servoHandler;
    }

    private ServoHandler()
    {
        currAngle = 0;
        actualAngle = 0;
        prevAngle = 0;
        prevSpeed = 0;
        currSpeed = 0;
        offset = 0;
        minDC = 27;
        maxDC = 971;
        unitsPerRev = 360;
        scaleFactor = 1000;
        hoodServo = new Servo(0);
        positionFeedback = new DutyCycle(new DigitalInput(0));
        //Set bounds on our servo, since this Adafruit servo doesn't fit in with WPILIB servo libraries
        hoodServo.setBounds(1.72, 1.52, 1.5, 1.48, 1.28);
        theta = getRawTheta();
        thetaP = theta;
    }

    public void run()
    {
        prevAngle = actualAngle;
        prevSpeed = currSpeed;
        currSpeed = hoodServo.getSpeed();
        thetaP = theta;
        theta = getRawTheta();
        if (theta < 0)
        {
            theta = 0;
        }
        else if (theta > unitsPerRev - 1)
        {
            theta = unitsPerRev - 1;
        }
        rotations += getFixedTheta(thetaP, theta);
        /*
        currAngle = theta + rotations;
        if (getRate() > 180)
        {
            rotations -= 360;
        }
        else if (getRate() < -180)
        {
            rotations += 360;
        }*/
        actualAngle = theta + rotations;
        System.out.println(theta);
    }

    private boolean hasChangedDirection()
    {
        if (currSpeed > 0 && prevSpeed < 0)
        {
            return true;
        }
        else if (currSpeed < 0 && prevSpeed > 0)
        {
            return true;
        }
        return false;
    }

    private double getRawTheta()
    {
        return (unitsPerRev-1)-((((scaleFactor * positionFeedback.getOutput()) - minDC) * unitsPerRev)/(maxDC - minDC + 1));
    }

    private double getFixedTheta(double tP, double t)
    {
        //Maybe set to getRate instead?
        if (hoodServo.getSpeed() > 0 && (t < 180 && tP > 180))
        {
            return 360;
        }
        else if (hoodServo.getSpeed() < 0 && (t > 180 && tP < 180))
        {
            return -360;
        }
        return 0;
    }

    public double getAngle()
    {
        return actualAngle - offset;
    }

    public void setOffset(double offset)
    {
        ServoHandler.offset = offset;
    }

    public void setSpeed(double speed)
    {
        if (speed > 1)
        {
            speed = 1;
        }
        else if (speed < -1)
        {
            speed = -1;
        }
        hoodServo.setSpeed(speed);
    }

    public double getRate()
    {
        return ((currAngle - prevAngle) / .02);
    }
}