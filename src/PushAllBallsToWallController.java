import com.cyberbotics.webots.controller.Compass;
import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.DistanceSensor;
import javafx.animation.RotateTransition;

/**
 * Created by Michael on 21.12.2015.
 *
 */
public class PushAllBallsToWallController extends DifferentialWheels {


    //setup values
    private static int TIME_STEP = 16;
    private static int MAX_SENSOR_VALUE = 200;
    private static int BACKWARDS = -100;
    private static int MIN_SPEED = 0; // min. motor speed
    private static int MAX_SPEED = 1000; // max. motor speed
    private static int CONSTANT = 400;

    private static int TURN_90_DEGREES = 21;
    private static int MAX_STRAIGHT_STEPS = 40;
    private static int INCREASED_STEPS = 1;

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

    private Boolean foundBall = false;
    private Boolean foundWall = false;
    private int goRight = 0;
    private int goStraight = 0;
    private int increaseSteps = 0;


    public PushAllBallsToWallController() {
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
            followRoute();

        }
    }



    private void followRoute() {

        if (goStraight < MAX_STRAIGHT_STEPS * INCREASED_STEPS){
            driveForward();
            goStraight++;
        } else if (goRight < TURN_90_DEGREES){
            turnRight();
            goRight++;
        } else {
            goRight = 0;
            goStraight = 0;
            increaseSteps++;

            if (increaseSteps % 2 == 0) {
                INCREASED_STEPS = INCREASED_STEPS + 1;
            }
        }
    }

    private void driveForward() {
        setSpeed(MAX_SPEED, MAX_SPEED);
    }

    private void pushBallToWall() {

    }

    private void returnToPosition() {

    }

    private void turnRight() {
        setSpeed(988, -988);

    }

    private void turnAround() {
        setSpeed(MAX_SPEED, -MAX_SPEED);
    }

    // This is the main program of your controller.
    // It creates an instance of your Robot subclass, launches its
    // function(s) and destroys it at the end of the execution.
    // Note that only one instance of Robot should be created in
    // a controller program.
    // The arguments of the main function can be specified by the
    // "controllerArgs" field of the Robot node
    public static void main(String[] args) {
        PushAllBallsToWallController controller = new PushAllBallsToWallController();
        controller.run();
    }

}
