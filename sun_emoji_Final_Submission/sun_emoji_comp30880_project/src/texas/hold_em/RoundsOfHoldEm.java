
package texas.hold_em;

import poker.*;
import texas.RoundOfTexas;
import texas.TexasComputerPlayer;
import texas.TexasPlayer;

import java.util.*;

// This package provides classes necessary for implementing a game system for playing poker

// A RoundOfPoker is a single round/deal in a game
// A PokerGame is a sequence of RoundOfPoker's

public class RoundsOfHoldEm extends RoundOfTexas {
    private ArrayList<TexasPlayer> roundPlayers;

    private List<Card> communityCards;

    public RoundsOfHoldEm(DeckOfCards deck, ArrayList<TexasPlayer> texasPlayers, int dealerIndex) {
        super(deck, texasPlayers, dealerIndex);
        this.roundPlayers = texasPlayers;
        this.communityCards = new ArrayList<>();

        this.printGame = new PrintHoldEmGame(texasPlayers, pots, communityCards);

        initComputerPlayerWithCommunityCards(communityCards);
    }

    private void initComputerPlayerWithCommunityCards(List<Card> communityCards) {
        for (TexasPlayer player : roundPlayers) {
            if (player instanceof HoldEmComputerPlayer) {
                ((TexasComputerPlayer) player).setCommunityElements(communityCards);
            }
        }
    }

    @Override
    //compare the hand value and decide who win
    public void showDown() {
        if (onePlayerLeft()) {
            for (TexasPlayer player : roundPlayers) {
                if (!player.hasFolded()) {
                    for (PotOfMoney pot : pots) {
                        player.takePot(pot);
                    }
                }
            }
        } else {
            HashMap<Integer, Integer> valueRank = new HashMap<>();
            // calculate handValue for each player
            for (int i = 0; i < roundPlayers.size(); i++) {
                TexasPlayer player = roundPlayers.get(i);
                if (!player.hasFolded()) {
                    Card[] communityCardsArr = new Card[communityCards.size()];
                    player.findBestHand(communityCards.toArray(communityCardsArr), (DeckOfCards) deck);
                    System.out.println(player.getCurrentBestHand());
                    int handValue = player.getCurrentBestHand().getValue();
                    valueRank.put(i, handValue);
                }
            }
            // find who has the largest handValue
            for (int i = pots.size() - 1; i >= 0; i--) {
                PotOfMoney pot = pots.get(i);
                int potAmount = pot.getTotal();
                HashMap<Integer, Integer> winners = new HashMap<>();
                int highestHandValue = -1;
                // Find the eligible winners for this pot based on hand value
                for (int playerId : valueRank.keySet()) {
                    if (pot.getPlayerIds().contains(playerId)) {
                        int handValue = valueRank.get(playerId);
                        if (handValue > highestHandValue) {
                            highestHandValue = handValue;
                            winners.clear();
                            winners.put(playerId, handValue);
                        } else if (handValue == highestHandValue) {
                            winners.put(playerId, handValue);
                        }
                    }
                }
                // Divide the pot amount equally among the winners
                if (!winners.isEmpty()) {
                    int splitAmount = potAmount / winners.size();
                    for (int winnerId : winners.keySet()) {
                        TexasPlayer winner = getPlayerById(roundPlayers, winnerId);
                        winner.winFromPot(splitAmount, pot);
                    }
                }
            }
        }
    }

    @Override
    protected void dealCommunityElements(int numCardsToBeDealt) {
        for (int i = 0; i < numCardsToBeDealt; i++) {
            communityCards.add((Card) deck.dealNext());
        }
    }
}