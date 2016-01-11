import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.DistanceSensor;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Michael on 11.01.2016.
 *
 */
public class Robot extends DifferentialWheels {

    private DistanceSensor[] _distanceSensors;

    private static int TIME_STEP = 16;
    private double _leftWheelSpeed = 1000;
    private double _rightWheelSpeed = 1000;

    private List<State> _stateList;

    public Robot () {
        super();
        activateDistanceSensors();
        initStates();
    }

    private void activateDistanceSensors() {
        _distanceSensors = new DistanceSensor[] {
                getDistanceSensor("ps5"), //left
                getDistanceSensor("ps6"), //middle_left
                getDistanceSensor("ps7"), //front left
                getDistanceSensor("ps0"), //front right
                getDistanceSensor("ps1"), //middle right
                getDistanceSensor("ps2"), //right
                getDistanceSensor("ps3"), //right behind
                getDistanceSensor("ps4") //left behind
        };

        //enable the sensors
        for (int i = 0; i < _distanceSensors.length; i++){
            _distanceSensors[i].enable(10);
        }
    }

    private void initStates() {
        _stateList = new LinkedList<>();
        _stateList.add(new LeaveBallState(this));
        _stateList.add(new PushBallToWallState(this));
        _stateList.add(new FindBallState(this));
    }

    public void run() {
        // Main loop:
        // Perform simulation steps of 16 milliseconds
        // and leave the loop when the simulation is over
        int currentState = 0;
        while (step(TIME_STEP) != -1) {

            for (int i = 0; i < _stateList.size(); ++i) {
                if (_stateList.get(i).run()) {
                    setSpeed(_leftWheelSpeed, _rightWheelSpeed);
                    i = _stateList.size();
                }
            }
        }
    }

    public DistanceSensor[] getDistanceSensors() {
        return _distanceSensors;
    }

    public void setWheelSpeed(double leftWheelSpeed, double rightWheelSpeed) {
        _leftWheelSpeed = leftWheelSpeed;
        _rightWheelSpeed = rightWheelSpeed;
    }
}
