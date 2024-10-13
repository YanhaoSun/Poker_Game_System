package texas;

import poker.*;
import texas.scramble.deck.DeckOfTiles;
import texas.scramble.dictionary.DictionaryTrie;
import texas.scramble.hand.HandElement;

import java.util.*;

public abstract class TexasPlayer extends poker.Player {
	public final int NUM_CARDS_DEALT = 2;

	public final int NUM_CARDS_REQUIRED_FOR_FULL_HAND = 3;

	private int id;

	private Hand currentBestHand = null;

	private boolean allIn;

	private boolean hasSidePot;

	protected boolean dealer = false;

	private boolean onTurn = false;

	private Deck deck;

	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Constructor
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	
	public TexasPlayer(String name, int money,int id)	{
		super(name, money);
		this.id = id;
		allIn = false;
		hasSidePot =false;
	}


	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Reset internal state for start of new hand of poker  
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//

	public void resetDealer() {
		dealer=false;
	}
	public void reset() {
		folded = false;
		allIn = false;
		hasSidePot =false;
		stake  = 0;
		totalStake=bank;
	}

	public void resetStake() {
		stake = -1;
	}

	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Display Behaviour 
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	
	public String toString() {
		if (hasFolded())
			return "> " + getName() + " has folded, and has " + addCount(getBank(), "chip", "chips") + " in the bank.";
		else
			return "> " + getName() + " has  " + addCount(getBank(), "chip", "chips") + " in the bank";
	}
	
	
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Accessors
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	public boolean isOnTurn() {
		return onTurn;
	}
	public void setOnTurn(boolean onTurn) {
		this.onTurn = onTurn;
	}
	public Hand getHand() {
		return hand;
	}

	public boolean isDealer(){
		return dealer;
	}

	public int getId() {
		return id;
	}

	public Hand getCurrentBestHand() {
		if (currentBestHand == null) {
			return hand;
		}
		return currentBestHand;
	}

	public Deck getDeck(){
		return deck;
	}

	public boolean isAllIn() {
		return allIn;
	}
	public boolean hasSidePot() {
		return hasSidePot;
	}
	public void SetSidePot() {
		hasSidePot=true;
	}
	public void addBank(int add) {
		bank+=add;
	}

	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Modifiers
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	@Override
	public void fold() {
		// make this player give up
		if (!folded)
			System.out.println("\n> " + getName() + " says: I fold!\n");

		folded = true;
	}

	public void dealTo(Deck deck) {
		hand = deck.dealHand(NUM_CARDS_DEALT);
	}

	public void allIn() {
		allIn = true;
	}
	public void setId(int id) {
		this.id = id;
	}

	//every player can act as a dealer
	public void setDealer(boolean dealer) {
		this.dealer = dealer;
	}

	public void setDeck(Deck deck){
		this.deck = deck;
	}

	@Override
	public void takePot(PotOfMoney pot) {
		// when the winner of a hand takes the pot as his/her winnings

		System.out.println("\n> " + getName() + " says: I WIN " + addCount(pot.getTotal(), "chip", "chips") + "!\n");
		System.out.println(getCurrentBestHand());

		bank += pot.takePot();

		System.out.println(this);
	}

	// Computes best hand using player's hole cards and the public cards

	public void findBestHand(Card[] publicCards, DeckOfCards deck) {
		if (publicCards.length < NUM_CARDS_REQUIRED_FOR_FULL_HAND) {
			return;
		}

		Card[] data = new Card[5];

		int aLen = hand.getHand().length;
		int bLen = publicCards.length;

		Card[] result = new Card[aLen + bLen];
		System.arraycopy((Card[])hand.getHand(), 0, result, 0, aLen);
		System.arraycopy(publicCards, 0, result, aLen, bLen);

		List<Hand> hands = new ArrayList<>();
		combinationUtil(result, data, 0, result.length - 1, 0, 5, hands, deck);
		Hand bestHand = hands.get(0);
		for (Hand hand : hands) {
			if (bestHand.getValue() < hand.getValue()) {
				bestHand = hand;
			}
		}

		currentBestHand = bestHand;
	}

	// Utility recursive function for generating all combinations of cards and adding to list

