package texas.scramble.player;

import poker.Card;
import poker.PotOfMoney;
import texas.Action;
import texas.RoundOfTexas;
import texas.Rounds;
import texas.TexasComputerPlayer;
import texas.scramble.dictionary.DictionaryTrie;
import texas.scramble.dictionary.FullDictionary;
import texas.scramble.deck.Tile;
import texas.scramble.hand.HandElement;

import java.util.*;

import static texas.Action.*;
import static texas.Action.FOLD;

public class ScrambleComputerPlayer extends TexasComputerPlayer {
    public static final int VARIABILITY_UPPER = 120;
    public static final int VARIABILITY_LOWER = 50;
    public final int averageHandValue = 4;
    private int riskTolerance;  // willingness of a player to take risks and bluff
    private Random dice = new Random(System.currentTimeMillis());

    private DictionaryTrie dict;

    public ScrambleComputerPlayer(String name, int money, int id) {
        super(name, money, id);

        dict = new DictionaryTrie("resources/hard.txt");

        riskTolerance = dice.nextInt(VARIABILITY_UPPER - VARIABILITY_LOWER + 1) + VARIABILITY_LOWER;
        // this gives a range of tolerance between VARIABILITY_LOWER to VARIABILITY_UPPER
    }

    public ScrambleComputerPlayer(String name, int money, int id, String pathToDictionary) {
        super(name, money, id);

        dict = new DictionaryTrie(pathToDictionary);

        riskTolerance = dice.nextInt(VARIABILITY_UPPER - VARIABILITY_LOWER + 1) + VARIABILITY_LOWER;
        // this gives a range of tolerance between VARIABILITY_LOWER to VARIABILITY_UPPER
    }

    public ScrambleComputerPlayer(String name, int money, int id, String pathToDictionary, int riskTolerance) {
        super(name, money, id);
        dict = new DictionaryTrie(pathToDictionary);
        this.riskTolerance = riskTolerance;
    }

    public boolean knowsWord(String word) {
        return dict.isValidWord(word);
    }

    public void learnWord(String word) {
        dict.add(word);
    }

    public int getRiskTolerance(PotOfMoney pot) {
        int risk = 0;
        risk = riskTolerance - (pot.getCurrentStake()-getStake()) + predicateRiskTolerance();
        return risk; // tolerance drops as stake increases
    }


    public Card getCard(int num, Card[] hand) {
        if (num >= 0 && num < hand.length) {
            return hand[num];
        } else {
            return null;
        }
    }

    public float averageScoreCalculator(List<String> allWords) {
        int averageScore = 0;
        for (String word : allWords) {
            averageScore += calculateWordScore(word);
        }
        return (float) averageScore / allWords.size();
    }

    /********************** predicate riskTolerance of different rounds ************************/
    public int predicateRiskTolerance() {
        Tile[] publicCards = getCommunityElements().toArray(new Tile[getCommunityElements().size()]);
        Rounds currentRound = getCurrentRound();
        int risk = 0;
        //for pre-flop round
        if (currentRound == Rounds.PRE_FLOP) {
            risk += preFlopRiskToleranceHelper(super.getHand().getHand());
        }
        //for flop and turn round
        if (currentRound == Rounds.FLOP || currentRound == Rounds.TURN) {
            risk += predicateBestWordAndRisk(publicCards, currentRound);
        }
        //for river round
        if (currentRound == Rounds.RIVER) {
            risk += riverRoundRiskToleranceHelper(publicCards);
        }
        return risk;
    }
    /******************** put community cards and letters on player's hand to one single String array ********************/
    public String[] combineCommunityAndLettersOnHand(Tile[] publicCards, HandElement[] lettersOnNHand){
        ArrayList<String> letters = new ArrayList<>();//store letters both from community letters and letters on hand
        for(Tile communityLetter: publicCards){
            letters.add(communityLetter.name());
        }
        for(HandElement letterOnHand: lettersOnNHand){
            letters.add(letterOnHand.toString());
        }
        return letters.toArray(new String[0]);
    }

