package texas.scramble.controller;

import texas.TexasController;
import texas.scramble.deck.*;
import texas.scramble.player.ScrambleComputerPlayer;
import texas.scramble.player.ScrambleHumanPlayer;

import java.util.*;
import java.util.stream.Collectors;

public class ScrambleController extends TexasController {
    protected DeckOfTiles deck;
    //set up the game with ScramblePlayer and Tiles for deck
    @Override
    public void setUp(String[] names, int bank, int numHumanPlayers) {
        texasPlayers = new ArrayList<>();
        numPlayers = names.length;

        for (int i = 0; i < numPlayers; i++) {
            if (i < numHumanPlayers) {
                texasPlayers.add(new ScrambleHumanPlayer(names[i].trim(), bank, i));
            } else {
                texasPlayers.add(getPresetComputerPlayer(names[i].trim(), bank, i));
            }
        }

        deck = new DeckOfTiles();
    }

    // Get computer players
    private ScrambleComputerPlayer getPresetComputerPlayer(String name, int bank, int id) {
        ScrambleComputerPlayer player;
        switch (name) {
            case "Tom" -> player = new ScrambleComputerPlayer(name, bank, id, "resources/easy.txt", 50);
            case "Dick" -> player = new ScrambleComputerPlayer(name, bank, id, "resources/medium.txt", 120);
            case "Harry" -> player = new ScrambleComputerPlayer(name, bank, id, "resources/harder.txt", 85);
            case "Jim" -> player = new ScrambleComputerPlayer(name, bank, id, "resources/hard.txt", 63);
            case "Dave" -> player = new ScrambleComputerPlayer(name, bank, id, "resources/medium.txt", 104);
            default -> player = new ScrambleComputerPlayer(name, bank, id, "resources/medium.txt");
        }
        return player;
    }

    //distribute new ids to remain players
    public void updatePlayerIDs() {
        for (int i = 0; i < texasPlayers.size(); i++) {
            texasPlayers.get(i).setId(i);
        }
    }

    @Override
    public void play()	{
        int dealerIndex=texasPlayers.size();
        while (texasPlayers.size() > 1) {
            deck.reset();//before each game starts, shuffle the deck
            if(dealerIndex>=texasPlayers.size()){dealerIndex=0;}


            //start a game, there are four rounds within a game: Pre-flop, Turn, River and the one after River.
            RoundOfScramble round = new RoundOfScramble(deck, texasPlayers, dealerIndex);
            round.play();
            if(texasPlayers.size()==1){
                break;
            }
            try {
                System.out.print("\n\nPlay another round? Press 'q' to terminate this game ... ");

                byte[] input = new byte[100];

                System.in.read(input);

                for (int i = 0; i < input.length; i++)
                    if ((char)input[i] == 'q' || (char)input[i] == 'Q')
                        return;
                //next player become a dealer
                dealerIndex ++;

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            updatePlayerIDs();
        }

        System.out.println("Congratulations!");
        System.out.println(texasPlayers.get(0).getName()+" is the Winner of the Game!");
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

        System.out.println("\nWelcome to the Automated Texas Scramble Machine ...\n\n");

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
        //get names of human players
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
                } else if (numHumanPlayers==1) {
                    System.out.print("\nHow many computer players do you wish to play with? (Between 1 and " + (10 - numHumanPlayers) + " inclusive)  ");
                } else {
                    System.out.print("\nHow many computer players do you wish to play with? (Between 0 and " + (10 - numHumanPlayers) + " inclusive)  ");

                }
                try {
                    numComputerPlayers = scanner.nextInt();

                    if (numComputerPlayers + numHumanPlayers <= 10 && numComputerPlayers + numHumanPlayers >= 2) {
                        playerNames = getRandomComputerPlayers(numHumanPlayers, numComputerPlayers, names);
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

        System.out.println("\nLet's play Texas Scramble ...\n\n");
        ScrambleController game = new ScrambleController();
        game.setUp(playerNames, startingBank, numHumanPlayers);
        game.play();
    }

    // Picks random computer player names and adds to name list

    private static String[] getRandomComputerPlayers(int numHumanPlayers, int numComputerPlayers, String[] names) {
        String[] computerNames = {"Tom", "Dick", "Harry", "Jim", "Dave", "Paul", "Bob", "John", "Bill"};

        Set<Integer> set = new Random().ints(0, 9)
                .distinct()
                .limit(numComputerPlayers)
                .boxed()
                .collect(Collectors.toSet());

        int currIndex = numHumanPlayers;
        for (int index : set) {
            names[currIndex++] = computerNames[index];
        }

        return Arrays.copyOfRange(names, 0, numComputerPlayers + numHumanPlayers);
    }
}
