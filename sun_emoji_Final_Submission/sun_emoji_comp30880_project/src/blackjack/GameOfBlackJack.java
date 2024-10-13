package blackjack;

import java.util.Arrays;
import java.util.Scanner;

public class GameOfBlackJack {


    private Player[] players;//list that contains all players

    private DeckOfCards deck;//an instance of variable type of DeckOfCards

    private int numPlayers;//the number of players

    /*--------------------Constructor--------------------------*/
    public GameOfBlackJack(String[] names, int bank, int numHumanPlayers)
    {
        numPlayers = names.length+1;//the reason that adds extra one is for dealer

        players = new Player[numPlayers];
        players[numPlayers-1] = new DealerPlayer("Dealer", -1);//create an instance of Dealer

        //initialise HumanPlayer and all ComputerPlayer
        for (int i = 0; i < numPlayers-1; i++)
            if (i < numHumanPlayers)
                players[i] = new HumanPlayer(names[i].trim(), bank);
            else
                players[i] = new ComputerPlayer(names[i].trim(), bank);


        deck  = new DeckOfCards();//add 52 cards into deck of cards
    }

    /*--------------------Game starts to play, game ends until all players have no money in bank---------------------*/
    public void play() {
        while (getNumSolventPlayers() > 1) {
            deck.reset();//before each round starts, shuffle the deck
            RoundOfBlackJack round = new RoundOfBlackJack(deck, players);//create a new round

            round.play();//play with this round

            try {
                System.out.print("\n\nPlay another round? Press 'c' to continue and q' to terminate this game ... ");

                byte[] input = new byte[100];

                System.in.read(input);

                for (int i = 0; i < input.length; i++)
                    if ((char)input[i] == 'q' || (char)input[i] == 'Q')
                        return;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("GAME OVER!");
    }

    /*--------------------return the number of players that still have money--------------------------*/
    public int getNumSolventPlayers() {
        int count = 0;

        for (int i = 0; i < getNumPlayers(); i++)
            if (getPlayer(i) != null && !getPlayer(i).isBankrupt())
                count++;

        return count;
    }

    /*--------------------Getter Methods--------------------------*/
    public int getNumPlayers() {
        return numPlayers;
    }
    public Player getPlayer(int num) {
        if (num >= 0 && num <= numPlayers)
            return players[num];
        else
            return null;
    }
    public static boolean checkName(String humanName, String[] names){
        for(String name: names){
            if(name.equals(humanName)){
                return true;
            }
        }
        return false;
    }
    public static void startGame() {
        String[] names = {"Human", "Tom", "Dick", "Harry", "Jim", "Dave", "Paul", "Bob", "John", "Bill"};

        System.out.println("\nWelcome to the Automated Blackjack Machine ...\n\n");

        Scanner scanner = new Scanner(System.in);
        int numHumanPlayers = 0;
        while (true) {
            System.out.print("\nHow many human players are there? (max 10)  ");
            try {
                numHumanPlayers = scanner.nextInt();
                if (numHumanPlayers >= 0 && numHumanPlayers <= 10) {
                    break;
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (Exception e) {
                System.out.println("\nInvalid input.");
            }
            scanner.nextLine();
        }
        String userInput;
        for (int i = 0; i < numHumanPlayers; i++) {
            System.out.print("\nPlayer " + (i + 1) + ", What is your name?  ");

            byte[] input = new byte[100];

            try {
                int numBytesRead = System.in.read(input);
                userInput = new String(input, 0, numBytesRead).trim();
                while (checkName(userInput, names)){
                    System.out.println("\nThis name is already occupied, please try again: ");
                    byte[] input1 = new byte[100];
                    int numBytesRead1 = System.in.read(input1);
                    userInput = new String(input1, 0, numBytesRead1).trim();
                }
                if (!userInput.isEmpty()) {
                    names[i] = userInput;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String[] playerNames = names;

        if (numHumanPlayers != 10) {
            int numComputerPlayers = -10;
            // ask how many players in the game
            while (!(numComputerPlayers + numHumanPlayers <= 10 && numComputerPlayers + numHumanPlayers >= 2)) {
                if (numHumanPlayers == 0) {
                    System.out.print("\nHow many computer players do you wish to play with? (Between 2 and 10 inclusive)  ");
                } else {
                    System.out.print("\nHow many computer players do you wish to play with? (Between 1 and " + (10 - numHumanPlayers) + " inclusive)  ");
                }
                try {
                    numComputerPlayers = scanner.nextInt();

                    if (numComputerPlayers + numHumanPlayers <= 10 && numComputerPlayers + numHumanPlayers >= 2) {
                        playerNames = Arrays.copyOfRange(names, 0, numComputerPlayers + numHumanPlayers);
                    } else {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e) {
                    System.out.println("\nInvalid input.");
                }
                scanner.nextLine(); // Clear the scanner buffer
            }
        }

        int startingBank = 10;

        System.out.println("\nLet's play BLACKJACK ...\n\n");
        GameOfBlackJack game = new GameOfBlackJack(playerNames, startingBank, numHumanPlayers);

        game.play();
    }
}