    /******************** predicate pre-flop round risk ********************/
    private int calculateHandScore(HandElement[] hand) {
        int score = 0;
        for (HandElement handElement : hand) {
            switch (handElement.toString()) {
                case "E", "A", "I", "O", "N", "R", "T", "L", "S", "U" -> score += 1;
                case "D", "G" -> score += 2;
                case "B", "C", "M", "P" -> score += 3;
                case "F", "H", "V", "W", "Y" -> score += 4;
                case "K" -> score += 5;
                case "J", "X" -> score += 6;
                case "Q", "Z" -> score += 7;
                default -> score += 5;
            }
        }
        return score;
    }

    public int preFlopRiskToleranceHelper(HandElement[] hand) {
        int risk = 0;
        if (calculateHandScore(hand) >= averageHandValue) {
            risk = 28;
        } else {
            risk = 15;
        }
        return risk;
    }

    /********************** predicate flop and turn round risk ************************/
    public int predicateBestWordAndRisk(Tile[] publicCards, Rounds currentRound) {
        int risk = 0;
        FullDictionary dict = FullDictionary.getInstance();
        //combine community letters and letters on hand
        String[] lettersOnHand = combineCommunityAndLettersOnHand(publicCards, this.getHand().getHand());
        //obtain community letters
        ArrayList<String> community = new ArrayList<>();
        for(Tile publicCard: publicCards){
            community.add(publicCard.name());
        }
        String[] communityLetters = community.toArray(new String[0]);
        //calculate average score of current community letters score
        float averageCommunityLettersScore = averageScoreCalculator(dict.findAllWords(communityLetters));
        //calculate average score of words that can be formed by players' current letters
        float averageScore = averageScoreCalculator(dict.findAllWords(lettersOnHand));
        //we compare the average score of current player and average score of community letters
        if (averageScore >= averageCommunityLettersScore) {
            //if averageScore is higher than averageCommunityLettersScore, this means player's cards would be better in average
            risk = 35;
        } else {
            //otherwise, player's cards would not be better in average, player will not take more risk for actions
            risk = 20;
        }
        return risk;
    }

    /********************** predicate river round ************************/
    public int riverRoundRiskToleranceHelper(Tile[] publicCards) {
        int risk = 0;
        DictionaryTrie dict = FullDictionary.getInstance();
        //combine community letters and letters on hand
        String[] lettersOnHand = combineCommunityAndLettersOnHand(publicCards, this.getHand().getHand());
        //obtain community letters
        ArrayList<String> community = new ArrayList<>();
        for(Tile publicCard: publicCards){
            community.add(publicCard.name());
        }
        String[] communityLetters = community.toArray(new String[0]);

        //calculate average score of current community letters score
        float averageCommunityLettersScore = averageScoreCalculator(dict.findAllWords(communityLetters));
        //calculate highest score word that can be formed by players' current letters
        String highestScoreWord = findHighestScoreWord(lettersOnHand, dict);
        float wordScore = calculateWordScore(highestScoreWord);
        //we compare the word score of current player and average score of community letters
        if (wordScore >= averageCommunityLettersScore) {
            //if word score is higher than averageCommunityLettersScore, this means player's cards would be better in average
            risk = 40;
        } else {
            //otherwise, player's cards would not be better in average, player will not take more risk for actions
            risk = 25;
        }
        return risk;
    }

