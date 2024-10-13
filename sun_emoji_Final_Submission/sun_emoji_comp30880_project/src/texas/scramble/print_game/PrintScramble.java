package texas.scramble.print_game;

import poker.PotOfMoney;
import texas.TexasPlayer;
import texas.TexasPrintGame;
import texas.scramble.deck.Tile;
import texas.scramble.player.ScrambleHumanPlayer;

import java.util.List;

public class PrintScramble extends TexasPrintGame {
    private List<Tile> communityCards;

    public PrintScramble(List<TexasPlayer> texasPlayers, List<PotOfMoney> pots, List<Tile> communityCards) {
        super(texasPlayers, pots);
        this.communityCards = communityCards;
    }

    /************************ this method will print the game table and cards of each player and public cards *****************************/
    public void cardPrinter(boolean showDown) {
//        showDown = true;
        StringBuilder sb = null;
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        int halfPlayers = texasPlayers.size() / 2;
        System.out.println("\n\n********** Game Table **********");
        for (int i = 0; i <= cardHeight; i++) {
            if (i == 0) {
                /************************ print border of players *****************************/
                int count = 0;
                for (int j = 0; j < texasPlayers.size(); j++) {
                    String temp = String.format("__________________");
                    if (j < halfPlayers && sb != sb1) {
                        sb = sb1;

                    } else if (j >= halfPlayers && sb != sb2) {
                        sb1.append("\n");
                        sb = sb2;
                    }
                    if (j < halfPlayers) {
                        sb.append(temp);
                    } else {
                        sb.append(temp);
                        count++;
                        if(texasPlayers.size()%2==0){
                            if (count == halfPlayers) {
                                sb.append("\n");
                            }
                        }else {
                            if(count == halfPlayers+1){
                                sb.append("\n");
                            }
                        }
                    }
                }

                /************************ print name of players *****************************/
                for (int j = 0; j < texasPlayers.size(); j++) {
                    if (j < halfPlayers && sb != sb1) {
                        sb = sb1;

                    } else if (j >= halfPlayers && sb != sb2) {
                        sb1.append("\n");
                        sb = sb2;
                    }

                    if (j == 0 || j == halfPlayers) {
                        sb.append("│ ");
                    }

                    String temp = String.format("%14s", "Name = " + texasPlayers.get(j).getName());
                    sb.append(temp).append(" │ ");
                }
                sb.append("\n");
            }
            /************************ print cards of players *****************************/
            for (int j = 0; j < texasPlayers.size(); j++) {
                int[] index1, index2;
                if (j < halfPlayers && sb != sb1) {
                    sb = sb1;
                } else if (j >= halfPlayers && sb != sb2) {
                    sb1.append("\n");
                    sb = sb2;
                }

                if (j == 0 || j == halfPlayers) {
                    sb.append("│ ");
                }

                if (texasPlayers.get(j).hasFolded()) {
                    if (i == cardHeight - 1) {
                        String tmp = String.format("%14s", " Folded ");
                        sb.append(tmp).append(" │ ");
                    } else {
                        String tmp = String.format("%14s", " ");
                        sb.append(tmp).append(" │ ");
                    }
                } else {

                    if (i == 0) {
                        sb.append(cardEdge[0]).append("  ").append(cardEdge[0]).append(" │ ");
                    } else if (i == cardHeight) {
                        sb.append(cardEdge[1]).append("  ").append(cardEdge[1]).append(" │ ");
                    } else {
                        if (showDown) {
                            if (i == 1) {
                                String letter1 = String.format("%2s", (texasPlayers.get(j).getHand().getHand()[0]).toString());
                                String letter2 = String.format("%2s", (texasPlayers.get(j).getHand().getHand()[1]).toString());
                                sb.append("│").append(letter1).append("  │").append("  ").append("│").append(letter2).append("  │").append(" │ ");
                            } else if (i == 2) {
                                int value1 = texasPlayers.get(j).getHand().getHand()[0].getValue();
                                int value2 = texasPlayers.get(j).getHand().getHand()[1].getValue();
                                if(value1==10 && value2!=10){
                                    sb.append("│  ").append(value1).append("│").append("  ").append("│   ").append(value2).append("│").append(" │ ");
                                }
                                if(value1!=10 && value2==10){
                                    sb.append("│   ").append(value1).append("│").append("  ").append("│  ").append(value2).append("│").append(" │ ");
                                }
                                if(value1!=10 && value2!=10){
                                    sb.append("│   ").append(value1).append("│").append("  ").append("│   ").append(value2).append("│").append(" │ ");
                                }
                                if(value1==10 && value2==10){
                                    sb.append("│  ").append(value1).append("│").append("  ").append("│  ").append(value2).append("│").append(" │ ");
                                }
                            }
                        } else {
                            if (texasPlayers.get(j).isOnTurn() && texasPlayers.get(j) instanceof ScrambleHumanPlayer) {
                                if (i == 1) {
                                    String letter1 = String.format("%2s", (texasPlayers.get(j).getHand().getHand()[0]).toString());
                                    String letter2 = String.format("%2s", (texasPlayers.get(j).getHand().getHand()[1]).toString());
                                    sb.append("│").append(letter1).append("  │").append("  ").append("│").append(letter2).append("  │").append(" │ ");
                                } else if (i == 2) {
                                    int value1 = texasPlayers.get(j).getHand().getHand()[0].getValue();
                                    int value2 = texasPlayers.get(j).getHand().getHand()[1].getValue();
                                    if(value1==10 && value2!=10){
                                        sb.append("│  ").append(value1).append("│").append("  ").append("│   ").append(value2).append("│").append(" │ ");
                                    }
                                    if(value1!=10 && value2==10){
                                        sb.append("│   ").append(value1).append("│").append("  ").append("│  ").append(value2).append("│").append(" │ ");
                                    }
                                    if(value1!=10 && value2!=10){
                                        sb.append("│   ").append(value1).append("│").append("  ").append("│   ").append(value2).append("│").append(" │ ");
                                    }
                                    if(value1==10 && value2==10){
                                        sb.append("│  ").append(value1).append("│").append("  ").append("│  ").append(value2).append("│").append(" │ ");
                                    }
                                }
                            } else {
                                sb.append("│    │").append("  ").append("│    │").append(" │ ");
                            }
                        }
                    }
                }
            }
            sb.append("\n");

            if (i == cardHeight) {
                /************************ print stakes of players *****************************/
                for (int j = 0; j < texasPlayers.size(); j++) {
                    if (j < halfPlayers && sb != sb1) {
                        sb = sb1;

                    } else if (j >= halfPlayers && sb != sb2) {
                        sb1.append("\n");
                        sb = sb2;
                    }

                    if (j == 0 || j == halfPlayers) {
                        sb.append("│ ");
                    }
                    sb.append(String.format("%14s", "Stake=" + texasPlayers.get(j).getStake())).append(" │ ");
                }
                sb.append("\n");

                /************************ print bank of players *****************************/
                for (int j = 0; j < texasPlayers.size(); j++) {
                    if (j < halfPlayers && sb != sb1) {
                        sb = sb1;

                    } else if (j >= halfPlayers && sb != sb2) {
                        sb1.append("\n");
                        sb = sb2;
                    }

                    if (j == 0 || j == halfPlayers) {
                        sb.append("│ ");
                    }
                    sb.append(String.format("%14s", "Bank=" + texasPlayers.get(j).getBank())).append(" │ ");
                }
                sb.append("\n");

                /************************ print Dealer *****************************/
                for (int j = 0; j < texasPlayers.size(); j++) {
                    if (j < halfPlayers && sb != sb1) {
                        sb = sb1;

                    } else if (j >= halfPlayers && sb != sb2) {
                        sb1.append("\n");
                        sb = sb2;
                    }

                    if (j == 0 || j == halfPlayers) {
                        sb.append("│ ");
                    }
                    if (texasPlayers.get(j).isDealer()) {
                        sb.append(String.format("%14s", "Dealer")).append(" │ ");
                    } else {
                        sb.append(String.format("%14s", " ")).append(" │ ");
                    }
                }
                sb.append("\n");
                /************************ print border *****************************/
                int count = 0;
                for (int j = 0; j < texasPlayers.size(); j++) {
                    String temp = String.format("------------------");

                    if (j < halfPlayers && sb != sb1) {
                        sb = sb1;

                    } else if (j >= halfPlayers && sb != sb2) {
                        sb1.append("\n");
                        sb = sb2;
                    }
                    if (j < halfPlayers) {
                        sb.append(temp);
                    } else {
                        sb.append(temp);
                        count++;
                        if(texasPlayers.size()%2==0){
                            if (count == halfPlayers) {
                                sb.append("\n");
                            }
                        }else {
                            if(count == halfPlayers+1){
                                sb.append("\n");
                            }
                        }
                    }
                }
            }
        }
        System.out.println(sb1);
        if (communityCards.isEmpty()) {
            System.out.println("Public cards not shown yet");
        } else {
            System.out.println("Public cards: ");
        }
        printPublicCard(communityCards);
        System.out.print("\n" + sb2);


        for (int i = 0; i < pots.size(); i++) {
            if (i == 0) {
                System.out.println("| Main Pot:\n|    CurrentStake=" + pots.get(i).getCurrentStake() + "     Total=" + pots.get(i).getTotal());
            } else {
                System.out.println("| Side Pot:\n|    CurrentStake=" + pots.get(i).getCurrentStake() + "     Total=" + pots.get(i).getTotal());
            }
        }
    }
    /************************ this method will print all public cards on game table *****************************/
    public void printPublicCard(List<Tile> communityCards) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j <= cardHeight; j++) {
            for (int i = 0; i < communityCards.size(); i++) {
                String letter1 = String.format("%2s", communityCards.get(i).toString());
                int value1 = communityCards.get(i).getValue();
                switch (j) {
                    case 0:
                        sb.append(cardEdge[0]).append("  ");
                        break;
                    case 1:
                        String tmp = String.format("%2s", letter1);
                        sb.append("│").append(tmp).append("  │").append("  ");
                        break;
                    case cardHeight:
                        sb.append(cardEdge[cardEdge.length - 1]).append("  ");
                        break;
                    default:
                        if(value1==10){
                            sb.append("│  ").append(value1).append("│").append("  ");
                        }else {
                            sb.append("│   ").append(value1).append("│").append("  ");
                        }
                        break;
                }
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }
}
