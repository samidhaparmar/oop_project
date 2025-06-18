// src/models/Player.java

package GameEngineProject.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    //Encapsulation: bundling state and behavior together
    private String name;
    private List<Card> hand;
    private int score;

    //// Constructor: Encapsulation – it initializes the player's state.
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.score = 0;
    }
    ////// Public accessor methods: Encapsulation – they provide controlled access to the private fields.
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    // New Overloaded Method
    public void addCard(String suit, String value) {
        hand.add(new Card(suit, value));
    }
    
    public Card playCard(int index) {
        return (index >= 0 && index < hand.size()) ? hand.remove(index) : null;
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand); // Hides internals: prevents external mutation;
    }

    public void incrementScore(int points) {
        score += points;
    }

        // Overloaded method to demonstrate method overloading (and autoboxing in action)
    public void incrementScore(Integer points) {
        // This method simply calls the int version; Java autoboxing will work here.
        incrementScore(points.intValue());
    }

    
}