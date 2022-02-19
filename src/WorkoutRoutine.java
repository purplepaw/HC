import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Container object including all of the workouts
 */

public class WorkoutRoutine extends ArrayList<Workout> {
    String name;

    // this is a prepared recommended workout routine for user
    public WorkoutRoutine(List<Workout> preparedWorkouts, String name) {
        super(preparedWorkouts);

        this.name = name;
    }
}
