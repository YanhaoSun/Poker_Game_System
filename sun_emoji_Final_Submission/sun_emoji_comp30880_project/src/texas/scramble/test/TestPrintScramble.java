package texas.scramble.test;

import org.junit.jupiter.api.Test;
import poker.PotOfMoney;
import texas.TexasPlayer;
import texas.hold_em.HoldEmComputerPlayer;
import texas.Hand;
import texas.hold_em.HoldEmController;
import texas.scramble.deck.DeckOfTiles;
import texas.scramble.deck.Tile;
import texas.scramble.print_game.PrintScramble;

import java.util.ArrayList;
import java.util.List;

public class TestPrintScramble {
    @Test
    public void testCardPrinter(){
        DeckOfTiles Tiles = new DeckOfTiles();
        Tiles.reset();
        String[] names = {"Human", "Tom", "Dick", "Harry", "Yan", "wang", "Sun"};
        HoldEmController controller = new HoldEmController();
        controller.setUp(names, 5, 1);
        ArrayList<TexasPlayer> texasPlayers = controller.texasPlayers;
        texasPlayers.get(1).setDealer(true);
        for(TexasPlayer player : texasPlayers){
            player.dealTo(Tiles);
//            player.setOnTurn(true);
        }
        texasPlayers.get(1).setOnTurn(true);
        texasPlayers.get(2).setOnTurn(false);
        texasPlayers.get(3).setOnTurn(true);
        Hand communityCards;
        ArrayList<PotOfMoney> pots = new ArrayList<>();
//        PotOfMoney pot = new PotOfMoney();
        communityCards=Tiles.dealHand(5);
        List<Tile> cards = List.of((Tile[]) communityCards.getHand());

        HoldEmComputerPlayer player=  new HoldEmComputerPlayer("ad", 0, 0);

        PrintScramble printPublic = new PrintScramble(texasPlayers, pots, cards);
        printPublic.cardPrinter(false);
    }
}
