import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.DistanceSensor;

public class BangBangFollowTheBallController extends DifferentialWheels {
    //setup values
    private static int TIME_STEP = 16;
    private static int MAX_SENSOR_VALUE = 200;
    private static int BACKWARDS = -100;
private static int MIN_SPEED = 0; // min. motor speed
    private static int MAX_SPEED = 1000; // max. motor speed
    private static int LIGHT_THRESHOLD = 200; //min light value for full stop

    //distance sensors
    private static int S_FRONT_LEFT = 0; // Sensor front left
    private static int S_FRONT_RIGHT = 1; // Sensor front right
    private static int S_MIDDLE_LEFT = 2; // Sensor middle left
    private static int S_MIDDLE_RIGHT = 3; // Sensor middle right

    private DistanceSensor [] _distanceSensors; // Array with all distance sensors

    public BangBangFollowTheBallController() {
        super();
        // get distance sensors and save them in array
        _distanceSensors = new DistanceSensor[] {
                getDistanceSensor("ps7"),
                getDistanceSensor("ps0"),
                getDistanceSensor("ps6"),
                getDistanceSensor("ps1"),
        };
        //enable the sensors
        for (int i = 0; i < 4; i++){
            _distanceSensors[i].enable(10);
        }
    }

    // User defined function for initializing and running
    public void run() {
        // Main loop:
        // Perform simulation steps of 64 milliseconds
        // and leave the loop when the simulation is over
        while (step(TIME_STEP) != -1) {
            driveForward();
            followBall();
        };
    }

    private void followBall() {
        if (ballIsClose()){

            DistanceSensor closestToObject = getClosestDistanceSensor();

            switch (closestToObject.getName()) {
                case "ps0":
                case "ps1":
                    driveRight();
                    break;
                case "ps6":
                case "ps7":
                    driveLeft();
                    break;
            }

        } else {
            driveForward();
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

    private boolean ballIsClose() {
        if ((_distanceSensors[S_FRONT_LEFT].getValue()  > 1500) || (_distanceSensors[S_FRONT_RIGHT].getValue() > 1500) || (_distanceSensors[S_MIDDLE_LEFT].getValue() > 300) || (_distanceSensors[S_MIDDLE_RIGHT].getValue() > 300)){
            return true;
        }
        return false;
    }

    private void turnHardLeft() {
        setSpeed(BACKWARDS, MAX_SPEED);
    }

    private void driveLeft() {
        setSpeed(MIN_SPEED, MAX_SPEED);
    }

    private void driveRight() {
        setSpeed(MAX_SPEED, MIN_SPEED);
    }

    private void turnHardRight() {
        setSpeed(MAX_SPEED, BACKWARDS);
    }

    private void driveForward() {
        setSpeed(MAX_SPEED, MAX_SPEED);
    }

    // This is the main program of your controller.
    // It creates an instance of your Robot subclass, launches its
    // function(s) and destroys it at the end of the execution.
    // Note that only one instance of Robot should be created in
    // a controller program.
    // The arguments of the main function can be specified by the
    // "controllerArgs" field of the Robot node
    public static void main(String[] args) {
        BangBangFollowTheBallController controller = new BangBangFollowTheBallController();
        controller.run();
    }
}