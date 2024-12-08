package GameEngineProject;

// src/Main.java

import GameEngineProject.gameengine.GameEngine;
import GameEngineProject.games.FiveCardGame;
import GameEngineProject.games.MemoryMatchGame;
import GameEngineProject.games.ElementalBattleGame;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            GameEngine engine = new GameEngine(scanner);

            // Add each game to the Game Engine
            engine.addGame("Five Card Game", new FiveCardGame(scanner));
            engine.addGame("Memory Match", new MemoryMatchGame(scanner));
            engine.addGame("Elemental Battle", new ElementalBattleGame(scanner));

            // Start the Game Engine
            engine.start();
        }
    }
}