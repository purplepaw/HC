import java.util.ArrayList;

/**
 * This Class manages a workout routine and adds workouts to the
 * specific workout routine
 */

public class WorkoutManager {
    ArrayList<WorkoutRoutine> workoutRoutines;
    ArrayList<Workout> armWorkouts;
    ArrayList<Workout> coreWorkouts;
    ArrayList<Workout> legsWorkouts;

    public WorkoutManager() {
        this.workoutRoutines = new ArrayList<>();

        createLegWorkout();
        createCoreWorkout();
        createArmWorkout();
    }

    public Workout createWorkout(String name, int set, int rep) {
        return new Workout(name, set, rep);
    }

    public void createLegWorkout() {
        Workout leg1 = createWorkout("Squates", 10, 3);
        Workout leg2 = createWorkout("Lunges", 10, 3);
        Workout leg3 = createWorkout("Knee ups", 10, 3);
        legsWorkouts.add(leg1);
        legsWorkouts.add(leg2);
        legsWorkouts.add(leg3);
    }

    public void createCoreWorkout() {
        Workout core1 = createWorkout("Sit ups", 10, 3);
        Workout core2 = createWorkout("Crunches", 10, 3);
        Workout core3 = createWorkout("Russian Twist", 10, 3);
        coreWorkouts.add(core1);
        coreWorkouts.add(core2);
        coreWorkouts.add(core3);
    }

    public void createArmWorkout() {
        Workout arm1 = createWorkout("Push Up", 10, 3);
        Workout arm2 = createWorkout("Arm Dips", 10, 3);
        Workout arm3 = createWorkout("Bicep Curls with weights", 10, 3);
        armWorkouts.add(arm1);
        armWorkouts.add(arm2);
        armWorkouts.add(arm3);
    }

    public void createRoutine(String mode) {
        WorkoutRoutine new_routine;
        switch (mode) {
            case "empty" -> new_routine = new WorkoutRoutine();
            case "legs" -> new_routine = new WorkoutRoutine(legsWorkouts);
            case "core" -> new_routine = new WorkoutRoutine(coreWorkouts);
            case "arms" -> new_routine = new WorkoutRoutine(armWorkouts);
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
        this.workoutRoutines.add(new_routine);
    }

    public boolean deleteWorkoutFromRoutine(int orderOfWorkout, WorkoutRoutine wr) {
        return true;
    }

    public void addWorkoutToRoutine(Workout w) {

    }

    public ArrayList<WorkoutRoutine> getWorkoutRoutines() {
        return this.workoutRoutines;
    }
}
