import Jama.Matrix;
import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.DistanceSensor;

/**
 * Created by Michael on 21.12.2015.
 *
 */
public class PushAllBallsToWallControllerSOAP extends DifferentialWheels {


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

    private DistanceSensor[] _distanceSensors; // Array with all distance sensors
    private int stepUpdate = 0;

    public PushAllBallsToWallControllerSOAP() {
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
        while (step(TIME_STEP) != -1) {

        };
    }



    // This is the main program of your controller.
    // It creates an instance of your Robot subclass, launches its
    // function(s) and destroys it at the end of the execution.
    // Note that only one instance of Robot should be created in
    // a controller program.
    // The arguments of the main function can be specified by the
    // "controllerArgs" field of the Robot node
    public static void main(String[] args) {
        PushAllBallsToWallControllerSOAP controller = new PushAllBallsToWallControllerSOAP();
        controller.run();
    }

}
