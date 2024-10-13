package texas.scramble.controller;

import poker.PotOfMoney;
import texas.RoundOfTexas;
import texas.Rounds;
import texas.TexasComputerPlayer;
import texas.TexasPlayer;
import texas.hold_em.HoldEmComputerPlayer;
import texas.hold_em.HoldEmHumanPlayer;
import texas.scramble.deck.*;
import texas.scramble.player.ScrambleComputerPlayer;
import texas.scramble.player.ScrambleHumanPlayer;
import texas.scramble.print_game.PrintScramble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundOfScramble extends RoundOfTexas {
    private ArrayList<TexasPlayer> roundPlayers;
    private List<Tile> communityTiles;

    public RoundOfScramble(DeckOfTiles deck, ArrayList<TexasPlayer> texasPlayers, int dealerIndex) {
        super(deck, texasPlayers, dealerIndex);
        this.roundPlayers = texasPlayers;
        this.communityTiles = new ArrayList<>();
        this.printGame = new PrintScramble(texasPlayers, pots, communityTiles);
        initComputerPlayerWithCommunityTiles(communityTiles);
    }
    // initialize communityTiles for ComputerPlayers
    private void initComputerPlayerWithCommunityTiles(List<Tile> communityTiles) {
        for (TexasPlayer player : roundPlayers) {
            if (player instanceof HoldEmComputerPlayer) {
                ((TexasComputerPlayer) player).setCommunityElements(communityTiles);
            }
        }
    }

    @Override
    public void showDown() {
        // If only one player remaining, this player is the winner
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
            List<String> newWords = new ArrayList<>();
            // calculate handValue for each player
            for (int i = 0; i < roundPlayers.size(); i++) {
                TexasPlayer player = roundPlayers.get(i);
                if (!player.hasFolded()) {
                    promptPlayerToContinue(player);
                    //get words and final score from human player
                    if (player instanceof ScrambleHumanPlayer p) {
                        valueRank.put(i, p.submitWord(communityTiles));
                        newWords.addAll(p.getWords());
                        System.out.println("--------"+p.getName()+" says: My total Score is "+valueRank.get(i)+"\n");
                    //get words and final score from computer player
                    }else if(player instanceof ScrambleComputerPlayer p) {
                        String[] playerHand = combineToString(communityTiles, (Tile[]) p.getHand().getHand());
                        HashMap<String, Integer> CPUWords = p.submitWords(playerHand);
                        int handValue = 0;
                        int letterCount=0;
                        for (Map.Entry<String, Integer> entry : CPUWords.entrySet()) {
                            System.out.println(p.getName()+" submitted word \"" + entry.getKey() + "\" Value = " + entry.getValue());
                            newWords.add(entry.getKey());
                            handValue+=entry.getValue();
                            letterCount+=entry.getKey().length();
                        }
                        // if used all the Tiles
                        if(CPUWords.size() > 1 && letterCount == 7){
                            handValue += 50;
                        }
                        // print final score
                        valueRank.put(i, handValue);
                        System.out.println("--------"+p.getName()+" says: My total Score is "+handValue+"\n");
                    }
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
            // let computer player learn the words not in their dictionary
            System.out.println("\n");
            updatePlayerDictionary(newWords);
        }
    }

    @Override
    public void roundMove(Rounds currentRound) {
        //decide who move first
        int currentIndex = firstMovePlayerIndex(currentRound);
        int activePlayer = 0;
        for (TexasPlayer player : roundPlayers) {
            if (!player.hasFolded()) {
                activePlayer++;
            }
        }
        roundPlayers.get(currentIndex).setDeck(deck);
        //loop until every one called or folded
        while (!onePlayerLeft() && !ActionClosed()) {
            TexasPlayer currentPlayer = roundPlayers.get(currentIndex);
            if (!currentPlayer.hasFolded() && !currentPlayer.isAllIn()) {
                delay(DELAY_BETWEEN_ACTIONS);
                currentPlayer.setOnTurn(true);
                if (currentPlayer instanceof TexasComputerPlayer)
                    ((TexasComputerPlayer) currentPlayer).setCommunityElements(communityTiles);
                promptPlayerToContinue(currentPlayer);
                printGame.table(currentRound);
                currentPlayer.nextAction(getActivePot());
                currentPlayer.setOnTurn(false);
            }

            currentIndex++;

            if (currentIndex == numPlayers) {
                currentIndex = 0;
            }
        }
        onePlayerLeft();
        createSidePot(activePlayer);

    }

    // Takes all the words submitted by users as input and adds into each computer players dictionary if it is not in theirs
    private void updatePlayerDictionary(List<String> newWords) {
        HashMap<String,List<String>> learnWords = new HashMap<>();
        for (TexasPlayer roundPlayer : roundPlayers) {
            if (roundPlayer instanceof ScrambleComputerPlayer csp) {
                for (String word : newWords) {
                    if (!csp.knowsWord(word) && !word.equals("")) {
                        csp.learnWord(word);
                        List<String> str = new ArrayList<>();
                        if (learnWords.containsKey(csp.getName())) {
                            str = learnWords.get(csp.getName());
                        }
                        str.add(word);
                        learnWords.put(csp.getName(), str);
                    }
                }
            }

        }
            // print who learn what words
            for(Map.Entry<String,List<String>> entry:learnWords.entrySet()){
                if(entry.getValue().size()==1){
                    System.out.println(entry.getKey()+" says: "+entry.getValue()+" is new word for me and I learned it now!");
                }else if(entry.getValue().size()>1){
                    System.out.println(entry.getKey()+" says: "+entry.getValue()+" are new words for me and I learned them now!");
                }
            }


    }

    @Override
    protected void dealCommunityElements(int numCardsToBeDealt) {
        for (int i = 0; i < numCardsToBeDealt; i++) {
            communityTiles.add((Tile) deck.dealNext());
        }
    }
    // combine hand and communityTiles to one String[]
    public String[] combineToString(List<Tile> communityTiles,Tile[] hand){
        String[] str = new String[7];
        int i=0;
        for(Tile tile : communityTiles){
            str[i]=tile.name();
            i++;
        }
        for(Tile handTile : hand){
            str[i]= handTile.name();
            i++;
        }
        return str;
    }
}
