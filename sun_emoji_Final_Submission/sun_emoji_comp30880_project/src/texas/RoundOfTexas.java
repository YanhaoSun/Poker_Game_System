package texas;

import poker.*;
import texas.hold_em.HoldEmComputerPlayer;
import texas.hold_em.HoldEmHumanPlayer;
//import texas_scramble.*;

import java.util.*;

public abstract class RoundOfTexas {
    public static int DELAY_BETWEEN_ACTIONS = 1000;  // number of milliseconds between game actions

    public static final int SMALL_BLIND_AMOUNT = 1;

    public static final int BIG_BLIND_AMOUNT = 2 * SMALL_BLIND_AMOUNT;
    protected ArrayList<TexasPlayer> roundPlayers;
    private int dealerIndex;

    protected Deck deck;
    protected int numPlayers;
    private int smallIndex;
    private int bigIndex;

    protected ArrayList<PotOfMoney> pots = new ArrayList<>();

    private PotOfMoney mainPot = new PotOfMoney();

    protected TexasPrintGame printGame;


    public RoundOfTexas(Deck deck, ArrayList<TexasPlayer> players, int dealerIndex) {
        this.deck = deck;
        this.roundPlayers = players;
        roundPlayers.get(dealerIndex).setDealer(true);
        this.dealerIndex = dealerIndex;
        numPlayers = roundPlayers.size();
        pots.add(mainPot);
        ArrayList<Integer> playersID = new ArrayList<>();
        for (TexasPlayer player : players) {
            playersID.add(player.getId());
        }
        pots.get(0).setPlayerIds(playersID);
    }

    //Abstract Functions:

    abstract public void showDown();


    //remove player who are unable to play the game
    public void removePlayer() {
        Iterator<TexasPlayer> iterator = roundPlayers.iterator();
        while (iterator.hasNext()) {
            TexasPlayer player = iterator.next();
            if (player.getBank() < BIG_BLIND_AMOUNT) {
                iterator.remove();
            }
        }

        for (TexasPlayer player : roundPlayers) {
            player.reset();
            player.resetDealer();
        }
    }
    public TexasPlayer getPlayerById(List<TexasPlayer> roundPlayers, int playerId) {
        for (TexasPlayer player : roundPlayers) {
            if (player.getId() == playerId) {
                return player;
            }
        }
        return null; // player with the given ID was not found
    }
    public void createSidePot(int activePlayer) {
        ArrayList<Integer> playerList = getActivePot().getPlayerIds();
        ArrayList<Integer> allInPlayer = new ArrayList<>();
        for (int id : playerList) {
            if (getPlayerById(roundPlayers, id).isAllIn() && !getPlayerById(roundPlayers, id).hasSidePot()) {
                allInPlayer.add(id);
            }
        }
        if (allInPlayer.size() == 0) {
            return;
        }


        //sort all in player list, start from player who has the smallest stake
        Collections.sort(allInPlayer, new Comparator<Integer>() {
            @Override
            public int compare(Integer playerId1, Integer playerId2) {
                Player player1 = getPlayerById(roundPlayers, playerId1);
                Player player2 = getPlayerById(roundPlayers, playerId2);
                return Integer.compare(player1.getStake(), player2.getStake());
            }
        });
        //go through the allin players
        for (int ID : allInPlayer) {
            TexasPlayer player = getPlayerById(roundPlayers, ID);
            PotOfMoney sidePot = new PotOfMoney();
            PotOfMoney lastPot = getActivePot();
            ArrayList<Integer> newPlayerIds = new ArrayList<>(lastPot.getPlayerIds());
            newPlayerIds.removeIf(id -> id == player.getId());


            //count total chips in pots
            int previousStake = 0;
            for (PotOfMoney pot : pots) {
                previousStake += pot.getTotal();
            }
            // do not create side pot if the player can win more than current total stakes in pots
            if (player.getTotalStake() * activePlayer > previousStake) {
                player.SetSidePot();
                continue;
            }
            //calculate side pot
            if (pots.size() == 1) {
                previousStake = lastPot.getTotal();
                sidePot.setStake(lastPot.getCurrentStake());
                sidePot.setPlayerIds(newPlayerIds);
                lastPot.setTotal(player.getTotalStake() * activePlayer);
                sidePot.setTotal(previousStake - player.getTotalStake() * activePlayer);
            } else {
                previousStake -= lastPot.getTotal();
                int lastTotal = lastPot.getTotal();
                sidePot.setStake(lastPot.getCurrentStake());
                sidePot.setPlayerIds(newPlayerIds);
                lastPot.setTotal(player.getTotalStake() * activePlayer - previousStake);
                sidePot.setTotal(lastTotal - lastPot.getTotal());
            }

            player.SetSidePot();
            pots.add(sidePot);
        }

    }


