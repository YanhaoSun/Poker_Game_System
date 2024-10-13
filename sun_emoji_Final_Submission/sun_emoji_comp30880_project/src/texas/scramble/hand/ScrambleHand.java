package texas.scramble.hand;

import texas.Deck;
import texas.Hand;
import texas.scramble.deck.Tile;

import java.util.Arrays;

// A hand of Tiles

public class ScrambleHand implements Hand {
    private Tile[] hand;  								// the actual sequence of cards

    private Deck deck; 							// the deck from which the hand is made

    // Constructors

    public ScrambleHand(Deck deck, int numCardsToBeDealt) {
        this.deck = deck;

        hand = new Tile[numCardsToBeDealt];

        for (int i = 0; i < numCardsToBeDealt; i++) {
            setTile(i, (Tile) deck.dealNext());
        }
    }
    public ScrambleHand(Deck deck) {
        this(deck, 2);
    }

    @Override
    public int getValue() {
        int value = Arrays.stream(hand).mapToInt(Tile::value).sum();
        if (hand.length == 7) value += 50;
        return value;
    }

    @Override
    public Tile[] getHand() {
        return hand;
    }

    @Override
    public String toString() {
        String desc = "";
        for (Tile tile : hand) {
            desc += tile + "\n";
        }
        return desc;
    }

    public void setTile(int idx, Tile tile) {
        if (idx >= 0 && idx < hand.length) {
            hand[idx] = tile;
        }
    }



    // Useless methods for Scramble

    // No categories for Scramble hands

    @Override
    public Hand categorize() {
        return null;
    }


    // No discard feature in Scramble

    @Override
    public void throwaway(int cardPos) {}

    @Override
    public Hand discard() {
        return null;
    }

    @Override
    public int getNumDiscarded() {
        return 0;
    }

    // Risk worthiness is implemented in ScrambleComputerClass

    @Override
    public int getRiskWorthiness() {
        return 0;
    }
}
