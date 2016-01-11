import com.cyberbotics.webots.controller.DistanceSensor;

/**
 * Created by Michael on 11.01.2016.
 *
 */
public class PushBallToWallState extends State {

    private DistanceSensor[] _distanceSensors;
    private static int S_FRONT_LEFT = 2; // Sensor front left
    private static int S_FRONT_RIGHT = 3; // Sensor front right

    public PushBallToWallState(Robot robot) {
        super(robot);
        _distanceSensors = robot.getDistanceSensors();
    }

    @Override
    public boolean run() {

        double sFrontLeft = _distanceSensors[S_FRONT_LEFT].getValue();
        double sFrontRight = _distanceSensors[S_FRONT_RIGHT].getValue();

        if (sFrontLeft > 500 || sFrontRight > 500) {
            DistanceSensor closestToObject = getClosestDistanceSensor();

            switch (closestToObject.getName()) {
                case "ps0":
                case "ps1":
                    getRobot().setWheelSpeed(1000, 0);
                    return true;
                case "ps6":
                case "ps7":
                    getRobot().setWheelSpeed(0, 1000);
                    return true;
                default:
                    return false;
            }

        } else {
            return false;
        }

    }

    private DistanceSensor getClosestDistanceSensor() {
        DistanceSensor tempSensor = _distanceSensors[0];

        for (DistanceSensor distanceSensor : _distanceSensors) {
            if (tempSensor.getValue() < distanceSensor.getValue()) {
                tempSensor = distanceSensor;
            }
        }
        return tempSensor;
    }

}
