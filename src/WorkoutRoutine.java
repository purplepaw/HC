import java.util.ArrayList;
import java.util.List;

public class WorkoutRoutine {
    /**
     * Container object including all of the workouts
     */

    // this will create an empty routine
    ArrayList<WorkOut> routine;
    public WorkoutRoutine(){
        this.routine = new ArrayList<>();
    }
    // this is a prepared recommended workout routine for user
    public WorkoutRoutine(ArrayList<WorkOut> preparedworkout){
        this.routine = preparedworkout;
    }
    public void addWorkOut(WorkOut w){
        this.routine.add(w);
    }
    public void deleteWorkout(WorkOut w){
        this.routine.remove(w);
    }
}
