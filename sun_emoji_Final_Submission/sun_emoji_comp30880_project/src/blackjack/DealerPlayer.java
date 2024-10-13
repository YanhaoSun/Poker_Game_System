package blackjack;

import poker.Card;

public class DealerPlayer extends Player {
    private static final int DEALER_STAND_LIMIT = 17;//Dealer must stand once dealer's card value is greater than or equal to 17

    /*--------------------Constructor--------------------------*/
    public DealerPlayer(String name, int bank)
    {
        super(name, bank);
    }

    /*--------------------Show the faced up card that on dealer's hand--------------------------*/
    public Card showOneCard()
    {
        return this.getHands().get(0).getCard(0);
    }
    //dealer always hit until the handValue is greater or equal to 17

    /*--------------------Dealer will decide if dealer want to HIT or STAND--------------------------*/
    // dealer never spilt, doubleDown
    @Override
    Action chooseAction(BlackjackHand hand){
        try{
            if(this.getHands().get(0).getValue()<DEALER_STAND_LIMIT)
            {
                return Action.HIT;
            }
            else {
                return Action.STAND;
            }
        }
        catch (Exception e){}
        return Action.INVALID;
    }

    /*--------------------Getter method: get the hand of dealer--------------------------*/
    public BlackjackHand getHand() {
        return hands.get(0);
    }
}
