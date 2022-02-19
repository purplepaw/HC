/**
 * This file contains the implementation for the workout class which
 * consists of the workout name, reps, and sets.
 * possibility of the duration of the workout
 */

public class Workout {
    private String name;
    private int repCount;
    private int setCount;
    private int duration;

    public Workout(String name, int setCount, int repCount) {
        this.name = name;
        this.setCount = setCount;
        this.repCount = repCount;
    }

    public void setSetCount(int setCount) {
        this.setCount = setCount;
    }

    public void setRepCount(int repCount) {
        this.repCount = repCount;
    }
}
