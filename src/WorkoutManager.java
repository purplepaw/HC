import java.util.ArrayList;

/**
 * This Class manages a workout routine and adds workouts to the
 * specific workout routine
 */

public class WorkoutManager {
    private ArrayList<WorkoutRoutine> workoutRoutines;
    private ArrayList<Workout> armWorkouts;
    private ArrayList<Workout> coreWorkouts;
    private ArrayList<Workout> legsWorkouts;

    public WorkoutManager() {
        this.workoutRoutines = new ArrayList<>();

        createLegWorkouts();
        createCoreWorkouts();
        createArmWorkouts();
    }

    public Workout createWorkout(String name, int setCount, int repCount, double duration) {
        return new Workout(name, setCount, repCount, duration);
    }

    public void createLegWorkouts() {
        Workout leg1 = createWorkout("Squats", 10, 3, 10);
        Workout leg2 = createWorkout("Lunges", 10, 3, 10);
        Workout leg3 = createWorkout("Knee ups", 10, 3, 10);
        legsWorkouts.add(leg1);
        legsWorkouts.add(leg2);
        legsWorkouts.add(leg3);
    }

    public void createCoreWorkouts() {
        Workout core1 = createWorkout("Sit ups", 10, 3, 10);
        Workout core2 = createWorkout("Crunches", 10, 3, 10);
        Workout core3 = createWorkout("Russian Twist", 10, 3, 10);
        coreWorkouts.add(core1);
        coreWorkouts.add(core2);
        coreWorkouts.add(core3);
    }

    public void createArmWorkouts() {
        Workout arm1 = createWorkout("Push Up", 10, 3, 10);
        Workout arm2 = createWorkout("Arm Dips", 10, 3, 10);
        Workout arm3 = createWorkout("Bicep Curls with weights", 10, 3, 10);
        armWorkouts.add(arm1);
        armWorkouts.add(arm2);
        armWorkouts.add(arm3);
    }

    public void createRoutine(String mode) {
        WorkoutRoutine newRoutine;

        switch (mode) {
            case "empty":
                newRoutine = new WorkoutRoutine();
                break;
            case "legs":
                newRoutine = new WorkoutRoutine(legsWorkouts);
                break;
            case "core":
                newRoutine = new WorkoutRoutine(coreWorkouts);
                break;
            case "arms":
                newRoutine = new WorkoutRoutine(armWorkouts);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mode);
        }

        this.workoutRoutines.add(newRoutine);
    }

    public boolean deleteWorkoutFromRoutine(int orderOfWorkout, WorkoutRoutine workoutRoutine) {
        return true;
    }

    public void addWorkoutToRoutine(Workout workout) {

    }

    public ArrayList<WorkoutRoutine> getWorkoutRoutines() {
        return this.workoutRoutines;
    }
}
