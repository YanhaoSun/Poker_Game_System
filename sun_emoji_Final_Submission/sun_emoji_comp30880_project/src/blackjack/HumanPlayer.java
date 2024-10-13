package blackjack;
import java.util.Scanner;

public class HumanPlayer extends Player {

    /*--------------------Constructor--------------------------*/
    public HumanPlayer(String name, int bank) {
        super(name, bank);
    }

    /*------------Facilitate human placing a bet---------------*/

    @Override
    public void placeBet(int bet) {
        Scanner input = new Scanner(System.in);
        while (bet <= 0 || bet > bank) {
            System.out.print("Enter your bet amount (you have " + bank + " chips): ");
            try{
                if (input.hasNextInt()) {
                    bet = input.nextInt();
                    if (bet <= 0) {
                        System.out.println("Invalid bet amount! Please enter a positive value.");
                    } else if (bet > bank) {
                        System.out.println("You don't have enough chips! Please enter a smaller value.");
                    }
                }else{
                    input.next();
                    System.out.println("Invalid input! Please enter a valid number.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        hands.get(FIRST_HAND).setStake(bet);
        bank -= bet;

        System.out.println("\n> " + getName() + " says: I bet with " + bet + " chip!\n");
    }

    /*-------------Utility function to notify user of wrong action-----------------*/

    public void notifyInvalidAction(String action, String reason) {
        System.out.print("\n>> Cannot perform <" + action + "> due to <" + reason + ">\n\n");
    }

    /*----------------Get human player's action-----------------------*/

    @Override
    Action chooseAction(BlackjackHand hand) {
        System.out.print("\n>> Pick an option: 1. Hit  2. Stand  3. Double Down  4. Split  5. Surrender  ");
            byte[] input = new byte[100];

            try {
                System.in.read(input);

                for (byte b : input) {
                    switch ((char) b) {
                        case '\0', '\n': break;
                        case '1':
                            return Action.HIT;
                        case '2':
                            return Action.STAND;
                        case '3':
                            if (canDoubleDown(hand)) {
                                return Action.DOUBLE;
                            }
                            break;
                        case '4':
                            if (canSplit(hand)) {
                                return Action.SPLIT;
                            }
                            break;
                        case '5':
                            if (canSurrender(hand)) {
                                return Action.SURRENDER;
                            }
                        default:
                            System.out.println("Invalid option.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return Action.INVALID;
    }

    /*--------------Methods to check if actions are allowed--------------------*/

    private boolean canSurrender(BlackjackHand hand) {
        if (hand.getNumCardsInHand() != 2) {
            notifyInvalidAction("surrender", "can only double down on opening hand");
            return false;
        }
        return true;
    }

    private boolean canDoubleDown(BlackjackHand hand) {
        if (hand.getNumCardsInHand() != 2) {
            notifyInvalidAction("double down", "can only double down on opening hand");
            return false;
        }
        if (getBank() < hand.getStake()) {
            notifyInvalidAction("double down", "insufficient chips");
            return false;
        }
        return true;
    }

    private boolean canSplit(BlackjackHand hand) {
        if (hand.getNumCardsInHand() != 2) {
            notifyInvalidAction("split", "can only split on opening hand");
            return false;
        }
        if (hand.getCard(0).getValue() != hand.getCard(1).getValue()) {
            notifyInvalidAction("split", "cards not the same");
            return false;
        }
        if (getBank() < hand.getStake()) {
            notifyInvalidAction("split", "insufficient chips");
            return false;
        }
        return true;
    }
}
