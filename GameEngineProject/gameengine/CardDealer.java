package GameEngineProject.gameengine;

import GameEngineProject.models.Card;
import java.util.Collections;
import java.util.List;

public class CardDealer {

    // Deal a fixed number of cards, preserving deck order
    public static List<Card> dealCards(List<Card> deck, int count) {
        return dealCards(deck, count, false);
    }

    // Deal and optionally shuffle before dealing
    public static List<Card> dealCards(List<Card> deck, int count, boolean shuffle) {
        if (shuffle) Collections.shuffle(deck);
        return deck.subList(0, Math.min(count, deck.size()));
    }
}