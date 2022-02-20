import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Locale;


public class WorkoutRoutineAudioSource {
    private WorkoutRoutine routine;

    public WorkoutRoutineAudioSource(WorkoutRoutine routine) {
        this.routine = routine;
    }

    public void play() {
        try {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

            Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
            synthesizer.resume();

            for (Workout workout : routine) {
                synthesizer.speakPlainText(workout.getSetCount() + " sets of " + workout.getRepCount() + ' ' + workout.getName(), null);
                Thread.sleep(workout.getDuration());
            }

            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
            synthesizer.deallocate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        var manager = new WorkoutManager();

        manager.createRoutine("legs", "My leg Workout");

        var source = new WorkoutRoutineAudioSource(manager.getWorkoutRoutines().get(0));

        source.play();
    }
}
