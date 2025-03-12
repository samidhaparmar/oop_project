// src/games/MemoryMatchGame.java

package GameEngineProject.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import GameEngineProject.gameengine.Game;
import GameEngineProject.models.Card;
import GameEngineProject.models.Player;

public class MemoryMatchGame implements Game {
    private static final int GRID_SIZE = 2; // Fixed 4x4 grid
    private Card[][] grid;                  // 4x4 grid of cards
    private List<Card> deck;                // Deck with 8 pairs of cards (16 cards total)
    private List<Player> players;
    private Scanner scanner;

    public MemoryMatchGame(Scanner scanner) {
        this.scanner = scanner;
        players = new ArrayList<>();
        deck = new ArrayList<>();
        initializeDeck();
    }

    @Override
    public void initialize() {
        System.out.println("Starting Memory Match Game...");
        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter the name of player " + i + ": ");
            players.add(new Player(scanner.nextLine()));
        }

        Collections.shuffle(deck);
        setupGrid();
    }

    @Override
    public void play() {
        System.out.println("Memory Match Game begins!");

        while (!allPairsMatched()) {
            for (Player player : players) {
                System.out.println("\n" + player.getName() + "'s turn!");
                displayGrid(false);

                System.out.print("Enter row and column for the first card (e.g., 0 1): ");
                int row1 = scanner.nextInt();
                int col1 = scanner.nextInt();
                revealCard(row1, col1);

                System.out.print("Enter row and column for the second card (e.g., 1 2): ");
                int row2 = scanner.nextInt();
                int col2 = scanner.nextInt();
                revealCard(row2, col2);

                if (grid[row1][col1] != null && grid[row2][col2] != null &&
                    grid[row1][col1].getValue().equals(grid[row2][col2].getValue()) &&
                    grid[row1][col1].getSuit().equals(grid[row2][col2].getSuit())) {
                    System.out.println("Match found!");
                    player.incrementScore(1);
                    grid[row1][col1] = null; // Remove matched cards from grid
                    grid[row2][col2] = null;
                } else {
                    System.out.println("No match. Turning cards back over.");
                }

                if (allPairsMatched()) {
                    break;
                }
            }
        }
    }

    @Override
    public void end() {
        Player winner = players.stream().max((p1, p2) -> Integer.compare(p1.getScore(), p2.getScore())).orElse(null);
        System.out.println("Game over! The winner is " + (winner != null ? winner.getName() + " with " + winner.getScore() + " pairs" : "No winner"));
    }

    private void initializeDeck() {
        // Use a fixed set of 8 unique pairs of cards for the 4x4 grid
        // String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        // String[] values = {"2", "3", "4", "5", "6", "7", "8", "9"}; 
        // aiya akhi deck use karine evo logic lakhvano chhe k game khatam thay jay 5 ya 7 step ma 
        String[] suits = {"Hearts", "Diamonds"};
        String[] values = {"2", "3"};
        for (int i = 0; i < 2; i++) {
            deck.add(new Card(suits[i % suits.length], values[i]));
            deck.add(new Card(suits[i % suits.length], values[i])); // Add a pair of each card
        }
    }

    private void setupGrid() {
        // Fill the 4x4 grid with shuffled cards
        grid = new Card[GRID_SIZE][GRID_SIZE];
        int index = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = deck.get(index++);
            }
        }
    }

    private void displayGrid(boolean revealAll) {
        System.out.println("\nCurrent Grid:");
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == null) {
                    System.out.print("X\t"); // Empty slot for matched cards
                } else if (revealAll) {
                    System.out.print(grid[i][j] + "\t");
                } else {
                    System.out.print("[*]\t"); // Hidden cards
                }
            }
            System.out.println();
        }
    }

    private void revealCard(int row, int col) {
        System.out.println("Revealed: " + grid[row][col]);
    }

    private boolean allPairsMatched() {
        for (Card[] row : grid) {
            for (Card card : row) {
                if (card != null) return false; // If any card is still unmatched
            }
        }
        return true;
    }
}