package GameEngineProject;

import java.util.Scanner;

import GameEngineProject.gameengine.Game;
import GameEngineProject.gameengine.GameEngine;
import GameEngineProject.games.ElementalBattleGame;
import GameEngineProject.games.FiveCardGame;
import GameEngineProject.games.MemoryMatchGame;

public class Main {
    public static void main(String[] args) {
        
        try (Scanner scanner = new Scanner(System.in)) {
            GameEngine engine = new GameEngine(scanner);

            // Add each game to the Game Engine
            engine.addGame("Five Card Game", new FiveCardGame(scanner));
            engine.addGame("Memory Match", new MemoryMatchGame(scanner));
            engine.addGame("Elemental Battle", new ElementalBattleGame(scanner));


            // --- Dynamic-binding proof (client-side demo) ---
            Game demo = new FiveCardGame(scanner);
            demo.initialize(); // calls FiveCardGame.initialize()
            demo.play();       // calls FiveCardGame.play()
            demo.end();        // calls FiveCardGame.end()
            System.out.println("✅ Dynamic binding confirmed.\n");
            
            // Start the Game Engine
            engine.start();
        }
    }
}