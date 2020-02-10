package frc.robot.auto.routines;

import java.util.Set;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class FromLeft
{
    

    public static Command getAutoCommand()
    {
        Command command = new Command(){
        
            @Override
            public Set<Subsystem> getRequirements() {
                // TODO Auto-generated method stub
                return null;
            }
        };
        return command;
    }
}