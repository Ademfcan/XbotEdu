package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class DriveToOrientationCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    double oldPos;
    double DegreesAway;
    double goal;
    double StartRot;
    double rotation;
    double head;
    boolean done = false;
    boolean started = false;

    @Inject
    public DriveToOrientationCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    public void setTargetHeading(double heading) {
        // This method will be called by the test, and will give you a goal heading.
        // You'll need to remember this target position and use it in your calculations.
        head = heading;
    }

    @Override
    public void initialize() {
        StartRot= pose.getCurrentHeading().getDegrees();
        rotation = pose.getCurrentHeading().getDegrees();
        goal = head;
    }

    @Override
    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to turn to the target orientation
        // - Gets the robot stop (or at least be moving really really slowly) at the
        // target position

        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints!
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
        if(speed > 2 || speed < 0){
            started = true;
        }
        drive.frontRight.simpleSet(x);
        drive.frontLeft.simpleSet(-x);


        oldPos = rotation;
        if(Math.abs(DegreesAway) < 0.1){
            done = true;
        }
    }

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        if(done == true ){
            return true;
        }
        return false;
    }
}
