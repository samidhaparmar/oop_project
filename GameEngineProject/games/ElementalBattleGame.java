// src/games/ElementalBattleGame.java

package GameEngineProject.games;

import GameEngineProject.gameengine.CardDealer;
import GameEngineProject.gameengine.Game;
import GameEngineProject.models.Card;
import GameEngineProject.models.Player;
import GameEngineProject.gameengine.InvalidMoveException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ElementalBattleGame implements Game {
    private List<Player> players;
    private List<Card> deck;
    private Scanner scanner;

    public ElementalBattleGame(Scanner scanner) {
        this.scanner = scanner;
        players = new ArrayList<>();
        deck = new ArrayList<>();
        initializeDeck();
    }

    @Override
    public void initialize() {
        System.out.println("Starting Elemental Battle Game...");
        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

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
        System.out.println("Let the Elemental Battle begin!");

        while (players.get(0).getHand().size() > 0) { // Play until players have cards
            for (int i = 0; i < players.size(); i++) {
                Player attacker = players.get(i);
                Player defender = players.get((i + 1) % players.size());


                
                System.out.println(attacker.getName() + "'s turn to attack! Your hand: " + attacker.getHand());
                System.out.print("Enter the index of the card to attack with (0 to " + (attacker.getHand().size() - 1) + "): ");
                int attackIndex = scanner.nextInt();
                Card attackCard = attacker.playCard(attackIndex);
                if (attackCard == null) {
                    throw new InvalidMoveException("Invalid attack card index: " + attackIndex);
                }

                System.out.println(defender.getName() + "'s turn to defend! Your hand: " + defender.getHand());
                System.out.print("Enter the index of the card to defend with (0 to " + (defender.getHand().size() - 1) + "): ");
                int defendIndex = scanner.nextInt();
                Card defendCard = defender.playCard(defendIndex);
                if (defendCard == null) {
                    throw new InvalidMoveException("Invalid defense card index: " + defendIndex);
                }

                int result = calculateBattleOutcome(attackCard, defendCard);
                if (result > 0) {
                    System.out.println(attacker.getName() + " wins the battle and gains " + result + " points.");
                    attacker.incrementScore(result);
                } else if (result < 0) {
                    System.out.println(defender.getName() + " wins the battle and gains " + Math.abs(result) + " points.");
                    defender.incrementScore(Math.abs(result));
                } else {
                    System.out.println("It's a tie!");
                }

                if (attacker.getHand().isEmpty() || defender.getHand().isEmpty()) break;
            }
        }
    }

    @Override
    public void end() {
        Player winner = players.stream().max((p1, p2) -> Integer.compare(p1.getScore(), p2.getScore())).orElse(null);
        System.out.println("Game over! The winner is " + (winner != null ? winner.getName() + " with " + winner.getScore() + " points" : "No winner"));
    }

    private void initializeDeck() {
        String[] elements = {"Fire", "Water", "Grass"};
        String[] types = {"attack", "defense"};
        for (String element : elements) {
            for (String type : types) {
                for (int power = 1; power <= 5; power++) {
                    deck.add(new Card(element, power, type));
                }
            }
        }
    }

    private int calculateBattleOutcome(Card attack, Card defense) {
        if (attack.getElement().equals("Fire") && defense.getElement().equals("Grass") ||
            attack.getElement().equals("Water") && defense.getElement().equals("Fire") ||
            attack.getElement().equals("Grass") && defense.getElement().equals("Water")) {
            return attack.getPower();
        } else if (defense.getElement().equals("Fire") && attack.getElement().equals("Grass") ||
                   defense.getElement().equals("Water") && attack.getElement().equals("Fire") ||
                   defense.getElement().equals("Grass") && attack.getElement().equals("Water")) {
            return -defense.getPower();
        } else {
            return 0;
        }
    }
}