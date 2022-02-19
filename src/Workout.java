/**
 * This file contains the implementation for the workout class which
 * consists of the workout name, reps, and sets.
 * possibility of the duration of the workout
 */

public class Workout {
    private String name;
    private int repCount;
    private int setCount;
    private double duration;

    public Workout(String name, int setCount, int repCount, double duration) {
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

    public double getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
