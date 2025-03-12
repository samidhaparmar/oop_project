// src/models/Player.java

package GameEngineProject.models;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;
    private int score;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public Card playCard(int index) {
        return (index >= 0 && index < hand.size()) ? hand.remove(index) : null;
    }

    public List<Card> getHand() {
        return hand;
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