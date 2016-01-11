import com.cyberbotics.webots.controller.DistanceSensor;

/**
 * Created by Michael on 11.01.2016.
 *
 */
public class LeaveBallState extends State {

    DistanceSensor[] _distanceSensors;

    private static int S_LEFT = 0;  //sensor left
    private static int S_MIDDLE_LEFT = 1; // Sensor middle left
    private static int S_MIDDLE_RIGHT = 4; // Sensor middle right
    private static int S_RIGHT = 5; //sensor right

    private static int DISTANCE_TO_TURN = 500;

    public LeaveBallState(Robot robot) {
        super(robot);
        _distanceSensors = getRobot().getDistanceSensors();
    }

    @Override
    public boolean run() {

        double sLeft = _distanceSensors[S_LEFT].getValue();
        double sMiddleLeft = _distanceSensors[S_MIDDLE_LEFT].getValue();
        double sMiddleRight = _distanceSensors[S_MIDDLE_RIGHT].getValue();
        double sRight = _distanceSensors[S_RIGHT].getValue();

        if (sLeft > DISTANCE_TO_TURN || sMiddleLeft > DISTANCE_TO_TURN){
            getRobot().setWheelSpeed(1000, -1000);
            System.out.println("turn away to right");
            return true;
        } else if (sRight > DISTANCE_TO_TURN || sMiddleRight > DISTANCE_TO_TURN) {
            getRobot().setWheelSpeed(-1000, 1000);
            System.out.println("turn away to left");
            return true;
        } else {
            return false;
        }
    }
}
