
package poker;

// This package provides classes necessary for implementing a game system for playing poker


public class High extends PokerHand {
	
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Constructors
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	
	public High(Card[] hand, DeckOfCards deck) {
		super(hand, deck);
	}

	
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// What is the riskworthiness of this hand?
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	
	public int getRiskWorthiness() {
		if (getCard(4).getRank() == 1) {
			return 100 - PokerHand.HIGHCARD_RISK + 14;
		} else {
			return 100 - PokerHand.HIGHCARD_RISK + getCard(0).getValue();
		}
	}

	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// What is the value of this hand?
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	
	public int getValue() {
		if (getCard(4).getRank() == 1) // an ace
			return getCard(4).getValue() + getCard(3).getValue() + getCard(2).getValue()
					+ getCard(1).getValue() + getCard(0).getValue();
		else
			return getCard(0).getValue() + getCard(4).getValue() + getCard(3).getValue()
					+ getCard(2).getValue() + getCard(1).getValue();
	}
	
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Discard and redeal some cards
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	
	public PokerHand discard() {
		if (getCard(4).getRank() == 1)
			return discard(1,2,3);
		else
			return discard(2,3,4);
	}

	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Display
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	
	public String toString() {
		if (getCard(4).getRank() == 1)
			return "Ace High: " + super.toString();
		else
			return getCard(0).getName() + " High: " + super.toString();
	}
	
}
