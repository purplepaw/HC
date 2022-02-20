import java.io.Serializable;

/**
 * This file contains the implementation for the workout class which
 * consists of the workout name, reps, and sets.
 * possibility of the duration of the workout
 */


public class Workout implements Serializable{
    private String name;
    private int repCount;
    private int setCount;
    private long duration;

    public Workout(String name, int setCount, int repCount, long duration) {
        this.name = name;
        this.setCount = setCount;
        this.repCount = repCount;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSetCount() {
        return setCount;
    }

    public void setSetCount(int setCount) {
        this.setCount = setCount;
    }

    public int getRepCount() {
        return repCount;
    }

    public void setRepCount(int repCount) {
        this.repCount = repCount;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
