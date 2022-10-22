package competition.subsystems.drive;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import competition.electrical_contract.ElectricalContract;
import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import edu.wpi.first.wpilibj.command.Command;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.subsystems.drive.BaseDriveSubsystem;
import xbot.common.injection.electrical_contract.CANTalonInfo;

@Singleton
public class DriveSubsystem extends BaseSubsystem {

    public final XCANTalon frontLeft;
    public final XCANTalon frontRight;
    private static boolean precision = false;

    private final double simulatedEncoderFactor = 256.0 * 39.3701; // 256 "ticks" per meter, and ~39 inches in a meter

    @Inject
    public DriveSubsystem(CommonLibFactory factory, ElectricalContract electricalContract) {
        log.info("Creating DriveSubsystem");
        // instantiate speed controllers and sensors here, save them as class members

        this.frontLeft = factory
                .createCANTalon(new CANTalonInfo(1, true, FeedbackDevice.CTRE_MagEncoder_Absolute, false,
                        simulatedEncoderFactor));
        this.frontRight = factory
                .createCANTalon(new CANTalonInfo(2, true, FeedbackDevice.CTRE_MagEncoder_Absolute, false,
                        simulatedEncoderFactor));

        frontLeft.createTelemetryProperties(this.getPrefix(), "frontLeft");
        frontRight.createTelemetryProperties(this.getPrefix(), "frontRight");

        this.register();

    }

    public void tankDrive(double leftPower, double rightPower) {

        // You'll need to take these power values and assign them to all of the motors.
        // As
        // an example, here is some code that has the frontLeft motor to spin according
        // to
        // the value of leftPower:
        if (precision == false) {
            frontLeft.simpleSet(leftPower);
            frontRight.simpleSet(rightPower);
        } else {
            frontLeft.simpleSet(leftPower / 2);
            frontRight.simpleSet(rightPower / 2);
        }

    }

    public static boolean getPrecisionMode() {
        return precision;
    }

    public static void setPrecisionMode(boolean p) {
        if (p == true) {
            precision = true;
        } else {
            precision = false;
        }

    }

    public void ArcadeDrive(double x, double y) {

        frontLeft.simpleSet(y-x);
        frontRight.simpleSet(y+x);
        

    }

    @Override
    public void periodic() {
        super.periodic();
        frontLeft.updateTelemetryProperties();
        frontRight.updateTelemetryProperties();
    }
}
