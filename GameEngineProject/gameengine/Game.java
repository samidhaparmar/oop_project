// src/gameengine/Game.java


//// Demonstrates abstraction: It defines what a game must do without specifying how.
package GameEngineProject.gameengine;

public interface Game {
    void initialize();
    void play();
    void end(); 
}