    public void play() {
        //if there are more than one unfolded players in the game, they have to showdown to determine the winner.
        roundCounter(1);
        //decided who win
        showDown();
        //remove player who all-in and loss or has less than big blind chips
        removePlayer();
    }

    // handle the small blind and big blind
    public void blindBet() {
        System.out.println("Small Blind: " + SMALL_BLIND_AMOUNT + "\nBig Blind: " + BIG_BLIND_AMOUNT);
        System.out.println("\n\nNew Deal:\n\n");

        if (dealerIndex == (numPlayers - 2)) {
            smallIndex = numPlayers - 1;
            bigIndex = 0;
        } else if (dealerIndex == numPlayers - 1) {
            smallIndex = 0;
            bigIndex = 1;
        } else {
            smallIndex = dealerIndex + 1;
            bigIndex = dealerIndex + 2;
        }
        roundPlayers.get(smallIndex).smallBlind(SMALL_BLIND_AMOUNT, pots.get(0));
        roundPlayers.get(bigIndex).bigBlind(SMALL_BLIND_AMOUNT, pots.get(0));
    }


    public boolean onePlayerLeft() {
        int counter = 0;
        for (TexasPlayer player : roundPlayers) {
            if (player.hasFolded()) {
                pots.get(pots.size() - 1).getPlayerIds().removeIf(id -> id == player.getId());
                counter++;
            }
        }
        return counter == numPlayers - 1;
    }

    // five round in the game
    public void roundCounter(int counter) {
        int roundCounter = counter;

        while (!onePlayerLeft() && roundCounter != 5) {

            switch (roundCounter) {
                case 1 -> {
                    preFlopRound();
                    roundCounter++;
                    dealCommunityElements(3);
                    System.out.println("\n\nThree Public Cards are released\n");
                }
                case 2 -> {
                    printGame.table(Rounds.FLOP);
                    flopRound();
                    roundCounter++;
                    dealCommunityElements(1);
                    System.out.println("\n\nTurn Card is released\n");
                }
                case 3 -> {
                    printGame.table(Rounds.TURN);
                    turnRound();
                    roundCounter++;
                    dealCommunityElements(1);
                    System.out.println("\n\nRiver Card is released\n");
                }
                default -> {
                    printGame.table(Rounds.RIVER);
                    riverRound();
                    roundCounter++;

                    printGame.table(Rounds.SHOWDOWN);
                }
            }
            resetStakes();
        }
    }

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

    // If player is human, prompts them to press key to continue to prevent last player from seeing next player's hand.

    protected void promptPlayerToContinue(TexasPlayer currentPlayer) {
        System.out.println("\n>> " + currentPlayer.getName() + "'s turn!\n");
        if (currentPlayer instanceof HoldEmHumanPlayer) {
            System.out.print("> Switch to " + currentPlayer.getName() + " then press any key to continue.  ");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }
    }


    public void preFlopRound() {
        blindBet();
        //after small blind and big blind, deal two cards to each player
        for (TexasPlayer player : roundPlayers) {

            player.dealTo(deck);
            System.out.println(player);
        }
        //print the table before the game starts
        System.out.println();
        printGame.table(Rounds.PRE_FLOP);
        roundMove(Rounds.PRE_FLOP);
    }

    public void flopRound() {
        roundMove(Rounds.FLOP);
    }

    public void turnRound() {
        roundMove(Rounds.TURN);
    }

    public void riverRound() {
        roundMove(Rounds.RIVER);
    }


    // get who move first
    public int firstMovePlayerIndex(Rounds currentRound) {
        int index;
        if (currentRound == Rounds.PRE_FLOP) {
            index = bigIndex + 1;
        } else {
            index = dealerIndex + 1;
        }
        if (index == (numPlayers)) {
            index = 0;
        }
        while (roundPlayers.get(index).hasFolded()) {
            index++;
            if (index == numPlayers) {
                index = 0;
            }
        }
        return index;
    }

    //everyone called or folded
    public Boolean ActionClosed() {
        int foldCounter = 0;
        int callCounter = 0;
        for (TexasPlayer player : roundPlayers) {
            if (player.hasFolded()) {
                foldCounter++;
            } else if (player.getStake() == getActivePot().getCurrentStake() || player.isAllIn()) {
                callCounter++;
            }
        }
        return foldCounter + callCounter == numPlayers;
    }

    protected abstract void dealCommunityElements(int numCardsToBeDealt);

    // Get the pot everyone is betting with

    public PotOfMoney getActivePot() {
        return pots.get(pots.size() - 1);
    }

    // Reset pot and player stakes to prepare for new round of betting

    public void resetStakes() {
        getActivePot().setStake(0);

        for (TexasPlayer player : roundPlayers) {
            player.resetStake();
        }
    }

    // Utility method to delay actions

    public void delay(int numMilliseconds) {
        try {
            Thread.sleep(numMilliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}