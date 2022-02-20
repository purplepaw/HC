public class Controller {
    /**
     * This file contains the implementation for the Controller.
     * The controller interacts with the gateway and WorkoutManager
     */
    WorkoutManager workoutManager;
    WorkoutGateway gateway;
    public Controller(){
        this.workoutManager = new WorkoutManager();
        // this is where the gateway goes
    }
}
