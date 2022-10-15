package competition.subsystems.drive;

import org.junit.Test;
import xbot.common.controls.actuators.XCANTalon;

import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import xbot.common.command.BaseCommand;
import xbot.common.math.XYPair;

public class TankDriveTest extends BaseDriveTest {
    public XCANTalon frontLeft;
    public XCANTalon frontRight;
    @Test
    
    public void test() {
        final BaseCommand command = injector.getInstance(TankDriveWithJoysticksCommand.class);


        // Call the TankDriveWithJoysticksCommand initialize once
        command.initialize();

        // Call execute without having moved the joysticks yet. This means the joystick
        // axes are at 0,
        // so the motors should be at 0 power (stopped).
        command.execute();
        assertDrive(0, 0, "Expect Motors initially 0");

        // Push the left and right joystick to fully forward (+1). This should make all
        // motors go to +1,
        // also knows as "full forward"
        
        // Push the left joystick fully backward (-1) and the right joystick fully
        // forward (1). This
        // would make a tank turn - left!
        gamepad.setLeftStick(new XYPair(0, -1.0));
        gamepad.setRightStick(new XYPair(0, 1.0));
        command.execute();
        assertDrive(-1.0, 1.0, "Expect Motors are all forward when both joysticks are completely forward");

        //frontLeft.simpleSet(get);

        //turn right
        gamepad.setLeftStick(new XYPair(0, 1.0));
        gamepad.setRightStick(new XYPair(0, -1.0));
        command.execute();
        assertDrive(1.0, -1.0);




        //move back
        gamepad.setLeftStick(new XYPair(0, -1.0));
        gamepad.setRightStick(new XYPair(0, -1.0));
        command.execute();
        assertDrive(-1.0, -1.0);
    }
}
