public class WorkOut {
    /**
     * This file contains the implementation for the workout class which
     * consists of the workout name, reps, and sets.
     * possibility of the duration of the workout
     */
    private String workOutName;
    private int reps;
    private int sets;
    private int duration;

    public WorkOut(String name, int sets, int reps){
        this.reps = reps;
        this.workOutName = name;
        this.sets = sets;

    }
    public void changeSet(int setnum){
        this.sets = setnum;
    }
    public void changeRep(int repnum){
        this.reps = repnum;
    }
}
