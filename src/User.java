public class User {
    private String name;
    private int workoutsCompleted = 0;

    /**
     * Construct an instance of User.
     * @param name the preferred name of the user
     */
    public User(String name) {
        this.name = name;
    }

    /**
     * Get name
     *
     * @return value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Get number of workouts completed
     *
     * @return number of workouts completed
     */
    public int getWorkoutsCompleted() {
        return workoutsCompleted;
    }

    /**
     * Increment the user's number of workouts completed.
     */
    public void incrementWorkoutCount() {
        workoutsCompleted++;
    }
}
