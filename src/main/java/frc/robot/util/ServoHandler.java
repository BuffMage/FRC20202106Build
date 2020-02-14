package frc.robot.util;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.Servo;

public class ServoHandler
{
    private static ServoHandler servoHandler = null;
    private static Servo hoodServo;
    private static DutyCycle positionFeedback;
    private static int angle;
    private static int theta;
    private static int thetaP;
    private static int unitsPerRev;//Degrees in a circle
    private static int scaleFactor;
    //Max and min duty cycles of the pulses
    private static int minDC;
    private static int maxDC;

    private static int offset;
    private static int rotations;

    private static int q2min;
    private static int q3max;


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
        rotations = 0;
        q2min = unitsPerRev / 4;
        q3max = q2min * 3;
        angle = 0;
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

        if (rotations >= 0)
        {
            angle = (rotations * unitsPerRev) + theta;
        }
        else if (rotations < 0)
        {
            angle = ((rotations + 1) * unitsPerRev) - (unitsPerRev - theta);
        }
        thetaP = theta;
    }

    private int getRawTheta()
    {
        Double d = (unitsPerRev-1)-((((scaleFactor * positionFeedback.getOutput()) - minDC) * unitsPerRev)/(maxDC - minDC + 1));
        return d.intValue();
    }

    private int getFixedTheta(double tP, double t)
    {
        if (theta < q2min && thetaP > q3max)
        {
            return 1;
        }
        else if (thetaP < q2min && theta > q3max)
        {
            return -1;
        }
        return 0;
    }

    public int getAngle()
    {
        return angle - offset;
    }

    public void setOffset(int offset)
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
}