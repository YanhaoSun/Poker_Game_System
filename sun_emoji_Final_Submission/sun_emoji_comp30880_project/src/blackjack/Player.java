package blackjack;
import poker.Card;

import java.util.ArrayList;

abstract class Player {
    protected static final int FIRST_HAND = 0;

    protected int bank = 0;
    private String name = "player";
    protected ArrayList<BlackjackHand> hands = new ArrayList<>();

    /*--------------------Constructor--------------------------*/

    public Player(String name, int money) {
        this.name = name;
        bank = money;
    }

    /*--------------------Accessors--------------------------*/

    public ArrayList<BlackjackHand> getHands() {
        return hands;
    }


    public int getBank() {
        return bank;
    }

    public String getName() {
        return name;
    }

    public boolean isBankrupt() {
        // no more money left

        return bank == 0;
    }

    /*--------------------Modifiers--------------------------*/

    public void dealTo(DeckOfCards deck) {
        hands = new ArrayList<>();
        hands.add(deck.dealBlackJackHand());
    }

    public void placeBet(int bet){
        if(bet<=bank){
            hands.get(FIRST_HAND).setStake(bet);
            bank -= bet;

            System.out.println("\n> " + getName() + " says: I bet with " + bet + " chip!\n");
        }
    }

    /*------------------Handle bet payouts----------------------------*/

    public void winBet(int bet){
        bank+=bet;
    }
    public void lossBet(int bet){
        bank-=bet;
    }


    /*----------Method for player to pick action to do----------------*/

    abstract Action chooseAction(BlackjackHand hand);

    /*----------------Actions a player can take-----------------------*/

    boolean split(BlackjackHand hand) {
        BlackjackHand splitHand = new BlackjackHand(hand);
        splitHand.addCard();
        hand.addCard();
        getHands().add(splitHand);
        return false;
    }
    boolean stand() {
        System.out.println("\n> " + getName() + " says: I stand!\n");
        return true;
    }

    /*----------------Schedule game actions----------------------------*/

    public void takeTurn(Card dealerFaceUpCard) {
        int numHand = 0;
        while (numHand != hands.size()) {
            BlackjackHand hand = hands.get(numHand);
            boolean actionCompleted = false;
            while (!(hand.isBusted() || actionCompleted) ) {
                System.out.println(">> " + name + ": hand " + (numHand+1) + " turn!");
                if (!(this instanceof DealerPlayer)) {
                    System.out.println("\n      Dealer's card: " + dealerFaceUpCard + "\n      Bank: " + bank + hand);
                }
                switch (chooseAction(hand)) {
                    case HIT -> actionCompleted = hand.hit(name);
                    case SPLIT -> {
                        bank -= hand.getStake();
                        actionCompleted = split(hand);
                    }
                    case STAND -> actionCompleted = stand();
                    case DOUBLE -> {
                        bank -= hand.getStake();
                        actionCompleted = hand.doubleDown(name);
                    }
                    case SURRENDER -> actionCompleted = hand.surrender(name);
                    default -> {
                    }
                }
                delay(500);
            }
            numHand++;
            System.out.println(hand);
            if(hand.isBusted()){
                System.out.println("\n> " + name + " says: I busted!\n");
            }
        }
    }

    /*-----------Utility method to delay actions--------------------*/

    private void delay(int numMilliseconds) {
        try {
            Thread.sleep(numMilliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
