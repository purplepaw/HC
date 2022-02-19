import exceptions.NoWorkoutAvailableException;

import java.util.ArrayList;
import java.util.Random;

public class User {
    private String name;
    private int workoutsCompleted = 0;
    private ArrayList<Workout> createdWorkouts = new ArrayList<Workout>();
    private Random randGen = new Random();

    /**
     * Construct an instance of User.
     * @param name the preferred name of the user
     */
    public User(String name) {
        this.name = name;
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
    public int getWorkoutsCompleted() {
        return workoutsCompleted;
    }

    /**
     * Increment the user's number of workouts completed.
     */
    public void incrementWorkoutCount() {
        workoutsCompleted++;
    }

    /**
     * Get workouts created by user.
     *
     * @return array of workouts created by user
     */
    public ArrayList<Workout> getCreatedWorkouts() {
        return createdWorkouts;
    }

    /**
     * Get random workout that was created by the user.
     * @return a random Workout created by the user in the past
     */
    public Workout getRandomCreatedWorkout() throws NoWorkoutAvailableException {
        if (createdWorkouts.size() == 0) {
            throw new NoWorkoutAvailableException();
        }

        return createdWorkouts.get(randGen.nextInt(createdWorkouts.size()));
    }

    /**
     * Get random workout that was created by the user.
     * @param previous the workout just completed by the user (that they do not want to repeat)
     * @return a random Workout created by the user in the past
     * @throws NoWorkoutAvailableException when no workouts are available or when the only workout available is the
     *                                  previous one
     */
    public Workout getRandomCreatedWorkoutNoRepeat(Workout previous) throws NoWorkoutAvailableException {
        Workout newWO;

        if (createdWorkouts.size() < 2) {
            throw new NoWorkoutAvailableException();
        }

        do {
            newWO = getRandomCreatedWorkout();
        } while (previous == newWO);

        return newWO;

    }
}
