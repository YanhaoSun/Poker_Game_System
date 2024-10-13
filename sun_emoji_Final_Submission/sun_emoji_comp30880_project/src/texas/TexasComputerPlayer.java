package texas;

import poker.PotOfMoney;
import texas.scramble.hand.HandElement;

import java.util.List;

abstract public class TexasComputerPlayer extends TexasPlayer {
    private List<? extends HandElement> communityElements;

    public TexasComputerPlayer(String name, int money, int id) {
        super(name, money, id);
    }

    //--------------------------------------------------------------------//
    //--------------------------------------------------------------------//
    // For Community Cards
    //--------------------------------------------------------------------//
    //--------------------------------------------------------------------//
    public List<? extends HandElement> getCommunityElements(){
        return communityElements;
    }

    public void setCommunityElements(List<? extends HandElement> communityElements){
        this.communityElements = communityElements;
    }

    public Rounds getCurrentRound() {
        int size = communityElements.size();
        switch (size) {
            case 3 -> {
                return Rounds.FLOP;
            }
            case 4 -> {
                return Rounds.TURN;
            }
            case 5 -> {
                return Rounds.RIVER;
            }
            default -> {
                return Rounds.PRE_FLOP;
            }
        }
    }

    public abstract int getRiskTolerance(PotOfMoney pot);

    protected abstract int predicateRiskTolerance();

    //--------------------------------------------------------------------//
    //--------------------------------------------------------------------//
    // Raises stake by minimum needed
    //--------------------------------------------------------------------//
    //--------------------------------------------------------------------//

    @Override
    public void raiseBet(PotOfMoney pot) {
        int raiseAmount;

        if (pot.getCurrentStake() == 0) {
            raiseAmount = RoundOfTexas.BIG_BLIND_AMOUNT;
        } else {
            raiseAmount = pot.getCurrentStake() * 2;
        }

        int needed = raiseAmount - stake;
        stake += needed;
        bank -= needed;

        pot.setStake(stake);
        pot.addToPot(needed);

        System.out.println("\n> " + getName() + " says: I raise to " + raiseAmount + " chips!\n");
    }
}
