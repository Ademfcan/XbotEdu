package competition.subsystems.drive.commands;

import com.google.inject.Inject;
import com.google.inject.spi.DisableCircularProxiesOption;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import edu.wpi.first.hal.simulation.RoboRioDataJNI;

public class TurnLeft90DegreesCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    double oldPos;
    double DegreesAway;
    double goal;
    double StartRot;
    double rotation;
    boolean done = false;

    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    @Override
    public void initialize() {
        StartRot= pose.getCurrentHeading().getDegrees();
        rotation = pose.getCurrentHeading().getDegrees();
        goal  = StartRot + 90;
        
    }

    @Override
    public void execute() {
        
        rotation = pose.getCurrentHeading().getDegrees();

        DegreesAway = goal - rotation;
        if(goal > 180){
            goal -= 360;
            DegreesAway += 360;
            
        }
        if(DegreesAway > 180){
            DegreesAway -=360;
        }
        if(DegreesAway <-180){
            DegreesAway += 360;
        }


        double speed = rotation - oldPos;

        double x = DegreesAway * 0.1 - speed * 2.5;
        drive.frontRight.simpleSet(x);
        drive.frontLeft.simpleSet(-x);


        oldPos = rotation;
        if(DegreesAway < 0.1){
            done = true;
        }
        

        


        
    }
    @Override
    public boolean isFinished() {
        if(done == true){

            return true;
        }
        return false;
        
    }

}
