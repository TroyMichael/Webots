import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.DistanceSensor;
import com.cyberbotics.webots.controller.LightSensor;

public class TemplateController extends DifferentialWheels {
    //setup values
    private static int TIME_STEP = 16;
    private static int MAX_SENSOR_VALUE = 200;
    private static int MIN_SPEED = 0; // min. motor speed
    private static int MAX_SPEED = 1000; // max. motor speed

    //distance sensors
    private static int S_LEFT = 0; // Sensor left
    private static int S_MIDDLE_LEFT = 1; // Sensor right
    private static int S_FRONT_LEFT = 2; // Sensor front left
    private static int S_FRONT_RIGHT = 3; // Sensor front right
    private static int S_MIDDLE_RIGHT = 4; // Sensor right
    private static int S_RIGHT = 5; // Sensor right

    //light sensors
    private static int L_LEFT = 0;
    private static int L_MIDDLE_LEFT = 1;
    private static int L_FRONT_LEFT = 2;
    private static int L_FRONT_RIGHT = 3;
    private static int L_MIDDLE_RIGHT = 4;
    private static int L_RIGHT = 5;

    private DistanceSensor [] _distanceSensors; // Array with all distance sensors
    private LightSensor [] _lightSensors; //Array with all light sensors

    public TemplateController() {
        super();
        // get distance sensors and save them in array
        _distanceSensors = new DistanceSensor[] {
                getDistanceSensor("ps5"),
                getDistanceSensor("ps6"),
                getDistanceSensor("ps7"),
                getDistanceSensor("ps0"),
                getDistanceSensor("ps1"),
                getDistanceSensor("ps2"),
        };
        // get light sensors and save them in array
        _lightSensors = new LightSensor[]{
                getLightSensor("ls5"),
                getLightSensor("ls6"),
                getLightSensor("ls7"),
                getLightSensor("ls0"),
                getLightSensor("ls1"),
                getLightSensor("ls2")
        };

        //enable the sensors
        for (int i = 0; i < 6; i++){
            _distanceSensors[i].enable(10);
            _lightSensors[i].enable(10);
        }
    }

    // User defined function for initializing and running
    public void run() {
        // Main loop:
        // Perform simulation steps of 64 milliseconds
        // and leave the loop when the simulation is over
        while (step(64) != -1) {
            // Read the _distanceSensors:
            // Enter here functions to read sensor data, like:
            //  double val = distanceSensor.getValue();

            // Process sensor data here

            // Enter here functions to send actuator commands, like:
            //  led.set(1);
        };
    }

    /**
     * Robot drives to the right
     */
    private void driveRight() {
        setSpeed(MAX_SPEED, MIN_SPEED);
    }

    /**
     * Robot drives to the left
     */
    private void driveLeft() {
        setSpeed(MIN_SPEED, MAX_SPEED);
    }

    /**
     * Robot drives forward
     */
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
        TemplateController controller = new TemplateController();
        controller.run();
    }
}