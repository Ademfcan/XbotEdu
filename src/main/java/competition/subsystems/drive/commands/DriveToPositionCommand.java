package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import scala.util.control.Breaks;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.math.XYPair;

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    //public XCANTalon frontLeft;
    //public XCANTalon frontRight;
    private double goal;
    private boolean targetReached;
    double oldPos;
    double speed;

    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    public void setTargetPosition(double position) {
        // This method will be called by the test, and will give you a goal distance.
        // You'll need to remember this target position and use it in your calculations.
        goal = position;
    }

    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
     targetReached = false;
    }

    @Override
    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to move to the target position
        // - Hint: use pose.getPosition() to find out where you are
        // - Gets the robot stop (or at least be moving really really slowly) at the
        // target position

        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints!
        double position = pose.getPosition();
        
        
        double distanceAway = goal - position;
        speed = position - oldPos;

        double x = ((distanceAway) * .5- (speed *3));
        drive.frontLeft.simpleSet(x);
        drive.frontRight.simpleSet(x);
        
        if(distanceAway < 0.1){
            targetReached = true;
        }
        
         


        
       
        


        oldPos = position;
    }

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        if(targetReached == true){
            return true;
        }
        else{
            return false;
        }
    }

}
