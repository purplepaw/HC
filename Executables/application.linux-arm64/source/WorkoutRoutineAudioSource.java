import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Locale;


public class WorkoutRoutineAudioSource {
    private WorkoutRoutine routine;
    private static Synthesizer synthesizer;

    public WorkoutRoutineAudioSource(WorkoutRoutine routine) {
        this.routine = routine;
    }

    public void play() {
        try {
            if (synthesizer == null) {
              System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
              Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

              synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
              synthesizer.allocate();
              synthesizer.resume();
            }

            for (Workout workout : routine) {
                synthesizer.speakPlainText(workout.getSetCount() + " sets of " + workout.getRepCount() + ' ' + workout.getName(), null);
                Thread.sleep(workout.getDuration());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
