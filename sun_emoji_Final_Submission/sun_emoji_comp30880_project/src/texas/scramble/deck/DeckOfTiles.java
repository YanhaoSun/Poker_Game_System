package texas.scramble.deck;

import texas.Deck;
import texas.Hand;
import texas.scramble.hand.ScrambleHand;

import java.util.HashMap;
import java.util.Random;

public class DeckOfTiles implements Deck {
    public static final int NUM_TILES = 100;

    private Tile[] deck = new Tile[NUM_TILES];

    private int next = 0; // the next tile to be dealt

    private Random dice = new Random(System.currentTimeMillis());

    private HashMap<String, Integer> storeTiles = new HashMap<>();

    // Constructor

    public DeckOfTiles() {
        initializeTiles();
        reset();
    }
    private void initializeTiles(){
        while (next < 9) deck[next++] = new Tile("A", 1);
        while (next < 11) deck[next++] = new Tile("B", 3);
        while (next < 13) deck[next++] = new Tile("C", 3);
        while (next < 17) deck[next++] = new Tile("D", 2);
        while (next < 29) deck[next++] = new Tile("E", 1);
        while (next < 31) deck[next++] = new Tile("F", 4);
        while (next < 34) deck[next++] = new Tile("G", 2);
        while (next < 36) deck[next++] = new Tile("H", 4);
        while (next < 45) deck[next++] = new Tile("I", 1);
        while (next < 46) deck[next++] = new Tile("J", 8);
        while (next < 47) deck[next++] = new Tile("K", 5);
        while (next < 51) deck[next++] = new Tile("L", 1);
        while (next < 53) deck[next++] = new Tile("M", 3);
        while (next < 59) deck[next++] = new Tile("N", 1);
        while (next < 67) deck[next++] = new Tile("O", 1);
        while (next < 69) deck[next++] = new Tile("P", 3);
        while (next < 70) deck[next++] = new Tile("Q", 10);
        while (next < 76) deck[next++] = new Tile("R", 1);
        while (next < 80) deck[next++] = new Tile("S", 1);
        while (next < 86) deck[next++] = new Tile("T", 1);
        while (next < 90) deck[next++] = new Tile("U", 1);
        while (next < 92) deck[next++] = new Tile("V", 4);
        while (next < 94) deck[next++] = new Tile("W", 4);
        while (next < 95) deck[next++] = new Tile("X", 8);
        while (next < 97) deck[next++] = new Tile("Y", 4);
        while (next < 98) deck[next++] = new Tile("Z", 10);
        while (next < 100) deck[next++] = new Tile(" ", 0);

        storeTiles.put("A", 9);
        storeTiles.put("B", 2);
        storeTiles.put("C", 2);
        storeTiles.put("D", 4);
        storeTiles.put("E", 12);
        storeTiles.put("F", 2);
        storeTiles.put("G", 3);
        storeTiles.put("H", 2);
        storeTiles.put("I", 9);
        storeTiles.put("J", 1);
        storeTiles.put("K", 1);
        storeTiles.put("L", 4);
        storeTiles.put("M", 2);
        storeTiles.put("N", 6);
        storeTiles.put("O", 8);
        storeTiles.put("P", 2);
        storeTiles.put("Q", 1);
        storeTiles.put("R", 6);
        storeTiles.put("S", 4);
        storeTiles.put("T", 6);
        storeTiles.put("U", 4);
        storeTiles.put("V", 2);
        storeTiles.put("W", 2);
        storeTiles.put("X", 1);
        storeTiles.put("Y", 2);
        storeTiles.put("Z", 1);
        storeTiles.put(" ", 2);
    }
    public HashMap<String, Integer> getAllTiles(){
        return storeTiles;
    }

    @Override
    public void reset() {
        next = 0;
        shuffle();
    }

    @Override
    public void shuffle() {
        Tile palm;

        int alpha, beta;

        for (int i = 0; i < NUM_TILES*NUM_TILES; i++) {
            alpha       = Math.abs(dice.nextInt())%NUM_TILES;
            beta        = Math.abs(dice.nextInt())%NUM_TILES;

            palm        = deck[alpha];
            deck[alpha] = deck[beta];
            deck[beta]  = palm;
        }
    }

    @Override
    public Tile dealNext() {
        if (next >= NUM_TILES)
            return new Tile("^",0);  // deck is empty
        else
            return deck[next++];
    }

    @Override
    public ScrambleHand dealHand() {
        return new ScrambleHand(this);
    }

    @Override
    public Hand dealHand(int numCardsToBeDealt) {
        return new ScrambleHand(this, numCardsToBeDealt);
    }
}
