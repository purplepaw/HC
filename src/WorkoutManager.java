import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * This Class manages a workout routine and adds workouts to the
 * specific workout routine
 */

public class WorkoutManager implements Serializable{
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

    public WorkoutRoutine createRoutine(String mode, String name) {
        /**
         * each workout routine must have a unique name
         */
        WorkoutRoutine newRoutine;

        if (mode.equals("empty")) {
            newRoutine = new WorkoutRoutine(List.of(), name);
        }
        else if (mode.equals("legs")){
            newRoutine = new WorkoutRoutine(legsWorkouts, name);
        }
        else if (mode.equals("core")){
            newRoutine = new WorkoutRoutine(coreWorkouts, name);
        }
        else if (mode.equals("arms")){
            newRoutine = new WorkoutRoutine(armWorkouts, name);
        }
        else{
            throw new IllegalStateException("Unexpected value: " + mode);
        }

        this.workoutRoutines.add(newRoutine);
        return newRoutine;
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

    public void addWorkoutToRoutine(String workoutName, int reps, int sets, int duration,
                                    WorkoutRoutine workoutRoutine) {
        int index = this.workoutRoutines.indexOf(workoutRoutine);
        WorkoutRoutine theworkoutroutine = this.workoutRoutines.get(index);
        Workout workout = createWorkout(workoutName, reps, sets, duration);
        theworkoutroutine.add(workout);
    }

    public void deleteWorkoutroutine(WorkoutRoutine workoutRoutine){
        this.workoutRoutines.remove(workoutRoutine);
    }

    public ArrayList<WorkoutRoutine> getWorkoutRoutines() {
        return this.workoutRoutines;
    }

    public static void main(String[] args) {
        WorkoutManager workoutManager = new WorkoutManager();
        workoutManager.createRoutine("empty", "My workout");
        ArrayList<WorkoutRoutine> array = workoutManager.getWorkoutRoutines();
        WorkoutRoutine wr = array.get(0);
        workoutManager.addWorkoutToRoutine("Push up", 5, 3, 20, wr);
        Workout w = wr.get(0);
        workoutManager.deleteWorkoutFromRoutine(w, wr);
        workoutManager.deleteWorkoutroutine(wr);


    }
}
