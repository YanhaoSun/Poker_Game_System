package texas.scramble.test;

import org.junit.jupiter.api.Test;
import texas.scramble.deck.DeckOfTiles;
import texas.scramble.deck.Tile;
import texas.scramble.player.ScrambleHumanPlayer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestScrambleHumanPlayer {
    @Test
    public void testSubmitWord(){

        ScrambleHumanPlayer player = new ScrambleHumanPlayer("qwe",100,0);
        List<Tile> communityTiles = new ArrayList<>();
        DeckOfTiles deck = new DeckOfTiles();
        communityTiles.add(new Tile("T",1));
        communityTiles.add(new Tile("E",1));
        communityTiles.add(new Tile("E",1));
        communityTiles.add(new Tile("N",1));
        communityTiles.add(new Tile("A",1));
        player.dealTo(deck);
        System.out.println("CommunityTiles"+communityTiles);
// Set up the input for the user
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("TEA\nY\nn\n".getBytes());
        System.setIn(in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // Call the submitWord method and get the result
        int result1 = player.submitWord(communityTiles);

        // Check if the result is correct
        assertEquals(3, result1);

        // Restore the original System.in
        System.setIn(sysInBackup);
    }
}
