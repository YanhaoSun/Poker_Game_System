package texas.scramble.test;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import texas.scramble.deck.DeckOfTiles;

import java.util.HashMap;

public class TestDeckOfTiles {
    @Test
    public void testCorrectNumOfTiles() {
        DeckOfTiles deck = new DeckOfTiles();
        HashMap<String, Integer> letters = deck.getAllTiles();

        for (int i = 0; i < 100; i++) {
            String next = deck.dealNext().toString();
            letters.put(next, letters.get(next) - 1);
        }

        for (int count : letters.values()) {
            if (count != 0) Assertions.fail();
        }
    }
}
