import java.util.ArrayList;

public class WorkoutAudioSource {
    private ArrayList<Workout> workouts;

    WorkoutAudioSource(ArrayList<Workout> workouts) {
        this.workouts = (ArrayList<Workout>) workouts.clone();
    }

    void play() {
        for (Workout workout : workouts) {

        }
    }
}
