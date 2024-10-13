package texas.hold_em;

import poker.PotOfMoney;
import texas.Action;
import texas.RoundOfTexas;
import texas.TexasPlayer;

import java.util.Scanner;

import static texas.Action.*;

public class HoldEmHumanPlayer extends TexasPlayer {
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Constructor
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//

	public HoldEmHumanPlayer(String name, int money, int id) {
		super(name, money, id);
	}

	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// User Interface
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//

	// Ask the user a question and return true if the user says yes, 
	// otherwise return false

	public boolean askQuestion(String question) 	{
		System.out.print("\n>> " + question + " (y/n)?  ");

		byte[] input = new byte[100];

		try {
			System.in.read(input);

			for (byte b : input) {
				if ((char) b == 'y' || (char) b == 'Y') {
					return true;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public void notifyInvalidAction(String action, String reason) {
		System.out.print("\n>> Cannot perform <" + action + "> due to <" + reason + ">\n\n");
	}

	public Action chooseAction(PotOfMoney pot) {
		byte[] input = new byte[100];
		Action chosenAction = null;
		while (chosenAction == null) {
			try {
				System.out.print("\n>> Pick an option: 1. Call/Check  2. Raise  3. All In  4. Fold  ");

				System.in.read(input);

				for (byte b : input) {
					switch ((char) b) {
						case '\0', '\n' -> {}
						case '1' -> {
							if (shouldSee(pot)) chosenAction = SEE;
						}
						case '2' -> {
							if (shouldRaise(pot)) chosenAction = RAISE;
						}
						case '3' -> {
							if (shouldAllIn(pot)) chosenAction = ALL_IN;
						}
						case '4' -> {
							chosenAction = FOLD;
						}
						default -> {
							System.out.println("Invalid option.");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chosenAction;
	}

	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Get the user to input amount they wish to raise by and raise stake
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//

	@Override
	public void raiseBet(PotOfMoney pot) {
		Scanner scanner = new Scanner(System.in);
		int raiseAmount = -1;

		while (raiseAmount < pot.getCurrentStake() || raiseAmount > bank) {
			System.out.print("\n>> How many chips do you want to raise to? ");
			try {
				raiseAmount = scanner.nextInt();
				if (pot.getCurrentStake() == 0) {
					if (raiseAmount < RoundOfTexas.BIG_BLIND_AMOUNT) {
						System.out.println("Raise amount must be at least the big blind amount.");
					}
				} else {
					if (raiseAmount < pot.getCurrentStake() || raiseAmount > bank) {
						System.out.println("Invalid input.");
					}
				}
			} catch (Exception e) {
				System.out.println("Invalid input.");
				scanner.nextLine(); // Clear the scanner buffer
			}
		}

		System.out.println("You want to raise to " + raiseAmount + " chips.");
		int needed = raiseAmount - stake;
		stake += needed;
		bank -= needed;
		pot.setStake(stake);
		pot.addToPot(needed);
		System.out.println("\n> " + getName() + " says: I raise to " + raiseAmount + " chips!\n");

		if(bank==0){
			allIn();
		}
	}

	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Key decisions a player must make
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//

	public boolean shouldOpen(PotOfMoney pot) {
		return true;
	}

	public boolean shouldSee(PotOfMoney pot) {
		if (pot.getCurrentStake() - stake > bank) {
			notifyInvalidAction("see", "insufficient chips");
			return false;
		}
		return askQuestion("Do you want to call the bet of " +
					addCount(pot.getCurrentStake() - getStake(), "chip", "chips"));
	}

	public boolean shouldRaise(PotOfMoney pot) {
		if (bank < pot.getCurrentStake() * 2 - stake) {
			notifyInvalidAction("raise", "insufficient chips");
			return false;
		}
		return askQuestion("Do you want to raise the bet?");
	}

	public boolean shouldAllIn(PotOfMoney pot) {
		return askQuestion("Do you want to all in?");
	}
}
