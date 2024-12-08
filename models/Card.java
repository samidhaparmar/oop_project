// src/models/Card.java

package GameEngineProject.models;

public class Card {
    private String suit;
    private String value;
    private String element; // Used in Elemental Battle Game
    private int power;      // Used in Elemental Battle Game
    private String type;    // "attack" or "defense" in Elemental Battle Game

    // Constructor for Five Card Game and Memory Match Game
    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    // Constructor for Elemental Battle Game
    public Card(String element, int power, String type) {
        this.element = element;
        this.power = power;
        this.type = type;
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public String getElement() {
        return element;
    }

    public int getPower() {
        return power;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        if (suit != null && value != null) {
            return value + " of " + suit;
        } else {
            return type + " card - Element: " + element + ", Power: " + power;
        }
    }
}