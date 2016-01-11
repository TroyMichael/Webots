/**
 * Created by Michael on 11.01.2016.
 *
 */
public abstract class State {

    private Robot _robot;

    public State(Robot robot) {
        _robot = robot;
    }

    public abstract boolean run();

    public Robot getRobot() {
        return _robot;
    }
}
