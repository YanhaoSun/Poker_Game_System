package texas;

import poker.PotOfMoney;

import java.util.List;

abstract public class TexasPrintGame {
    protected final String[] cardEdge = {"╭────╮", "╰────╯"};

    protected final int cardHeight = 3; //counting from 0

    protected List<TexasPlayer> texasPlayers;

    protected List<PotOfMoney> pots;

    public TexasPrintGame(List<TexasPlayer> texasPlayers, List<PotOfMoney> pots) {
        this.texasPlayers = texasPlayers;
        this.pots = pots;
    }

    abstract public void cardPrinter(boolean showDown);

    public void table(Rounds currentRound) {
        switch (currentRound) {
            case PRE_FLOP, FLOP, RIVER, TURN -> cardPrinter(false);
            //after river round, if there are more than two players left, they need to showDown
            default -> cardPrinter(true);
        }
    }
}