	private void combinationUtil(Card[] arr, Card[] data, int start, int end, int index, int r, List<Hand> hands, DeckOfCards deck) {
		// data combines hand with 5 cards
		if (index == r) {
			Hand newHand = new PokerHand(Arrays.copyOf(data, data.length), deck);
			newHand = newHand.categorize();
			hands.add(newHand);
			return;
		}

		// replace index with all possible elements. The condition
		// "end-i+1 >= r-index" makes sure that including one element
		// at index will make a combination with remaining elements
		// at remaining positions
		for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
			data[index] = arr[i];
			combinationUtil(arr, data, i+1, end, index+1, r, hands, deck);
		}
	}

	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Methods to handle blinds
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//

	public void smallBlind(int smallBlind, PotOfMoney pot) {
		stake += smallBlind;
		bank -= smallBlind;
		pot.raiseStake(smallBlind);
	}

	public void bigBlind(int bigBlind, PotOfMoney pot) {
		stake += bigBlind * 2;
		bank -= bigBlind * 2;
		pot.raiseStake(bigBlind);
	}

	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Actions a player can make
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//



	public void allIn(PotOfMoney pot) {
		if (getBank() == 0) return;
		stake += bank;

		if (stake > pot.getCurrentStake()){
			pot.setStake(stake);
		}

		pot.addToPot(bank);

		bank = 0;
		allIn =true;

		System.out.println("\n> " + getName() + " says: and I all in!\n");
	}
	
	@Override
	public void seeBet(PotOfMoney pot) {
		int needed  = pot.getCurrentStake() - getStake();

		if (needed > getBank()) {
			return;
		}
		if (needed == 0) {
			System.out.println("\n> " + getName() + " says: I check!\n");
			return;
		}

		stake += needed;
		bank  -= needed;

		pot.addToPot(needed);
		if(bank==0){
			allIn();
			System.out.println("\n> " + getName() + " says: and I all in!\n");
		}else {
			System.out.println("\n> " + getName() + " says: I call that " + addCount(needed, "chip", "chips") + "!\n");
		}

	}

	public void winFromPot(int chips,PotOfMoney pot) {
		// when the winner of a hand takes the pot as his/her winnings

		System.out.println("\n> " + getName() + " says: I WIN " + addCount(chips, "chip", "chips") + "!\n");
		//System.out.println(hand.toString());

		bank += chips;
		pot.takeFromPot(chips);
		//System.out.println(this);
	}

	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Key decisions a player must make
	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//

    protected abstract boolean shouldAllIn(PotOfMoney pot);

	public abstract Action chooseAction(PotOfMoney pot);

	//--------------------------------------------------------------------//
	//--------------------------------------------------------------------//
	// Game actions are scheduled here
	//--------------------------------------------------------------------//
	//---------------------------------------------------------------------//
	
	public void nextAction(PotOfMoney pot) {
		if (hasFolded()) return;  // no longer in the game

		if (isAllIn()) return; // cannot make anymore actions due to all inning

		if (stake==-1) {
			stake = 0;
		}

		switch (chooseAction(pot)) {
			case SEE -> seeBet(pot);
			case RAISE -> raiseBet(pot);
			case ALL_IN -> allIn(pot);
			case FOLD -> fold();
			default -> {}
		}
	}


	/********************** For Texas Scramble ************************/

	//findHighestScoreWord will return the word with highest score among all words returned by findAllWords
	public String findHighestScoreWord(String[] letters, DictionaryTrie dict){
		//find all words that these letters can form
		List<String> allWords = dict.findAllWordsFormedByLetters(letters);
		//if all words is empty, this means current letters on player's hand cna not form any words
		if(allWords.isEmpty()){
			return "^";
		}
		//calculate score of each of these words
		HashMap<String, Integer> recordWordsScore = new HashMap<>();
		for(String word: allWords){
			recordWordsScore.put(word, calculateWordScore(word));
		}
		return findHighestScoreWordHelper(recordWordsScore);
	}
	public String findHighestScoreWordHelper(HashMap<String, Integer> recordWordsScore){
		ArrayList<String> storeHighestScoreWords = new ArrayList<>();
		//find those words with highest score
		int maxScore = Collections.max(recordWordsScore.values());
		for (Map.Entry<String, Integer> entry : recordWordsScore.entrySet()) {
			if (entry.getValue() == maxScore) {
				storeHighestScoreWords.add(entry.getKey());
			}
		}
		//if there is only one word with highest score, return this word,
		//otherwise, randomly choose one from those words with highest score
		if(storeHighestScoreWords.size()==1){
			return storeHighestScoreWords.get(0);
		}else {
			Random rand = new Random();
			int randomIndex = rand.nextInt(storeHighestScoreWords.size());
			return storeHighestScoreWords.get(randomIndex);
		}
	}

	public int calculateWordScore(String word){
		int score = 0;
		for(int i=0; i<word.length(); i++) {
			switch (word.charAt(i)) {
				case 'E', 'A', 'I', 'O', 'N', 'R', 'T', 'L', 'S', 'U' -> score += 1;
				case 'D', 'G' -> score+=2;
				case 'B', 'C', 'M', 'P' -> score+=3;
				case 'F', 'H', 'V', 'W', 'Y' -> score+=4;
				case 'K' -> score+=5;
				case 'J', 'X' -> score+=8;
				case 'Q', 'Z' -> score+=10;
				default -> score+=0;
			}
		}
		if(word.length()==7){
			return score+100;
		}
		return score;
	}

}