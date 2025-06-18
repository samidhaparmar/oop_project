// FiveCardGame.java – extend base class to demonstrate inheritance
package GameEngineProject.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import GameEngineProject.base.GameBase;
import GameEngineProject.gameengine.CardDealer;
import GameEngineProject.gameengine.Game;
import GameEngineProject.models.Card;
import GameEngineProject.models.Player;
import GameEngineProject.gameengine.InvalidMoveException;

public class FiveCardGame extends GameBase implements Game {
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
        announceStart(); // From GameBase
        System.out.println("Starting Five Card Game...");
        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter the name of player " + i + ": ");
            players.add(new Player(scanner.nextLine()));
        }

        // Shuffle the deck once
        Collections.shuffle(deck);

        // Deal 5 cards to each player via CardDealer overload
        for (Player player : players) {
        // Grab the next 5 cards (shuffling already done)
        List<Card> hand = CardDealer.dealCards(deck, 5);
        // Add to player's hand
        hand.forEach(player::addCard);
        // Remove dealt cards from the deck
        deck.removeAll(hand);
        } 
    }

    @Override
    public void play() {
        System.out.println("Let the game begin!");
        boolean allHandsEmpty = false;
        while (!allHandsEmpty) {
            allHandsEmpty = true;
            for (Player player : players) {
                if (player.getHand().isEmpty()) continue;
                allHandsEmpty = false;

                System.out.println(player.getName() + "'s turn. Your hand: " + player.getHand());
                System.out.print("Enter the index of the card to play (0 to " + (player.getHand().size() - 1) + "): ");

                int cardIndex = scanner.nextInt();
                while (cardIndex < 0 || cardIndex >= player.getHand().size()) {
                    System.out.println("Invalid index! Try again.");
                    System.out.print("Enter the index of the card to play (0 to " + (player.getHand().size() - 1) + "): ");
                    cardIndex = scanner.nextInt();
                }

                Card playedCard = player.playCard(cardIndex);
                if (playedCard == null) {
                    throw new InvalidMoveException("Invalid card index: " + cardIndex);
                }
                System.out.println(player.getName() + " played: " + playedCard);
                player.incrementScore(calculateScore(playedCard));
            }
        }
    }

    @Override
    public void end() {
        Player winner = players.stream().max((p1, p2) -> Integer.compare(p1.getScore(), p2.getScore())).orElse(null);
        System.out.println("Game over! The winner is " + (winner != null ? winner.getName() + " with " + winner.getScore() + " points" : "No winner"));
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
            case "A": return 15;
            case "K": case "Q": case "J": return 10;
            default: return Integer.parseInt(value);
        }
    }
}
