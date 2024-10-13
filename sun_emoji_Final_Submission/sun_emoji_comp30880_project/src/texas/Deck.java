package texas;

import texas.scramble.hand.HandElement;

// deck interface for use with polymorphism

public interface Deck {
    void reset();
    void shuffle();
    HandElement dealNext();
    Hand dealHand();

    Hand dealHand(int numCardsToBeDealt);
}
