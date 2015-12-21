import Jama.Matrix;
import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.DistanceSensor;
import com.cyberbotics.webots.controller.LightSensor;

public class ProportionalRunToLightController extends DifferentialWheels {
    //setup values
    private static int TIME_STEP = 16;
    private static int MAX_SENSOR_VALUE = 200;
    private static int BACKWARDS = -100;
    private static int MIN_SPEED = 0; // min. motor speed
    private static int MAX_SPEED = 1000; // max. motor speed
    private static int LIGHT_THRESHOLD = 200; //min light value for full stop

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
    private LightSensor [] _lightSensors; //Array with all light sensors
    private Matrix _lightSensorValueMatrix; //matrix for values of light sensors
    private Matrix _lightControllerMatrix; //to set importance for specific sensors
    private Matrix _distanceControllerMatrix;
    private Matrix _speedConstant;

    private Matrix _distanceSensorValueMatrix;

    public ProportionalRunToLightController () {
        super();
        // get light sensors and save them in array
        _lightSensors = new LightSensor[]{
                getLightSensor("ls5"),
                getLightSensor("ls6"),
                getLightSensor("ls7"),
                getLightSensor("ls0"),
                getLightSensor("ls1"),
                getLightSensor("ls2"),
                getLightSensor("ls3"),
                getLightSensor("ls4")
        };

        //enable the sensors
        for (int i = 0; i < 8; i++){
            _lightSensors[i].enable(10);
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
        };
    }

    private void calculateSpeed() {
        Matrix lightMatrix = _lightControllerMatrix.times(_lightSensorValueMatrix).plus(_speedConstant);
        setSpeed(lightMatrix.get(0, 0), lightMatrix.get(1, 0));
    }

    private void setPriorities() {

        _lightControllerMatrix = new Matrix(new double[][] {
                //left, middle left, front left, right, middle right, front right
                { 0.1, 0.05, 0.05, 0, 0, 0},
                { 0, 0, 0, 0.1, 0.05, 0.05}
        });

        _speedConstant = new Matrix(new double[][] {
                {150},
                {150}
        });
    }

    private void getValues() {
        _lightSensorValueMatrix = new Matrix(new double[][] {
                {_lightSensors[L_LEFT].getValue()},
                {_lightSensors[L_MIDDLE_LEFT].getValue()},
                {_lightSensors[L_FRONT_LEFT].getValue()},
                {_lightSensors[L_RIGHT].getValue()},
                {_lightSensors[L_MIDDLE_RIGHT].getValue()},
                {_lightSensors[L_FRONT_RIGHT].getValue()}
        });
    }

    // This is the main program of your controller.
    // It creates an instance of your Robot subclass, launches its
    // function(s) and destroys it at the end of the execution.
    // Note that only one instance of Robot should be created in
    // a controller program.
    // The arguments of the main function can be specified by the
    // "controllerArgs" field of the Robot node
    public static void main(String[] args) {
        ProportionalRunToLightController controller = new ProportionalRunToLightController();
        controller.run();
    }
}