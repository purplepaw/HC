import g4p_controls.*;
import uibooster.*;
import uibooster.model.*;

// Constants
int Y_AXIS = 1;
int X_AXIS = 2;
GButton start;
UiBooster booster;

// Global Variable
int state = 0; // 0 for main menu, 1 create account, 2 user dashboard
int oldState = -1; // to transition display

/**
  *  Sets up the canvas and the objects that the program depends on to run.
  **/
void setup() {
  
  size(displayWidth, displayWidth);
  
  noStroke();
  frameRate(30); // limit framerate to 30 frames per second to reduce impact on system
  G4P.setGlobalColorScheme(GCScheme.CYAN_SCHEME);
  
  start = new GButton(this, 3.5 * width / 8, 1.5 * height / 8, width / 8, 0.5 * height / 8);
  start.setVisible(false);
  textAlign(CENTER, CENTER);
}

/**
  * Draws the frames to the screen.
  **/
void draw() {
  textAlign(CENTER, CENTER);
  setGradient(0, 0, width, height, color(167, 242, 240), color(0, 102, 153), X_AXIS);
  if (state != oldState) {
    oldState = state;
    start.setVisible(false);
    
    if (state == 0) {
        // Button configuration
        start.setVisible(true);
        start.setText("Start");
        start.setLocalColorScheme(GCScheme.BLUE_SCHEME);
        start.addEventHandler(this, "button_test_click");
    } else if (state == 1) {
       // TODO: check if user already exists, otherwise prompt for name
       String name = new UiBooster().showTextInputDialog("What is your name?");
       // TODO: create User entity
       state = 2;
    }
  }
  
  if (state == 2) {
       // Display User Dashboard TODO
       textSize(75);
       text("Hello, " + "username" + "!", width/2, height/12); // TODO: get username from entity
       // TODO: display options
       line(width/8, 1.5 * height/12 + 1, 7 * width/8, 1.5 * height/12 + 3);
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
public void button_test_click(GButton button, GEvent event) {
   state = 1; 
}
