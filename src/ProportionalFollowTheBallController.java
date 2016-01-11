import Jama.Matrix;
import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.DistanceSensor;
import com.cyberbotics.webots.controller.LightSensor;

public class ProportionalFollowTheBallController extends DifferentialWheels {
    //setup values
    private static int TIME_STEP = 16;
    private static int MAX_SENSOR_VALUE = 200;
    private static int BACKWARDS = -100;
    private static int MIN_SPEED = 0; // min. motor speed
    private static int MAX_SPEED = 1000; // max. motor speed
    private static int LIGHT_THRESHOLD = 200; //min light value for full stop
    private static int CONSTANT = 400;

    //distance sensors
    private static int S_LEFT = 0; // Sensor left
    private static int S_MIDDLE_LEFT = 1; // Sensor right
    private static int S_FRONT_LEFT = 2; // Sensor front left
    private static int S_FRONT_RIGHT = 3; // Sensor front right
    private static int S_MIDDLE_RIGHT = 4; // Sensor right
    private static int S_RIGHT = 5; // Sensor right
    private static int S_REAR_RIGHT = 6; // Sensor right
    private static int S_REAR_LEFT = 7; // Sensor right

    //light sensors
    private static int L_LEFT = 0;
    private static int L_MIDDLE_LEFT = 1;
    private static int L_FRONT_LEFT = 2;
    private static int L_FRONT_RIGHT = 3;
    private static int L_MIDDLE_RIGHT = 4;
    private static int L_RIGHT = 5;
    private static int L_REAR_RIGHT = 6;
    private static int L_REAR_LEFT = 7;

    private DistanceSensor [] _distanceSensors; // Array with all distance sensors
    private Matrix _distanceSensorValueMatrix; //matrix for values of light sensors
    private Matrix _controllerMatrix; //to set importance for specific sensors

    public ProportionalFollowTheBallController () {
        super();
        // get distance sensors and save them in array
        _distanceSensors = new DistanceSensor[] {
                getDistanceSensor("ps5"),
                getDistanceSensor("ps6"),
                getDistanceSensor("ps7"),
                getDistanceSensor("ps0"),
                getDistanceSensor("ps1"),
                getDistanceSensor("ps2"),
                getDistanceSensor("ps3"),
                getDistanceSensor("ps4")
        };

        //enable the sensors
        for (int i = 0; i < 8; i++){
            _distanceSensors[i].enable(10);
        }
    }

    // User defined function for initializing and running
    public void run() {
        // Main loop:
        // Perform simulation steps of 64 milliseconds
        // and leave the loop when the simulation is over
        setPriorities();
        while (step(TIME_STEP) != -1) {
            getValues();
            calculateSpeed();
        }
    }

    private void calculateSpeed() {
        Matrix speedMatrix = _controllerMatrix.times(_distanceSensorValueMatrix);
        setSpeed(speedMatrix.get(1, 0) + CONSTANT, speedMatrix.get(0, 0) + CONSTANT);
    }

    private void setPriorities() {
        double [] [] controllerMatrixArray = {
                //left, middle left, front left, right, middle right, front right
                {0.2,0.1,0.15,0,0,0},
                //left, middle left, front left, right, middle right, front right
                {0,0,0,0.2,0.1,0.15},

        };
        _controllerMatrix = new Matrix(controllerMatrixArray);
    }

    private void getValues() {
        double [][] distanceSensorArray = {
                {_distanceSensors[S_LEFT].getValue()}, {_distanceSensors[S_MIDDLE_LEFT].getValue()}, {_distanceSensors[S_FRONT_LEFT].getValue()},
                {_distanceSensors[S_RIGHT].getValue()}, {_distanceSensors[S_MIDDLE_RIGHT].getValue()}, {_distanceSensors[S_FRONT_RIGHT].getValue()}
        };
        _distanceSensorValueMatrix = new Matrix(distanceSensorArray);
    }

    // This is the main program of your controller.
    // It creates an instance of your Robot subclass, launches its
    // function(s) and destroys it at the end of the execution.
    // Note that only one instance of Robot should be created in
    // a controller program.
    // The arguments of the main function can be specified by the
    // "controllerArgs" field of the Robot node
    public static void main(String[] args) {
        ProportionalFollowTheBallController controller = new ProportionalFollowTheBallController();
        controller.run();
    }
}