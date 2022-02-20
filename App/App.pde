import g4p_controls.*;
import uibooster.*;
import uibooster.model.*;
import java.awt.Font;
 import java.awt.*;
import java.util.*;

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
void setup() {
  fullScreen();
  
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
    GButton button = new GButton(this, 2.15 * width / 8, 1.5 * height / 8 + i * height / 16, width / 8, 0.5 * height / 8);
    button.setText("Begin Workout " + i);
    button.setFont(new Font("Times New Roman", Font.BOLD, 28));
    button.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
    button.addEventHandler(this, "workoutStart" + i);
    button.setVisible(false);
    workoutButtons.add(button);
    
    button = new GButton(this, 5 * width / 8, 1.5 * height / 8 + i * height / 16, width / 8, 0.5 * height / 8);
    button.setText("Edit");
    button.setFont(new Font("Times New Roman", Font.BOLD, 28));
    button.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
    button.addEventHandler(this, "workoutEdit" + i);
    button.setVisible(false);
    editButtons.add(button);
  }
  
  workoutRepeat = new GButton(this, 3.5 * width / 8, 3.5 * height / 8, width / 8, 0.5 * height / 8);
  workoutRepeat.setText("Toggle repeat");
  workoutRepeat.setFont(new Font("Times New Roman", Font.BOLD, 28));
  workoutRepeat.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  workoutRepeat.addEventHandler(this, "workoutRepeatNow");
  workoutRepeat.setVisible(false);
  
  workoutStop = new GButton(this, 3.5 * width / 8, 5 * height / 8, width / 8, 0.5 * height / 8);
  workoutStop.setText("Stop workout");
  workoutStop.setFont(new Font("Times New Roman", Font.BOLD, 28));
  workoutStop.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  workoutStop.addEventHandler(this, "workoutStopNow");
  workoutStop.setVisible(false);

  nameChange = new GButton(this, 3.5 * width / 8, 0.5 * height / 8, width / 8, 0.5 * height / 8);
  nameChange.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  nameChange.setFont(new Font("Times New Roman", Font.BOLD, 28));
  nameChange.addEventHandler(this, "nameEdit");
  nameChange.setVisible(false);
  
  unitButtons = new ArrayList<GButton>();
  
  for (int i = 0; i < 10; ++i) {
    GButton button = new GButton(this, 3.5 * width / 8, 1.5 * height / 8 + i * height / 16, width / 8, 0.5 * height / 8);
    button.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
    button.setFont(new Font("Times New Roman", Font.BOLD, 28));
    button.addEventHandler(this, "unitEdit" + i);
    button.setVisible(false);
    unitButtons.add(button);
  }
  
  toMenu = new GButton(this, 3.5 * width / 8, 7.5 * height / 8, width / 8, 0.5 * height / 8);
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
    routines.add(manager.createRoutine("empty", "Empty workouts"));
    routines.add(manager.createRoutine("empty", "Empty workouts"));
    routines.add(manager.createRoutine("empty", "Empty workouts"));
    routines.add(manager.createRoutine("empty", "Empty workouts"));
    routines.add(manager.createRoutine("empty", "Empty workouts"));
    routines.add(manager.createRoutine("empty", "Empty workouts"));
    routines.add(manager.createRoutine("empty", "Empty workouts"));

    gateway.saveWorkoutList(routines, dataPath(""), dataPath("workoutFile.ser"));
  }
  
  textAlign(CENTER, CENTER);
  
  // Loads in logo to be drawn on menu
  logo = loadImage("logo.png");
  imageMode(CENTER);
}

/**
  * Draws the frames to the screen.
  **/
void draw() {
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
      String[] strings = loadStrings("settings.txt");
      
      if (strings == null) {
         name = new UiBooster().showTextInputDialog("What is your name?");
         saveStrings("settings.txt", new String[] { name });
      } else {
        name = loadStrings("settings.txt")[0];
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
      image(logo, width / 2, height / 4, logo.width / 2.9, logo.height / 2.9); 
  }
  
  if (state == 2) {
       textSize(75);
       text("Hello, " + name + "!", width/2, height/12);
       line(width/8, 1.5 * height/12 + 1, 7 * width/8, 1.5 * height/12 + 3);
       
    for (int i = 0; i < 10; ++i) {
      workoutButtons.get(i).setText(routines.get(i).name);   
    }
  } else if (state == 3) {
    textSize(75);
   text(routine.name, width/2, height/12);
   line(width/8, 1.5 * height/12 + 1, 7 * width/8, 1.5 * height/12 + 3);
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
void setGradient(int x, int y, float w, float h, color c1, color c2, int axis ) {

  noFill();

  if (axis == Y_AXIS) {  // Top to bottom gradient
    for (int i = y; i <= y+h; i++) {
      float inter = map(i, y, y+h, 0, 1);
      color c = lerpColor(c1, c2, inter);
      stroke(c);
      line(x, i, x+w, i);
    }
  }  
  else if (axis == X_AXIS) {  // Left to right gradient
    for (int i = x; i <= x+w; i++) {
      float inter = map(i, x, x+w, 0, 1);
      color c = lerpColor(c1, c2, inter);
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
  routine.name = name;
}

public void unitEdit(int index) {
  String name = new UiBooster().showTextInputDialog("Name of this workout?");
  String setCount2 = "";
  ArrayList <String> num_list = new ArrayList<String>(Arrays.asList("1","2","3", "4", "5", "6", "7", "8", "9", "10"));
  while (num_list.contains(setCount2) == false) {
  setCount2 = new UiBooster().showTextInputDialog("Number of sets? (Pick between 1 and 10)");}
  int setCount = parseInt(setCount2);
  ArrayList <String> num_list2 = new ArrayList<String>(Arrays.asList("1","2","3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"));
  String repCount2 = "";
  while (num_list2.contains(repCount2) == false) {
      repCount2 = new UiBooster().showTextInputDialog("Number of reps? (Pick between 1 and 20)");
  }
  int repCount = parseInt(repCount2);
  String duration2 = "";
  while (num_list2.contains(duration2) == false){
      duration2 = new UiBooster().showTextInputDialog("Duration (in seconds between 1 and 20)?");
  }
  long duration = parseInt(duration2);
  duration = duration*1000;
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
