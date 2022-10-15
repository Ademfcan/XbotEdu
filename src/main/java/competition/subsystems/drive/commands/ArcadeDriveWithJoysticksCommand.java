package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import xbot.common.controls.actuators.XCANTalon;
import competition.subsystems.drive.DriveSubsystem;
import competition.operator_interface.OperatorInterface;

public class ArcadeDriveWithJoysticksCommand extends BaseCommand {

    DriveSubsystem drive;
    OperatorInterface operatorInterface;
    public XCANTalon frontLeft;
    public XCANTalon frontRight;
    @Inject
    public ArcadeDriveWithJoysticksCommand(DriveSubsystem driveSubsystem, OperatorInterface oi) {
        drive = driveSubsystem;
        operatorInterface = oi;
        this.addRequirements(drive);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute(){
        double leftX =  operatorInterface.gamepad.getLeftVector().x;
        double leftY =  operatorInterface.gamepad.getLeftVector().y;
        drive.ArcadeDrive(leftX, leftY);
    }

}