    /********************** submitWords is used in showDown round, which will calculate the best word of current letters on hand,
     * then obtain remaining letters by minus current word's letters with original letters, then calculate the best word with remaining letters
     * then put those words with their word score in HashMap and return************************/
    public HashMap<String, Integer> submitWords(String[] lettersOnHand){
        HashMap<String, Integer> submitWords = new HashMap<>();
        boolean lettersFinished = false;
        HashMap<String, Integer> lettersContained = new HashMap<>();
        for(String letter: lettersOnHand){
            if(lettersContained.containsKey(letter)){
                lettersContained.put(letter, lettersContained.get(letter)+1);
            }else {
                lettersContained.put(letter, 1);
            }
        }
        String word = findHighestScoreWord(lettersOnHand, dict);
        String[] word1 = word.split("");
        submitWords.put(word, calculateWordScore(word));
        while (!lettersFinished){
            if(word.length()==7){
                lettersFinished = true;
            }else {
                //filter out remaining letters
                for(String letter: word1){
                    if(lettersContained.containsKey(letter) && lettersContained.get(letter)>0){
                        lettersContained.put(letter, lettersContained.get(letter)-1);
                    }else {
                        lettersContained.put(" ", lettersContained.get(" ")-1);
                    }
                }
                if(noMoreRemainingLetters(lettersContained)){
                    return submitWords;
                }
                //put remaining letters to a String[] array
                ArrayList<String> remainingLetters = new ArrayList<>();
                for(Map.Entry<String, Integer> entry: lettersContained.entrySet()){
                    if(entry.getValue()>0){
                        for(int i=0; i<entry.getValue(); i++){
                            remainingLetters.add(entry.getKey());
                        }
                    }
                }
                String[] remainingLetters1 = new String[remainingLetters.size()];
                remainingLetters.toArray(remainingLetters1);
                //find highest score word with remaining letters
                word = findHighestScoreWord(remainingLetters1, dict);
                word1 = word.split("");
                if(!word.equals("^")){
                    submitWords.put(word, calculateWordScore(word));
                }else {
                    return submitWords;
                }
            }
        }
        return substituteBlank(submitWords, lettersOnHand);
    }
    public HashMap<String, Integer> substituteBlank(HashMap<String, Integer> words, String[] lettersOnHand){
        HashMap<String, Integer> lettersContained = new HashMap<>();
        for(String letter: lettersOnHand){
            if(lettersContained.containsKey(letter)){
                lettersContained.put(letter, lettersContained.get(letter)+1);
            }else {
                lettersContained.put(letter, 1);
            }
        }
        HashMap<String, Integer> finalWords = new HashMap<>(words);
        for(Map.Entry<String, Integer> entry: words.entrySet()){
            String[] word1 = entry.getKey().split("");
            ArrayList<String> wordTemp = new ArrayList<>();
            for(String letter: word1){
                if(lettersContained.containsKey(letter) && lettersContained.get(letter)>0){
                    wordTemp.add(letter);
                    lettersContained.put(letter, lettersContained.get(letter)-1);
                }else {
                    wordTemp.add(" ");
                    lettersContained.put(" ", lettersContained.get(" ")-1);
                }
            }
            String word = String.join("", wordTemp);
            finalWords.put(entry.getKey(), calculateWordScore(word));
        }
        return finalWords;
    }
    public boolean noMoreRemainingLetters(HashMap<String, Integer> remainingLetters){
        boolean empty = true;
        for(Map.Entry<String, Integer> entry: remainingLetters.entrySet()){
            if(entry.getValue()!=0){
                empty=false;
            }
        }
        return empty;
    }
    /********************** chooseAction method will determine the action that player should make ************************/
    public Action chooseAction(PotOfMoney pot) {
        ArrayList<Action> action = new ArrayList<>();
        if (shouldRaise(pot)){
             action.add(RAISE);
        }
        if (shouldSee(pot)){
            action.add(SEE);
        }
        if (shouldAllIn(pot) && (action.size()==0 || action.size()==2)){
             action.add(ALL_IN);
        }
        if(action.size()==1){
            return action.get(0);
        }else if(action.size()==0){
            return FOLD;
        }else {
            Random random = new Random();
            int index = random.nextInt(action.size());
            return action.get(index);
        }
    }

    @Override
    public boolean shouldOpen(PotOfMoney pot) {
        return true;
    }

    @Override
    protected boolean shouldSee(PotOfMoney pot) {
        if (pot.getCurrentStake() - stake > bank) {
            return false;
        } else {
            int risk = getRiskTolerance(pot);
            int random = Math.abs(dice.nextInt()) % 60;
            return random <= getCurrentBestHand().getRiskWorthiness() + risk;
        }
    }

    @Override
    protected boolean shouldRaise(PotOfMoney pot) {
        if (bank < pot.getCurrentStake() * 2 - stake || bank < RoundOfTexas.BIG_BLIND_AMOUNT) {
            return false;
        }
        int risk = getRiskTolerance(pot);
        int random = Math.abs(dice.nextInt()) % 60;
        return random <= getCurrentBestHand().getRiskWorthiness() + risk;
    }

    protected boolean shouldAllIn(PotOfMoney pot) {
        int risk = getRiskTolerance(pot);
        int random = Math.abs(dice.nextInt()) % 75;
        return random<=(getCurrentBestHand().getRiskWorthiness()+risk)*0.1;
    }
}