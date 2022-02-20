import java.util.Random;

public class User {
    private String name;
    private int completedWorkoutCount;
    private Random randGen;

    /**
     * Construct an instance of User.
     *
     * @param name the preferred name of the user
     */
    public User(String name) {
        this.name = name;
        this.completedWorkoutCount = 0;
        this.randGen = new Random();
    }

    /**
     * Get name of the User.
     *
     * @return value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Get number of workouts completed.
     *
     * @return number of workouts completed
     */
    public int getCompletedWorkoutCount() {
        return completedWorkoutCount;
    }

    /**
     * Increment the user's number of workouts completed.
     */
    public void incrementWorkoutCompletedCount() {
        ++completedWorkoutCount;
    }
}
