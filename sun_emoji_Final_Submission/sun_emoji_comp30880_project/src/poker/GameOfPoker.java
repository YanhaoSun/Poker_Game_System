
package poker;

// This package provides classes necessary for implementing a game system for playing poker

// A RoundOfPoker is a single round/deal in a game
// A GameOfPoker is a sequence of one or more RoundOfPoker's


public class GameOfPoker
{
	private Player[] players;

	private DeckOfCards deck;

	private int numPlayers;

	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Constructor
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//

	public GameOfPoker(String[] names, int bank) {
		numPlayers = names.length;

		players = new Player[numPlayers];

		for (int i = 0; i < numPlayers; i++)
			if (i == 0)
				players[i] = new HumanPlayer(names[i].trim(), bank);
			else
				players[i] = new ComputerPlayer(names[i].trim(), bank);

		deck  = new DeckOfCards();
	}


	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Accessors
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//

	public int getNumPlayers() {
		return numPlayers;

	}


	public Player getPlayer(int num) {
		if (num >= 0 && num <= numPlayers)
			return players[num];
		else
			return null;
	}


	public int getNumSolventPlayers() {
		// how many players still have money left?

		int count = 0;

		for (int i = 0; i < getNumPlayers(); i++)
			if (getPlayer(i) != null && !getPlayer(i).isBankrupt())
				count++;

		return count;
	}


	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Play Poker
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//

	public void play()	{
		while (getNumSolventPlayers() > 1) {
			RoundOfPoker round = new RoundOfPoker(deck, players);

			round.play();

			try {
				System.out.print("\n\nPlay another round? Press 'q' to terminate this game ... ");

				byte[] input = new byte[100];

				System.in.read(input);

				for (int i = 0; i < input.length; i++)
					if ((char)input[i] == 'q' || (char)input[i] == 'Q')
						return;
			}
			catch (Exception e) {};
		}
	}


	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Launcher for application
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	public static boolean checkName(String humanName, String[] names){
		for(String name: names){
			if(name.equals(humanName)){
				return true;
			}
		}
		return false;
	}
	public static void startGame() {
		String[] names = {"Human", "Tom", "Dick", "Harry"};

		System.out.println("\nWelcome to the Automated Poker Machine ...\n\n");

		System.out.print("\nWhat is your name?  ");
		String userInput;
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
			names[0] = userInput;
		}
		catch (Exception e){};

		int startingBank = 10;

		System.out.println("\nLet's play POKER ...\n\n");

		GameOfPoker game = new GameOfPoker(names, startingBank);

		game.play();
	}


}
