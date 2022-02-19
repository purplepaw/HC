import java.util.ArrayList;

public class WorkoutManager {
    /**
     * This Class manages a workout routine and adds workouts to the
     * specific workout routine
     */
    ArrayList<WorkoutRoutine> workoutRoutines;
    ArrayList<WorkOut> armWorkout;
    ArrayList<WorkOut> coreWorkout;
    ArrayList<WorkOut> legsWorkout;

    public WorkoutManager(){
        this.workoutRoutines = new ArrayList<>();
        createlegworkout();
        createcoreworkout();
        createarmworkout();
    }
    public WorkOut createWorkout(String name, int set, int rep){
        return new WorkOut(name, set, rep);
    }
    public void createlegworkout(){
        WorkOut leg1 = createWorkout("Squates", 10, 3);
        WorkOut leg2 = createWorkout("Lunges", 10, 3);
        WorkOut leg3 = createWorkout("Knee ups", 10, 3);
        legsWorkout.add(leg1);
        legsWorkout.add(leg2);
        legsWorkout.add(leg3);
    }

    public void createcoreworkout(){
        WorkOut core1 = createWorkout("Sit ups", 10, 3);
        WorkOut core2 = createWorkout("Crunches", 10, 3);
        WorkOut core3 = createWorkout("Russian Twist", 10, 3);
        coreWorkout.add(core1);
        coreWorkout.add(core2);
        coreWorkout.add(core3);
    }

    public void createarmworkout(){
        WorkOut arm1 = createWorkout("Push Up", 10, 3);
        WorkOut arm2 = createWorkout("Arm Dips", 10, 3);
        WorkOut arm3 = createWorkout("Bicep Curls with weights", 10, 3);
        armWorkout.add(arm1);
        armWorkout.add(arm2);
        armWorkout.add(arm3);
    }

    public void createRoutine(String mode){
        WorkoutRoutine new_routine;
        switch (mode) {
            case "empty" -> new_routine = new WorkoutRoutine();
            case "legs" -> new_routine = new WorkoutRoutine(legsWorkout);
            case "core" -> new_routine = new WorkoutRoutine(coreWorkout);
            case "arms" -> new_routine = new WorkoutRoutine(armWorkout);
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
        this.workoutRoutines.add(new_routine);
    }
    public boolean deleteWorkoutFromRoutine(int orderofworkout, WorkoutRoutine wr){
        return true;
    }

    public void addWorkoutToRoutine(WorkOut w){

    }
    public ArrayList<WorkoutRoutine> getWorkoutRoutines(){
        return this.workoutRoutines;
    }

}
