package texas.hold_em;

import poker.DeckOfCards;
import texas.TexasController;

import java.util.Arrays;
import java.util.Scanner;

public class HoldEmController extends TexasController {

    @Override
    public void setUp(String[] names, int bank, int numHumanPlayers) {
        super.setUp(names, bank, numHumanPlayers);
        updatePlayerIDs();
        deck = new DeckOfCards();
    }
	//distribute new ids to remain players
    public void updatePlayerIDs() {
        for (int i = 0; i < texasPlayers.size(); i++) {
            texasPlayers.get(i).setId(i);
        }
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

        System.out.println("\nWelcome to the Automated Texas Hold'Em Machine ...\n\n");

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
                } else if(numHumanPlayers==1){
                    System.out.print("\nHow many computer players do you wish to play with? (Between 1 and " + (10 - numHumanPlayers) + " inclusive)  ");
                }else {
                    System.out.print("\nHow many computer players do you wish to play with? (Between 0 and " + (10 - numHumanPlayers) + " inclusive)  ");

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

		// every player start with 100 chips
        int startingBank = 100;

        System.out.println("\nLet's play Texas Hold'Em ...\n\n");
        HoldEmController game = new HoldEmController();
        game.setUp(playerNames, startingBank, numHumanPlayers);
        game.play();
    }
}