// src/gameengine/GameEngine.java

package GameEngineProject.gameengine;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameEngine {
    private Map<String, Game> games;
    private Scanner scanner;

    public GameEngine(Scanner scanner) {
        this.scanner = scanner;
        games = new HashMap<>();
    }

    public void addGame(String name, Game game) {
        games.put(name.toLowerCase(), game);
    }

    public void start() {
        while (true) {
            System.out.println("Welcome to the Game Engine!");
            System.out.println("Available games:");

            games.keySet().forEach(gameName -> System.out.println("- " + gameName));
            System.out.println("Enter the name of the game you want to play, or type 'exit' to quit:");

            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("exit")) {
                System.out.println("Exiting the Game Engine. Goodbye!");
                break;
            }

            Game selectedGame = games.get(choice);
            if (selectedGame != null) {
                selectedGame.initialize();
                selectedGame.play();
                selectedGame.end();
            } else {
                System.out.println("Invalid game selection. Please try again.");
            }
        }
    }
}