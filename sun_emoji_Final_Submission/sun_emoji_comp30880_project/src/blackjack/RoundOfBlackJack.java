package blackjack;
import poker.Card;
import texas.TexasPlayer;
import texas.hold_em.HoldEmHumanPlayer;

import java.util.Scanner;

public class RoundOfBlackJack {
    private DeckOfCards deck;
    private Player[] players;

    //call deal() to remove the player from players list who isBankrupt
    public RoundOfBlackJack(DeckOfCards deck, Player[] players) {
        this.deck = deck;
        this.players = players;
        deal();
    }

    public void play() {
        //dealer move first
        DealerPlayer dealer = (DealerPlayer) players[players.length - 1];
        Card faceUpCard = dealer.showOneCard();


        for (Player player : players) {
            if (player == null) {
                continue;
            }
            promptPlayerToContinue(player);
            // player without dealer
            if (!(player instanceof DealerPlayer)) {
                switch (player.getClass().getSimpleName()) {
                    case "HumanPlayer" -> {
                        System.out.println("Dealer's card: (" + faceUpCard.getName() + " of " + faceUpCard.getSuit() + ")");
                        player.placeBet(-1);
                    }
                    case "ComputerPlayer"-> {
                        ((ComputerPlayer) player).setDealerCard(faceUpCard);
                        //all in if less than 5
                        if (player.getBank() < 5) {
                            player.placeBet(player.getBank());
                        } else {
                            player.placeBet(5);
                        }
                    }
                    default-> {
                    }
                }
                //player choose actions
                player.takeTurn(faceUpCard);
            }
        }
        // after every player move, dealer move
        dealer.takeTurn(faceUpCard);
        for (Player player : players) {
            if (player == null) {
                continue;
            }
            if (!(player instanceof DealerPlayer) ) {
                for (BlackjackHand hand : player.getHands()) {
                    if (hand.isBusted()) {
                        //player busted
                        System.out.println(player.getName() + " loss " + hand.getStake() + " bet");
                    } else if (hand.hasSurrendered()) {
                        //player surrendered
                        player.winBet(hand.getStake() / 2);
                        System.out.println(player.getName() + " surrendered, return " + hand.getStake() / 2 + " bet");
                    } else if (dealer.getHand().isBusted() || hand.getValue() > dealer.getHand().getValue() || hand.getNumCardsInHand() == 5) {
                        //player win
                        player.winBet(hand.getStake() * 2);
                        System.out.println(player.getName() + " win " + hand.getStake() + " bet");
                    } else if (hand.getValue() == dealer.getHand().getValue()) {
                        //draw
                        player.winBet(hand.getStake());
                        System.out.println(player.getName() + " return " + hand.getStake() + " bet back");
                    } else {
                        //player loss
                        System.out.println(player.getName() + " loss " + hand.getStake() + " bet");
                        hand.setStake(0);
                    }
                }
                //print current bank
                System.out.println("> "+player.getName() + "'s current bank: " + player.getBank() + " bet");
            }

        }
    }

    // If player is human, prompts them to press key to continue to prevent last player from seeing next player's hand.

    private void promptPlayerToContinue(Player currentPlayer) {
        System.out.println("\n>> " + currentPlayer.getName() + "'s turn!\n");
        if (currentPlayer instanceof HumanPlayer) {
            System.out.print("> Switch to " + currentPlayer.getName() + " then press any key to continue.  ");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }
    }

    /*-----------Restart game and deal all remaining players cards--------------------------*/

    public void deal() {
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                if (players[i].isBankrupt()) {
                    removePlayer(i);
                } else {
                    players[i].dealTo(deck);
                }
            }
        }
    }

    /*------------Modifier-------------------*/

    public void removePlayer(int num) {
        if (num >= 0 && num < players.length) {
            System.out.println("\n> " + players[num].getName() + " leaves the game.\n");

            players[num] = null;
        }
    }
}
