import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.DistanceSensor;

public class BangBangWallRideController extends DifferentialWheels {

    //setup values
    private static int TIME_STEP = 16;
    private static int BACKWARDS = -100;
    private static int MIN_SPEED = 0; // min. motor speed
    private static int MAX_SPEED = 1000; // max. motor speed
    private static int FOUND_WALL_DISTANCE = 400; //distance value when wall should be found

    //distance sensors
    private static int S_FRONT_LEFT = 0; // Sensor front left
    private static int S_FRONT_RIGHT = 1; // Sensor front right
    private static int S_MIDDLE_LEFT = 2; // Sensor middle left
    private static int S_MIDDLE_RIGHT = 3; // Sensor middle right
    private static int S_LEFT = 4;
    private static int S_RIGHT = 5;

    private DistanceSensor[] _distanceSensors; // Array with all distance sensors

    public BangBangWallRideController() {
        super();
        // get distance sensors and save them in array
        _distanceSensors = new DistanceSensor[] {
                getDistanceSensor("ps7"),
                getDistanceSensor("ps0"),
                getDistanceSensor("ps6"),
                getDistanceSensor("ps1"),
                getDistanceSensor("ps5"),
                getDistanceSensor("ps2")
        };
        //enable the sensors
        for (int i = 0; i < _distanceSensors.length; i++){
            _distanceSensors[i].enable(10);
        }
    }

    // User defined function for initializing and running
    public void run() {
        // Main loop:
        // Perform simulation steps of 64 milliseconds
        // and leave the loop when the simulation is over

        while (step(TIME_STEP) != -1) {

            if (_distanceSensors[S_LEFT].getValue() < 35) {
                driveLeft();
            } else if (_distanceSensors[S_FRONT_LEFT].getValue() < 80 /*&& _distanceSensors[S_FRONT_RIGHT].getValue() < 80*/){
                driveForward();
            } else {
                driveRight();
            }
        }
    }


    private void turnRight() {
        setSpeed(MAX_SPEED, -MAX_SPEED);
    }

    private void turnHardLeft() {
        setSpeed(BACKWARDS, MAX_SPEED);
    }

    private void driveLeft() {
        setSpeed(-300, MAX_SPEED);
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

    private void stop(){
        setSpeed(MIN_SPEED, MIN_SPEED);
    }

    // This is the main program of your controller.
    // It creates an instance of your Robot subclass, launches its
    // function(s) and destroys it at the end of the execution.
    // Note that only one instance of Robot should be created in
    // a controller program.
    // The arguments of the main function can be specified by the
    // "controllerArgs" field of the Robot node
    public static void main(String[] args) {
        BangBangWallRideController controller = new BangBangWallRideController();
        controller.run();
    }
}
