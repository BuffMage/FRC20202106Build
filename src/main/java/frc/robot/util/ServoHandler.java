package frc.robot.util;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ServoHandler extends SubsystemBase
{
    private static ServoHandler servoHandler = null;
    private static Servo hoodServo;
    private static DutyCycle positionFeedback;
    private static int angle;
    private static int theta;
    private static int thetaP;
    private static int unitsPerRev;//Degrees in a circle
    private static int scaleFactor;
    public static double target;
    //Max and min duty cycles of the pulses
    private static int minDC;
    private static int maxDC;

    private static int offset;
    private static int rotations;

    private static int q2min;
    private static int q3max;

    private static int pidCounter;

    public static boolean isResetting;


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
        target = 0;
        pidCounter = 0;
        rotations = 0;
        unitsPerRev = 360;
        q2min = unitsPerRev / 4;
        q3max = q2min * 3;
        angle = 0;
        offset = 0;
        minDC = 27;
        maxDC = 971;
        scaleFactor = 1000;
        hoodServo = new Servo(0);
        positionFeedback = new DutyCycle(new DigitalInput(0));
        //Set bounds on our servo, since this Adafruit servo doesn't fit in with WPILIB servo libraries
        hoodServo.setBounds(1.72, 1.52, 1.5, 1.48, 1.28);
        theta = getRawTheta();
        thetaP = theta;
    }

    public void periodic()
    {
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
        rotations = rotations + getFixedTheta(thetaP, theta);

        if (rotations >= 0)
        {
            angle = (rotations * unitsPerRev) + theta;
        }
        else if (rotations < 0)
        {
            angle = ((rotations + 1) * unitsPerRev) - (unitsPerRev - theta);
        }
        if (!isResetting)
        {
            setAngle(target);
        }
        
    }
 
    private int getRawTheta()
    {
        Double d = (unitsPerRev-1)-((((scaleFactor * positionFeedback.getOutput()) - minDC) * unitsPerRev)/(maxDC - minDC + 1));
        return d.intValue();
    }

    private int getFixedTheta(int tP, int t)
    {
        if (t < q2min && tP > q3max)
        {
            return 1;
        }
        else if (tP < q2min && t > q3max)
        {
            return -1;
        }
        return 0;
    }

    public int getAngle()
    {
        return -(angle - offset);
    }

    public void setOffset(int offset)
    {
        ServoHandler.offset = offset;
    }

    public int getRawAngle()
    {
        return angle;
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

    public void setAngle(double setAngle)
    {
        if (setAngle > 430)
        {
            setAngle = 430;
        }
        else if (setAngle < 0)
        {
            setAngle = 0;
        }
        target = setAngle;
        double kP = .01;
        double pidOffset = 0;
        double errorAngle = setAngle - getAngle();
        double output = errorAngle * kP;

        if (output > .8)
        {
            output = .8;
        }
        else if (output < -.8)
        {
            output = -.8;
        }

        if (errorAngle > 0)
        {
            pidOffset = .1;
        }
        else if (errorAngle < 0)
        {
            pidOffset = -.1;
        }
        else
        {
            pidOffset = 0;
        }
        setSpeed(-(output + pidOffset));

    }

    public void resetPID()
    {
        pidCounter = 0;
    }

    public boolean atAngle()
    {

        if (target - getAngle() < 10 && target - getAngle() > -10)
        {
            pidCounter++;
        }
        else
        {
            pidCounter = 0;
        }
        
        if (pidCounter >= 50)
        {
            return true;
        }
        return false;
    }
}