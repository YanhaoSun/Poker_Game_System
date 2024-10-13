package texas.hold_em;

//import org.junit.test;
import org.junit.jupiter.api.Test;
import poker.*;
import texas.Hand;
import texas.TexasPlayer;

import java.util.ArrayList;
import java.util.List;

public class TestPrintGame {
    @Test
    public void testPrintPublicCard() {
        DeckOfCards deck = new DeckOfCards();
        deck.reset();
        Hand communityCards;
        ArrayList<TexasPlayer> texasPlayers = new ArrayList<>();
        PotOfMoney pot = new PotOfMoney();
        communityCards=deck.dealHand(3);
        List<Card> cards = List.of((Card[]) communityCards.getHand());
//        hand community = new PokerHand();
////        DeckOfCards deck;
//        ArrayList<Card> communityCards = new ArrayList<>();
//        communityCards.add(new NumberCard("Ace", "hearts", 1, 14));
//        communityCards.add(new NumberCard("Deuce", "diamonds", 2));
//        communityCards.add(new NumberCard("Three", "clubs", 3));
//        print_game printPublic = new print_game(texasPlayers, deck, pot, cards);
//        printPublic.printPublicCard(cards);

    }
    @Test
    public void testCardPrinter() {
        DeckOfCards deck = new DeckOfCards();
        deck.reset();
        String[] names = {"Human", "Tom", "Dick", "Harry", "Yan", "wang", "Sun"};
        HoldEmController controller = new HoldEmController();
        controller.setUp(names, 5, 1);
        ArrayList<TexasPlayer> texasPlayers = controller.texasPlayers;
        texasPlayers.get(1).setDealer(true);
        for(Player player : texasPlayers){
            player.dealTo(deck);
        }
//        texasPlayers.get(1).fold();
//        texasPlayers.get(2).fold();
//        for(player player : texasPlayers){
//            System.out.println("player name = "+player.getName());
//            for(Card card: player.getHand().getHand()){
//                System.out.println("Card name = "+card.getName());
//            }
//        }
        Hand communityCards;
        ArrayList<PotOfMoney> pots = new ArrayList<>();
//        PotOfMoney pot = new PotOfMoney();
        communityCards=deck.dealHand(4);
        List<Card> cards = List.of((Card[]) communityCards.getHand());

        HoldEmComputerPlayer player=  new HoldEmComputerPlayer("ad", 0, 0);

        PrintHoldEmGame printPublic = new PrintHoldEmGame(texasPlayers, pots, cards);
        printPublic.cardPrinter(false);

    }
}
