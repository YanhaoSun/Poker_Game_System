
package poker;

// This package provides classes necessary for implementing a game system for playing poker


public class FullHouse extends PokerHand {
	
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Constructors
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	
	public FullHouse(Card[] hand, DeckOfCards deck)	{
		super(hand, deck);
	}


	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// What is the riskworthiness of this hand?
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	
	public int getRiskWorthiness()	{
		return 100 - FULLHOUSE_RISK; 
	}

	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// What is the value of this hand?
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	
	public int getValue()	{
		if (getCard(0).getRank() == getCard(2).getRank())  // triple + pair
			return PokerHand.FULLHOUSE_VALUE + getCard(0).getValue()*100 + getCard(3).getValue();
		else
			return PokerHand.FULLHOUSE_VALUE + getCard(2).getValue()*100 + getCard(0).getValue();
	}
	
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Display
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	
	public String toString() 	{
		return "Full House: " + super.toString();
	}
	
}

