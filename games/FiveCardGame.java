// src/games/FiveCardGame.java

package GameEngineProject.games;

import GameEngineProject.gameengine.Game;
import GameEngineProject.models.Card;
import GameEngineProject.models.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FiveCardGame implements Game {
    private List<Player> players;
    private List<Card> deck;
    private Scanner scanner;

    public FiveCardGame(Scanner scanner) {
        this.scanner = scanner;
        players = new ArrayList<>();
        deck = new ArrayList<>();
        initializeDeck();
    }

    @Override
    public void initialize() {
        System.out.println("Starting Five Card Game...");
        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter the name of player " + i + ": ");
            players.add(new Player(scanner.nextLine()));
        }

        Collections.shuffle(deck);

        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                player.addCard(deck.remove(0));
            }
        }
    }

    @Override
    public void play() {
        System.out.println("Let the game begin!");

        boolean allHandsEmpty = false;
        while (!allHandsEmpty) { // Continue while not all hands are empty
            allHandsEmpty = true; // Assume all hands are empty initially
            for (Player player : players) {
                if (player.getHand().isEmpty()) {
                    // Skip players with no cards left
                    continue;
                }

                allHandsEmpty = false; // Found at least one player with cards

                System.out.println(player.getName() + "'s turn. Your hand: " + player.getHand());
                System.out.print("Enter the index of the card to play (0 to " + (player.getHand().size() - 1) + "): ");

                int cardIndex = scanner.nextInt();
                while (cardIndex < 0 || cardIndex >= player.getHand().size()) {
                    System.out.println("Invalid index! Try again.");
                    System.out.print(
                            "Enter the index of the card to play (0 to " + (player.getHand().size() - 1) + "): ");
                    cardIndex = scanner.nextInt();
                }

                Card playedCard = player.playCard(cardIndex);
                System.out.println(player.getName() + " played: " + playedCard);
                player.incrementScore(calculateScore(playedCard));
            }
        }
    }

    @Override
    public void end() {
        Player winner = players.stream().max((p1, p2) -> Integer.compare(p1.getScore(), p2.getScore())).orElse(null);
        System.out.println("Game over! The winner is "
                + (winner != null ? winner.getName() + " with " + winner.getScore() + " points" : "No winner"));
    }

    private void initializeDeck() {
        String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };
        String[] values = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
        for (String suit : suits) {
            for (String value : values) {
                deck.add(new Card(suit, value));
            }
        }
    }

    private int calculateScore(Card card) {
        String value = card.getValue();
        switch (value) {
            case "A":
                return 15;
            case "K":
            case "Q":
            case "J":
                return 10;
            default:
                return Integer.parseInt(value);
        }
    }
}