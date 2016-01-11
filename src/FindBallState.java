import com.cyberbotics.webots.controller.DistanceSensor;

/**
 * Created by Michael on 11.01.2016.
 *
 */
public class FindBallState extends State {

    public FindBallState(Robot robot) {
        super(robot);
    }

    @Override
    public boolean run() {
        getRobot().setWheelSpeed(1000, 1000);
        return true;
    }
}
