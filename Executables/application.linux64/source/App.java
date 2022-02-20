import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import g4p_controls.*; 
import uibooster.*; 
import uibooster.model.*; 
import java.awt.Font; 
import java.awt.*; 
import java.util.*; 

import com.sun.speech.freetts.en.us.cmu_time_awb.*; 
import com.sun.speech.freetts.en.us.cmu_us_kal.*; 
import com.sun.speech.freetts.en.*; 
import com.sun.speech.freetts.en.us.*; 
import com.sun.speech.engine.*; 
import com.sun.speech.engine.synthesis.*; 
import com.sun.speech.engine.synthesis.text.*; 
import com.sun.speech.freetts.jsapi.*; 
import com.sun.speech.freetts.*; 
import com.sun.speech.freetts.audio.*; 
import com.sun.speech.freetts.cart.*; 
import com.sun.speech.freetts.clunits.*; 
import com.sun.speech.freetts.diphone.*; 
import com.sun.speech.freetts.lexicon.*; 
import com.sun.speech.freetts.relp.*; 
import com.sun.speech.freetts.util.*; 
import de.dfki.lt.freetts.*; 
import de.dfki.lt.freetts.mbrola.*; 
import javax.speech.synthesis.*; 
import javax.speech.recognition.*; 
import javax.speech.*; 
import de.dfki.lt.freetts.en.us.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class App extends PApplet {





 


// Constants
int Y_AXIS = 1;
int X_AXIS = 2;

GButton start;
ArrayList<GButton> workoutButtons;
ArrayList<GButton> editButtons;

GButton workoutRepeat;
GButton workoutStop;

GButton nameChange;
ArrayList<GButton> unitButtons;
GButton toMenu;

UiBooster booster;
PImage logo;
PImage runner;

ArrayList<WorkoutRoutine> routines;
WorkoutRoutine routine;
WorkoutManager manager;
WorkoutGateway gateway;
Thread thread;

// Global Variable
int state = 0; // 0 for main menu, 1 create account, 2 user dashboard
int oldState = -1; // to transition display
String name;
boolean repeat = false;

Synthesizer synthesizer;

/**
  *  Sets up the canvas and the objects that the program depends on to run.
  **/
public void setup() {
  
  
  noStroke();
  frameRate(30); // limit framerate to 30 frames per second to reduce impact on system
  G4P.setGlobalColorScheme(GCScheme.CYAN_SCHEME);
  
  start = new GButton(this, (width / 2) - (width / 8), 5 * height / 8, 2 * width / 8, 1 * height / 8);
  start.setText("Start");
  start.setFont(new Font("Times New Roman", Font.BOLD, 60));
  start.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  start.addEventHandler(this, "startButton");
  start.setVisible(false);

  workoutButtons = new ArrayList<GButton>();
  editButtons = new ArrayList<GButton>();
  
  for (int i = 0; i < 10; ++i) {
    GButton button = new GButton(this, width / 3 - width / 16, 2 * height / 8 + i * height / 16, width / 8, 0.475f * height / 8);
    button.setText("Begin Workout " + i);
    button.setFont(new Font("Times New Roman", Font.BOLD, 28));
    button.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
    button.addEventHandler(this, "workoutStart" + i);
    button.setVisible(false);
    workoutButtons.add(button);
    
    button = new GButton(this, 2 * width / 3 - width / 16, 2 * height / 8 + i * height / 16, width / 8, 0.475f * height / 8);
    button.setText("Edit");
    button.setFont(new Font("Times New Roman", Font.BOLD, 28));
    button.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
    button.addEventHandler(this, "workoutEdit" + i);
    button.setVisible(false);
    editButtons.add(button);
  }
  
  workoutRepeat = new GButton(this, 3.5f * width / 8, 3.5f * height / 8, width / 8, 0.5f * height / 8);
  workoutRepeat.setText("Toggle repeat");
  workoutRepeat.setFont(new Font("Times New Roman", Font.BOLD, 28));
  workoutRepeat.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  workoutRepeat.addEventHandler(this, "workoutRepeatNow");
  workoutRepeat.setVisible(false);
  
  workoutStop = new GButton(this, 3.5f * width / 8, 5 * height / 8, width / 8, 0.5f * height / 8);
  workoutStop.setText("Stop workout");
  workoutStop.setFont(new Font("Times New Roman", Font.BOLD, 28));
  workoutStop.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  workoutStop.addEventHandler(this, "workoutStopNow");
  workoutStop.setVisible(false);

  nameChange = new GButton(this, 3.5f * width / 8, 0.5f * height / 8, width / 8, 0.5f * height / 8);
  nameChange.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  nameChange.setFont(new Font("Times New Roman", Font.BOLD, 28));
  nameChange.addEventHandler(this, "nameEdit");
  nameChange.setVisible(false);
  
  unitButtons = new ArrayList<GButton>();
  
  for (int i = 0; i < 10; ++i) {
    GButton button = new GButton(this, 3.5f * width / 8, 1.5f * height / 8 + i * height / 16, width / 8, 0.5f * height / 8);
    button.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
    button.setFont(new Font("Times New Roman", Font.BOLD, 28));
    button.addEventHandler(this, "unitEdit" + i);
    button.setVisible(false);
    unitButtons.add(button);
  }
  
  toMenu = new GButton(this, 3.5f * width / 8, 7 * height / 8, width / 8, 0.5f * height / 8);
  toMenu.setText("Main menu");
  toMenu.setFont(new Font("Times New Roman", Font.BOLD, 28));
  toMenu.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  toMenu.addEventHandler(this, "toMenuNow");
  toMenu.setVisible(false);

  gateway = new WorkoutGateway();
  manager = new WorkoutManager();
  routines = gateway.readRoutineList(dataPath("workoutFile.ser"));

  if (routines == null) {
    routines = new ArrayList<WorkoutRoutine>();
  
    routines.add(manager.createRoutine("legs", "Leg workouts"));
    routines.add(manager.createRoutine("arms", "Arm workouts"));
    routines.add(manager.createRoutine("core", "Core workouts"));
    routines.add(manager.createRoutine("empty", "Empty routine"));
    routines.add(manager.createRoutine("empty", "Empty routine"));
    routines.add(manager.createRoutine("empty", "Empty routine"));
    routines.add(manager.createRoutine("empty", "Empty routine"));
    routines.add(manager.createRoutine("empty", "Empty routine"));
    routines.add(manager.createRoutine("empty", "Empty routine"));
    routines.add(manager.createRoutine("empty", "Empty routine"));

    gateway.saveWorkoutList(routines, dataPath(""), dataPath("workoutFile.ser"));
  }
  
  textAlign(CENTER, CENTER);
  
  // load in logo to be drawn on menu
  // source: http://www.stickpng.com/img/objects/weights/weights-clipart
  logo = loadImage("logo.png");
  
  // load in runner to be drawn on user dashboard
  // source: https://www.emojipng.com/preview/12530385
  runner = loadImage("runner.png");
  imageMode(CENTER);
}

/**
  * Draws the frames to the screen.
  **/
public void draw() {
  textAlign(CENTER, CENTER);
  setGradient(0, 0, width, height, color(245, 39, 7), color(245, 154, 7), X_AXIS);
  if (state != oldState) {
    oldState = state;
    start.setVisible(false);
    for (int i = 0; i < 10; ++i) {
      workoutButtons.get(i).setVisible(false);
      editButtons.get(i).setVisible(false);
    }
    workoutRepeat.setVisible(false);
    workoutStop.setVisible(false);
    nameChange.setVisible(false);
    for (int i = 0; i < 10; ++i) {
      unitButtons.get(i).setVisible(false);
    }
    toMenu.setVisible(false);
    repeat = false;
  
    if (thread != null) {
      try {
        thread.interrupt();
      } catch( Exception e) {
      }
      thread = null;
    }
    
    if (state == 0) {
        // Button configuration
        start.setVisible(true);
    } else if (state == 1) {
      String[] strings = loadStrings(dataPath("settings.txt"));
      
      if (strings == null) {
         name = new UiBooster().showTextInputDialog("What is your name?");
         saveStrings(dataPath("settings.txt"), new String[] { name });
      } else {
        name = loadStrings(dataPath("settings.txt"))[0];
      }
       state = 2;
    } else if (state == 2) {
      for (int i = 0; i < 10; ++i) {
        workoutButtons.get(i).setVisible(true);
        editButtons.get(i).setVisible(true);
      }
    } else if (state == 3) {
      thread = new Thread() {
        public void run() {
          WorkoutRoutineAudioSource source = new WorkoutRoutineAudioSource(routine);
          source.play();
        }
      };
      thread.start();
      
      workoutRepeat.setVisible(true);
      workoutStop.setVisible(true);
    } else if (state == 4) {
      nameChange.setVisible(true);
      
      for (int i = 0; i < 10; ++i) {
        unitButtons.get(i).setVisible(true);
      }
      
      toMenu.setVisible(true);
    }
  }
  
  // logo display
  if (state == 0) {
      image(logo, width / 2, height / 4, logo.width / 2.9f, logo.height / 2.9f); 
  }
  
  if (state == 2) {
       textSize(75);
       text("Hello, " + name + "!", width/2, height/12);
       line(width/8, 1.65f * height/12 + 5, 7 * width/8, 1.5f * height/12 + 3);
       image(runner, width / 2, height / 2, runner.width / 2.9f, runner.height / 2.9f); 
       
    for (int i = 0; i < 10; ++i) {
      workoutButtons.get(i).setText(routines.get(i).name);   
    }
  } else if (state == 3) {
    textSize(75);
   text(routine.name, width/2, height/12);
   line(width/8, 1.5f * height/12 + 1, 7 * width/8, 1.5f * height/12 + 3);
   text("Repeat: " + (repeat ? "on" : "off"), width/2, 3 * height/12);
   
   if (!thread.isAlive()) {
     if (repeat) {
       try{
        thread.join();
       } catch(Exception e) {}
       
       thread = new Thread() {
         public void run() {
           WorkoutRoutineAudioSource source = new WorkoutRoutineAudioSource(routine);
           source.play();
         }
       };
       thread.start();
     } else {
       state = 2;
     }
   }
  } else if (state == 4) {
    nameChange.setText(routine.name);
    
    for (int i = 0; i < 10; ++i) {
      if (i < routine.size()) {
        unitButtons.get(i).setText(routine.get(i).getName());
       }
      else {
        unitButtons.get(i).setText("Empty workout");
    }
  }
} 

}

/** From: https://processing.org/examples/lineargradient.html */
public void setGradient(int x, int y, float w, float h, int c1, int c2, int axis ) {

  noFill();

  if (axis == Y_AXIS) {  // Top to bottom gradient
    for (int i = y; i <= y+h; i++) {
      float inter = map(i, y, y+h, 0, 1);
      int c = lerpColor(c1, c2, inter);
      stroke(c);
      line(x, i, x+w, i);
    }
  }  
  else if (axis == X_AXIS) {  // Left to right gradient
    for (int i = x; i <= x+w; i++) {
      float inter = map(i, x, x+w, 0, 1);
      int c = lerpColor(c1, c2, inter);
      stroke(c);
      line(i, y, i, y+h);
    }
  }
}

// function executed when start is pressed
public void startButton(GButton button, GEvent event) {
   state = 1; 
}

public void workoutStart(int index) {
  if (routines.get(index).isEmpty()) {
    new UiBooster().showErrorDialog("The selected workout routine is empty! Please populate it with some workouts.", "Empty routine");
    return;
  }

  routine = routines.get(index);
  state = 3;
}

public void workoutStart0(GButton button, GEvent event) { workoutStart(0); }
public void workoutStart1(GButton button, GEvent event) { workoutStart(1); }
public void workoutStart2(GButton button, GEvent event) { workoutStart(2); }
public void workoutStart3(GButton button, GEvent event) { workoutStart(3); }
public void workoutStart4(GButton button, GEvent event) { workoutStart(4); }
public void workoutStart5(GButton button, GEvent event) { workoutStart(5); }
public void workoutStart6(GButton button, GEvent event) { workoutStart(6); }
public void workoutStart7(GButton button, GEvent event) { workoutStart(7); }
public void workoutStart8(GButton button, GEvent event) { workoutStart(8); }
public void workoutStart9(GButton button, GEvent event) { workoutStart(9); }

public void workoutEdit(int index) {
  routine = routines.get(index);
  state = 4;
}

public void workoutEdit0(GButton button, GEvent event) { workoutEdit(0); }
public void workoutEdit1(GButton button, GEvent event) { workoutEdit(1); }
public void workoutEdit2(GButton button, GEvent event) { workoutEdit(2); }
public void workoutEdit3(GButton button, GEvent event) { workoutEdit(3); }
public void workoutEdit4(GButton button, GEvent event) { workoutEdit(4); }
public void workoutEdit5(GButton button, GEvent event) { workoutEdit(5); }
public void workoutEdit6(GButton button, GEvent event) { workoutEdit(6); }
public void workoutEdit7(GButton button, GEvent event) { workoutEdit(7); }
public void workoutEdit8(GButton button, GEvent event) { workoutEdit(8); }
public void workoutEdit9(GButton button, GEvent event) { workoutEdit(9); }

public void nameEdit(GButton button, GEvent event) {
  String name = new UiBooster().showTextInputDialog("Name of this workout?");
  if (name.equals("") || name == null)
    return;
  routine.name = name;
}

public void unitEdit(int index) {
  String name = new UiBooster().showTextInputDialog("Name of this workout?");
  int setCount, repCount;
  long duration;

  if (name.equals("") || name == null)
    return;

  do {
      setCount = parseInt(new UiBooster().showTextInputDialog("Number of sets? (Pick between 1 and 10)")); 
  } while (!(1 <= setCount && setCount <= 10));

  do {
      repCount = parseInt(new UiBooster().showTextInputDialog("Number of reps? (Pick between 1 and 20)")); 
  } while (!(1 <= repCount && repCount <= 20));

  duration = parseInt(new UiBooster().showTextInputDialog("Duration (in seconds)?")) * 1000;

  Workout workout = new Workout(name, setCount, repCount, duration);

  if (index < routine.size())
    routine.set(index, workout);
  else
    routine.add(workout);
}

public void unitEdit0(GButton button, GEvent event) { unitEdit(0); }
public void unitEdit1(GButton button, GEvent event) { unitEdit(1); }
public void unitEdit2(GButton button, GEvent event) { unitEdit(2); }
public void unitEdit3(GButton button, GEvent event) { unitEdit(3); }
public void unitEdit4(GButton button, GEvent event) { unitEdit(4); }
public void unitEdit5(GButton button, GEvent event) { unitEdit(5); }
public void unitEdit6(GButton button, GEvent event) { unitEdit(6); }
public void unitEdit7(GButton button, GEvent event) { unitEdit(7); }
public void unitEdit8(GButton button, GEvent event) { unitEdit(8); }
public void unitEdit9(GButton button, GEvent event) { unitEdit(9); }

public void toMenuNow(GButton button, GEvent event) {
  routine = null;
  state = 2;
  
  gateway.saveWorkoutList(routines, dataPath(""), dataPath("workoutFile.ser"));  
}

public void workoutRepeatNow(GButton button, GEvent event) {
  repeat = !repeat;
}

public void workoutStopNow(GButton button, GEvent event) {
  routine = null;
  state = 2; 
}

/**
 * Empty function to suppress G4P suggestion. 
*/
public void handleButtonEvents(GButton button, GEvent event) {
  return;
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "App" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
