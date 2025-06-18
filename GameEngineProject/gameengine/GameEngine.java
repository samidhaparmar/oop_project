// src/gameengine/GameEngine.java

package GameEngineProject.gameengine;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class GameEngine {
    // Modularity: The 'games' map holds independent game modules.
    
    private Map<String, Game> games; //generics
    private Scanner scanner;

    public GameEngine(Scanner scanner) {
        this.scanner = scanner;
        ///// Instantiates the map to store Game objects.
        /// // Modularity: Initialize the container to store game modules.
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
                try {
                    selectedGame.initialize(); // inclusion polymorphism: call resolves to actual subtype
                    selectedGame.play();
                    selectedGame.end();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter the correct format.");
                    scanner.nextLine();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Index out of range. Please try within bounds.");
                } catch (InvalidMoveException e) {
                    System.out.println("Move error: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid game selection. Please try again.");
            }
        }
    }
}