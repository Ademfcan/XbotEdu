package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import scala.util.control.Breaks;
import xbot.common.controls.actuators.XCANTalon;

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    //public XCANTalon frontLeft;
    //public XCANTalon frontRight;
    private double goal;
    private boolean targetReached;

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
        double accelerationThreshold = 2.1;
        
        if(position >= 0 && position <= accelerationThreshold && targetReached == false){
            drive.frontLeft.simpleSet(1);
            drive.frontRight.simpleSet(1);
        }
        else if(position > goal-2 && position< goal && targetReached == false){
            drive.frontLeft.simpleSet(-1);
            drive.frontRight.simpleSet(-1);
        }
        else if(position >= goal || targetReached == true){

            
            targetReached = true;
            drive.frontLeft.simpleSet(0);
            drive.frontRight.simpleSet(0);

        }
        else{
            drive.frontLeft.simpleSet(0);
            drive.frontRight.simpleSet(0);
        }


        
    }

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        return false;
    }

}
