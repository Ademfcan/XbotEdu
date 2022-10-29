package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class TurnLeft90DegreesCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    double oldPos;
    double DegreesAway;

    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        
        double rotation = pose.getCurrentHeading().getDegrees();
        DegreesAway = rotation - 90;
        double speed = rotation - oldPos;

        double x = DegreesAway * 0.1 - speed * 2.5;
        drive.frontRight.simpleSet(x);
        drive.frontLeft.simpleSet(-x);


        oldPos = rotation;
        

        


        
    }
    @Override
    public boolean isFinished() {
        if(DegreesAway < 0.1){
            drive.frontLeft.simpleSet(0);
            drive.frontRight.simpleSet(0);
            return true;
        }
        return false;
        
    }

}
