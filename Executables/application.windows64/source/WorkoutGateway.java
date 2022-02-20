import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorkoutGateway {
    /**
     * Takes A List of user objects and saves them to a.ser file.
     * @param list: The List of WorkoutRoutines to be saved
     */
    public void saveWorkoutList(ArrayList<WorkoutRoutine> list, String data, String path){
        try {
            File directory = new File(data);
            directory.mkdir();
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(list);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in "+file.getPath());
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    /**
     * Checks path to the .ser file where the List of WorkoutRoutines is stored and
     * Deserializes it to return a list of WorkoutRoutines to the program
     */
    public ArrayList<WorkoutRoutine> readRoutineList(String path){
        ArrayList<WorkoutRoutine> routinelist = null;
        try {
            File file = new File(path);
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            routinelist = (ArrayList<WorkoutRoutine>) in.readObject();
            in.close();
            fileIn.close();
            return routinelist;
        } catch (FileNotFoundException f) {
            return null;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Empty Routine List (Rebuild .ser file or check permissions)");
            c.printStackTrace();
            return null;
        }
    }

}
