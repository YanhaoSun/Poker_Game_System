package texas.scramble.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import texas.scramble.dictionary.DictionaryTrie;
import texas.scramble.player.ScrambleComputerPlayer;

import static org.junit.Assert.assertFalse;

public class TestDictionary {
    @Test
    public void testLearnWord() {
        ScrambleComputerPlayer com = new ScrambleComputerPlayer("TEST", 100, 0);

        Assertions.assertTrue(com.knowsWord("APPLE"));
        Assertions.assertFalse(com.knowsWord("AB"));

        com.learnWord("AB");

        Assertions.assertTrue(com.knowsWord("AB"));
    }

    @Test
    public void testDictionaryNotLoadingErrors() {
        DictionaryTrie dict = new DictionaryTrie("resources/test.txt");

        Assertions.assertFalse(dict.isValidWord("ADH43"), "Erroneous word not loaded in");
        Assertions.assertTrue(dict.isValidWord("WREER"), "Trailing whitespace trimmed");
    }
}
