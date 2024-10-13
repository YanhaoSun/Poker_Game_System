package blackjack;
import poker.Card;
import poker.FaceCard;
import poker.NumberCard;

import java.util.Random;

public class DeckOfCards {
    public static final String[] suits = {"hearts", "diamonds", "clubs", "spades"};

    public static final int NUMCARDS = 52;  // number of cards in a deck
    private Random dice = new Random(System.currentTimeMillis());
    private Card[] deck = new Card[NUMCARDS]; // sequence of cards to be dealt
    private int next = 0; //index of next card to be dealt

    /*--------------------Constructor--------------------------*/
    public DeckOfCards() {
        for (int i = 0; i < suits.length; i++) {
            deck[next++] = new NumberCard("Ace", suits[i], 1, 11);
            deck[next++] = new NumberCard("Deuce", suits[i], 2);
            deck[next++] = new NumberCard("Three", suits[i], 3);
            deck[next++] = new NumberCard("Four", suits[i], 4);
            deck[next++] = new NumberCard("Five", suits[i], 5);
            deck[next++] = new NumberCard("Six", suits[i], 6);
            deck[next++] = new NumberCard("Seven", suits[i], 7);
            deck[next++] = new NumberCard("Eight", suits[i], 8);
            deck[next++] = new NumberCard("Nine", suits[i], 9);
            deck[next++] = new NumberCard("Ten", suits[i], 10);
            deck[next++] = new FaceCard("Jack", suits[i], 10);
            deck[next++] = new FaceCard("Queen", suits[i], 10);
            deck[next++] = new FaceCard("King", suits[i], 10);
        }

        reset();
    }

    /*---------------Reset internal deck state--------------------------*/

    public void reset() {
        next = 0;
        shuffle();
    }

    public void shuffle() {
        Card palm = null;

        int alpha = 0, beta = 0;

        for (int i = 0; i < NUMCARDS*NUMCARDS; i++) {
            alpha       = Math.abs(dice.nextInt())%NUMCARDS;
            beta        = Math.abs(dice.nextInt())%NUMCARDS;

            palm        = deck[alpha];
            deck[alpha] = deck[beta];
            deck[beta]  = palm;
        }
    }

    /*----------------Return next card in deck--------------------------*/

    public Card dealNext() {
        if (next >= NUMCARDS)
            return new FaceCard("Joker", "no suit", 0);  // deck is empty
        else
            return deck[next++];
    }


    public BlackjackHand dealBlackJackHand() {
        return new BlackjackHand(this);
    }
}
