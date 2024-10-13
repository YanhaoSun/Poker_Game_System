
package poker;

// This package provides classes necessary for implementing a game system for playing poker


import java.util.ArrayList;
import java.util.List;

public class PotOfMoney
{	
	private int total = 0; // the total amount of money in the table, waiting to be won
	
	private int stake = 0; // the current highest stake expected 
						   // of each player to stay in the game
	private ArrayList<Integer> playerIds;
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Constructor
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	
	public PotOfMoney() {}

	public PotOfMoney(PotOfMoney other) {this.stake = other.stake;}
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Display Behaviour 
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	
	public String toString() {
		return "There is a pot of " + total + " chip(s) on the table";
	}
	
	
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Accessors
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setStake(int stake) {
		this.stake = stake;
	}

	public int getCurrentStake() {
		return stake;
	}

	public ArrayList<Integer> getPlayerIds() {
		return playerIds;
	}

	public void setPlayerIds(ArrayList<Integer> playerIds){
		this.playerIds=playerIds;
	}

	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Modifiers
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	
	public void raiseStake(int addition) {

		stake += addition;

		addToPot(stake);

	}
	
	
	public void addToPot(int addition) {
		total += addition;
	}
	public void removeFromPot(int addition) {
		total -= addition;
	}

	public void takeFromPot(int addition) {
		total -= addition;
	}
	public void clearPot() {
		total = 0;
	}
	
	
	public int takePot() {
	    // when the winner of a hand takes the pot as his/her winnings  
		
		int winnings = getTotal();
		
		clearPot();
		
		return winnings;
	}

	public int takePot(int numberOfWinner) {
		// when the winner of a hand takes the pot as his/her winnings

		int winnings = getTotal()/numberOfWinner;
		return winnings;
	}
	
}