import java.util.ArrayList;
import java.util.List;

/**
 * This Class manages a workout routine and adds workouts to the
 * specific workout routine
 */

public class WorkoutManager {
    private ArrayList<WorkoutRoutine> workoutRoutines;

    private List<Workout> armWorkouts;
    private List<Workout> coreWorkouts;
    private List<Workout> legsWorkouts;

    public WorkoutManager() {
        this.workoutRoutines = new ArrayList<>();

        this.armWorkouts = createLegWorkouts();
        this.coreWorkouts = createCoreWorkouts();
        this.legsWorkouts = createArmWorkouts();
    }

    public Workout createWorkout(String name, int setCount, int repCount, long duration) {
        return new Workout(name, setCount, repCount, duration);
    }

    private List<Workout> createLegWorkouts() {
        Workout leg1 = createWorkout("Squats", 10, 3, 5000);
        Workout leg2 = createWorkout("Lunges", 10, 3, 5000);
        Workout leg3 = createWorkout("Knee ups", 10, 3, 5000);

        return List.of(leg1, leg2, leg3);
    }

    private List<Workout> createCoreWorkouts() {
        Workout core1 = createWorkout("Sit ups", 10, 3, 5000);
        Workout core2 = createWorkout("Crunches", 10, 3, 5000);
        Workout core3 = createWorkout("Russian Twist", 10, 3, 5000);

        return List.of(core1, core2, core3);
    }

    private List<Workout> createArmWorkouts() {
        Workout arm1 = createWorkout("Push Up", 10, 3, 5000);
        Workout arm2 = createWorkout("Arm Dips", 10, 3, 5000);
        Workout arm3 = createWorkout("Bicep Curls with weights", 10, 3, 5000);

        return List.of(arm1, arm2, arm3);
    }

    public void createRoutine(String mode) {
        WorkoutRoutine newRoutine;

        switch (mode) {
            case "empty":
                newRoutine = new WorkoutRoutine(List.of(), "empty");
                break;
            case "legs":
                newRoutine = new WorkoutRoutine(legsWorkouts, "legs");
                break;
            case "core":
                newRoutine = new WorkoutRoutine(coreWorkouts, "core");
                break;
            case "arms":
                newRoutine = new WorkoutRoutine(armWorkouts, "arms");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mode);
        }

        this.workoutRoutines.add(newRoutine);
    }

    public boolean deleteWorkoutFromRoutine(Workout workout, WorkoutRoutine workoutRoutine) {
        int indexOfWorkoutRoutine = this.workoutRoutines.indexOf(workoutRoutine);
        WorkoutRoutine theworkoutroutine = this.workoutRoutines.get(indexOfWorkoutRoutine);
        int indexOfWorkout = theworkoutroutine.indexOf(workout);
        if(indexOfWorkout == -1){
            return false;
        }
        Workout theworkout = theworkoutroutine.get(indexOfWorkout);
        theworkoutroutine.remove(theworkout);
        return true;
    }

    public void addWorkoutToRoutine(Workout workout, WorkoutRoutine workoutRoutine) {
        int index = this.workoutRoutines.indexOf(workoutRoutine);
        WorkoutRoutine theworkoutroutine = this.workoutRoutines.get(index);
        theworkoutroutine.add(workout);
    }

    public void deleteWorkoutroutine(WorkoutRoutine workoutRoutine){
        this.workoutRoutines.remove(workoutRoutine);
    }

    public ArrayList<WorkoutRoutine> getWorkoutRoutines() {
        return this.workoutRoutines;
    }
}
