package texas.scramble.test;

//import org.junit.test;
import org.junit.jupiter.api.Test;
import texas.scramble.deck.Tile;
import texas.scramble.dictionary.DictionaryTrie;
import texas.scramble.dictionary.FullDictionary;
import texas.scramble.hand.HandElement;
import texas.scramble.player.ScrambleComputerPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestScrambleComputerPlayer {

    @Test
    public void testFindAllWords() {
        FullDictionary dict = FullDictionary.getInstance();
        ScrambleComputerPlayer player1 = new ScrambleComputerPlayer("Tom", 0, 0);
        String[] letters1 = {"e", "n", "o", "p", "g", "i", "a"};
        String[] letters2 = {"A", "S", "S"};

        //test letters3 and letters4 first, these two should have worked
        String[] letters3 = {"U", "N", "O", "S"};
        String[] letters6 = {"A", "B", "C", "D"};
        String[] letters4 = {"X", "Y", "E", "N", "O", "P", "G"};
        String[][] letters5 = {{"X", "Y", "E", "N", "O", "P", "G"}, {"X", "Y", "E", "N", "O", "P", "G"}, {"X", "Y", "E", "N", "O", "P", "G"}, {"X", "Y", "E", "N", "O", "P", "G"}, {"X", "Y", "E", "N", "O", "P", "G"}, {"X", "Y", "E", "N", "O", "P", "G"}, {"X", "Y", "E", "N", "O", "P", "G"}};
        String[] letters7 = {"E", "N", "O", "P", "G", "X", "Y"};
        String[] letters8 = {" ", " ", "A"};
        List<String> allWords = dict.findAllWords(letters8);
        for(String word: allWords){
            System.out.println(word);
        }

//        List<String> maxScoreWord3 = player1.findAllWords(letters4);
//        System.out.println(maxScoreWord3);

//        for(String[] letter: letters5){
//            List<String> maxScoreWord2 = player1.findAllWords(letter);
//        }
//        for(String[] letter: letters5){
//            List<String> maxScoreWord2 = player1.findAllWords(letter);
//            System.out.println(maxScoreWord2);
//        }
    }

    @Test
    public void testFindHighestScoreWord() {
        FullDictionary dict = FullDictionary.getInstance();
        ScrambleComputerPlayer player1 = new ScrambleComputerPlayer("Tom", 0, 0);
        String[] letters2 = {"C"};
        String[] letters3 = {"X", "Y", "E", "N", "O", "P", "G"};
        String[] letters5 = {"A", " ", " "};
        String[] letters8 = {"A", "B", "C", "D", "E"};
        String[] letters4 = {"XYENOPG", "XYENOPG", "XYENOPG", "XYENOPG", "XYENOPG", "XYENOPG", "XYENOPG"};
        String letters7 = "ZGHQJ";//these letters can not form any words
//        HashMap<String, Integer> maxScoreWord = player1.findHighestScoreWord(letters5, dict);
        String maxScoreWord = player1.findHighestScoreWord(letters2, dict);
        System.out.println("word with highest score : " + maxScoreWord);
//        for(String letter: letters4){
//            HashMap<String, Integer> maxScoreWord2 = player1.findHighestScoreWord(letter);
//            System.out.println("max = "+maxScoreWord2);
//        }
    }


    @Test
    public void testPreFlopRound(){
        FullDictionary dict = FullDictionary.getInstance();
        ScrambleComputerPlayer player1 = new ScrambleComputerPlayer("Tom", 0, 0);
        Random dice = new Random(System.currentTimeMillis());

        /********|||********/
        HandElement[] hand1 = {new Tile("D", 2), new Tile("Y", 4)};
        int risk1 = player1.preFlopRiskToleranceHelper(hand1);
        System.out.println("risk1 = "+risk1);
        System.out.println("For shouldSee: \n"+"getRiskTolerance() = "+risk1+"\n Math.abs(dice.nextInt()) % 120 = "+Math.abs(dice.nextInt()) % 120);
    }
    @Test
    public void testFlopRound() {
        FullDictionary dict = FullDictionary.getInstance();
        ScrambleComputerPlayer player1 = new ScrambleComputerPlayer("Tom", 0, 0);
        HashMap<String, Integer> wordsScore = new HashMap<>();
        //test when there are two unknown letters which are blanks
        String[] flopLetters3 = {"Z", "A", "J", "H", "H"};
        String[] flopCommunityLetters3 = {"J", "A", "Z"};
        String[] turnLetters2 = {"E", "A", "I", " ", " ", "E"};
        String[] turnCommunityLetters2 = {"I", " ", "A", "E"};
        String[] turnLetters3 = {"Z", "G", "H", "B", "C", "A"};
        //ArrayList<String> allCombination = player1.findAllCombination(flopLetters2, 2);
        List<String> allWords = dict.findAllWords(turnLetters2);
        float averageScore = 0;
        for (String word : allWords) {
            System.out.println("word = "+word);
            averageScore += player1.calculateWordScore(word);
        }
        System.out.println("allWords size = "+allWords.size());
        System.out.println("total score = "+averageScore);
        averageScore = averageScore/allWords.size();




        float averageCommunityLettersScore=0;
        int totalNumber = 0;
        List<String> allCommunityWords = dict.findAllWords(turnCommunityLetters2);
        for(String word: allCommunityWords){
            averageCommunityLettersScore+=player1.calculateWordScore(word);
            totalNumber++;
        }
        System.out.println("\n");
        System.out.println("allWords size = "+allCommunityWords.size());
        System.out.println("total score = "+averageCommunityLettersScore);
        averageCommunityLettersScore =  averageCommunityLettersScore/totalNumber;
        if(averageScore>averageCommunityLettersScore){
            System.out.println("averageScore = " + averageScore);
            System.out.println("averageCommunityLettersScore = "+averageCommunityLettersScore);
        }else {
            System.out.println("averageScore = " + averageScore);
            System.out.println("averageCommunityLettersScore = "+averageCommunityLettersScore);
        }
        /*ComputerScramblePlayer player1 = new ComputerScramblePlayer("Tom", 0, 0);
        HashMap<String, Integer> highestWords = new HashMap<>();
        //test when there are two unknown letters which are blanks
        String[] playerLetters3 = {"Z", "G", "H"};
        String[] playerLetters2 = {"Z", "G", "H", " ", " "};
        ArrayList<String> result2 = player1.findAllCombination(playerLetters3, 4);
        for (String re : result2) {
            //System.out.println(player1.findHighestScoreWord(re));
            highestWords.putAll(player1.findHighestScoreWord(re));
        }
        int finalScore = 0;
        for (Map.Entry<String, Integer> entry : highestWords.entrySet()) {
            finalScore += entry.getValue();
        }
        System.out.println("final score = " + finalScore);
        System.out.println("Size = " + result2.size());*/
    }

    @Test
    public void testTurnRound() {
        FullDictionary dict = FullDictionary.getInstance();
        ScrambleComputerPlayer player1 = new ScrambleComputerPlayer("Tom", 0, 0);
        HashMap<String, Integer> wordsScore = new HashMap<>();
        //test when there are two unknown letters which are blanks
        String[] flopLetters2 = {"E", "A", "J", "Q", "Z"};
        String[] flopLetters3 = {"Q", "Z", "J", "X", "K"};
        String[] communityLetters2 = {"Q", "A", "J"};
        String[] communityLetters3 = {"Q", "Z", "J"};
        String[] turnLetters2 = {"Z", "G", "H", " ", " ", "A"};
        List<String> allWords = dict.findAllWords(flopLetters2);
        float averageScore = 0;
        for (String word : allWords) {
            System.out.println("word = "+word);
            averageScore += player1.calculateWordScore(word);
        }
        System.out.println("allWords size = "+allWords.size());
        System.out.println("total score = "+averageScore);
        averageScore =  averageScore/allWords.size();



        float averageCommunityLettersScore=0;
        int totalNumber = 0;
        for(String word: dict.findAllWords(communityLetters2)){
            averageCommunityLettersScore+=player1.calculateWordScore(word);
            totalNumber++;
        }
        averageCommunityLettersScore = averageCommunityLettersScore/totalNumber;
        if(averageScore>averageCommunityLettersScore){
            System.out.println("averageScore = " + averageScore);
            System.out.println("averageCommunityLettersScore = "+averageCommunityLettersScore);
        }else {
            System.out.println("averageScore = " + averageScore);
            System.out.println("averageCommunityLettersScore = "+averageCommunityLettersScore);
        }
    }
    @Test
    public void testFindAllWordsFormedByLetters(){
        ScrambleComputerPlayer player1 = new ScrambleComputerPlayer("Tom", 0, 0);
        DictionaryTrie dict = FullDictionary.getInstance();
        String[] letters1 = {"A", "A", "E", "E"};
        String[] letters2 = {"E", "N", "O", "P", "G", "X", "Y"};
        List<String> result = dict.findAllWordsFormedByLetters(letters2);
        for(String word: result){
            System.out.println(word);
        }

    }
    @Test
    public void testCalculateAverageScoreOfAllWordsContainCommunityLetters() {
        ScrambleComputerPlayer player1 = new ScrambleComputerPlayer("Tom", 0, 0);
        String[] communityLetters1 = {"A", "B", "C"};
        String[] communityLetters2 = {"Z", "G", "H"};
        FullDictionary dict = FullDictionary.getInstance();
        int averageCommunityLettersScore=0;
        int totalNumber = 0;
        for(String word: dict.findAllWords(communityLetters2)){
            averageCommunityLettersScore+=player1.calculateWordScore(word);
            totalNumber++;
        }
        averageCommunityLettersScore = averageCommunityLettersScore/totalNumber;
        System.out.println("average score = "+averageCommunityLettersScore);
    }
    @Test
    public void testWordIsFormedByLettersContained(){
        ScrambleComputerPlayer player1 = new ScrambleComputerPlayer("Tom", 0, 0);
        FullDictionary dict = FullDictionary.getInstance();
        /********|||********/
        String prefix = "ABC";
        HashMap<String, Integer> letters = new HashMap<>();
        //A" "" "
        letters.put("A", 1);
        letters.put(" ", 2);
        boolean result = dict.wordIsFormedByLettersContained(prefix, letters);
        assertEquals(true, result);
        /********|||********/
        String prefix1 = "ADC";
        HashMap<String, Integer> letters1 = new HashMap<>();
        //AMM" "" "
        letters1.put("A", 1);
        letters1.put("M", 2);
        letters1.put(" ", 2);
        boolean result1 = dict.wordIsFormedByLettersContained(prefix1, letters1);
        assertEquals(true, result1);
        /********|||********/
        String prefix2 = "LG";
        HashMap<String, Integer> letters2 = new HashMap<>();
        //AMM" "" "
        letters2.put("A", 1);
        letters2.put("M", 2);
        letters2.put(" ", 2);
        boolean result2 = dict.wordIsFormedByLettersContained(prefix2, letters2);
        assertEquals(true, result2);
        /********|||********/
        String prefix3 = "LGK";
        HashMap<String, Integer> letters3 = new HashMap<>();
        //AMM" "" "
        letters3.put("A", 1);
        letters3.put("M", 2);
        letters3.put(" ", 2);
        boolean result3 = dict.wordIsFormedByLettersContained(prefix3, letters3);
        assertEquals(false, result3);
        /********|||********/
        String prefix4 = "AMMPK";
        HashMap<String, Integer> letters4 = new HashMap<>();
        //AMM" "" "
        letters4.put("A", 1);
        letters4.put("M", 2);
        letters4.put(" ", 2);
        boolean result4 = dict.wordIsFormedByLettersContained(prefix4, letters4);
        assertEquals(true, result4);
        /********|||********/
        String prefix5 = "APPLE";
        HashMap<String, Integer> letters5 = new HashMap<>();
        //AMM" "" "
        letters5.put("A", 1);
        letters5.put("P", 2);
        letters5.put("L", 1);
        letters5.put("E", 1);
        boolean result5 = dict.wordIsFormedByLettersContained(prefix5, letters5);
        assertEquals(true, result5);
    }
    @Test
    public void testSubmitWords(){
        ScrambleComputerPlayer player1 = new ScrambleComputerPlayer("Tom", 0, 0);
        /********|||********/
        String[] lettersOnHand1 = {"A", "B", "C", "D", "E"};
        HashMap<String, Integer> result1 = player1.submitWords(lettersOnHand1);
        for(Map.Entry<String, Integer> entry: result1.entrySet()){
            System.out.println(entry);
        }
        System.out.println("*************");
        /********|||********/
        String[] lettersOnHand2 = {"A", "B", "M", " ", "E", "Y", "A"};// 1 3 3 1 4 1 = 13
        HashMap<String, Integer> result2 = player1.submitWords(lettersOnHand2);
        for(Map.Entry<String, Integer> entry: result2.entrySet()){
            System.out.println(entry);
        }
        HashMap<String, Integer> result3 = player1.substituteBlank(result2, lettersOnHand2);
        System.out.println("Result 3:");
        for(Map.Entry<String, Integer> entry: result3.entrySet()){
            System.out.println(entry);
        }
        System.out.println("*************");
    }
    @Test
    public void testSubstituteBlank(){
        ScrambleComputerPlayer player1 = new ScrambleComputerPlayer("Tom", 0, 0);
        /********|||********/
        HashMap<String, Integer> words = new HashMap<>();
        words.put("APPLE", 15);
        words.put("ME", 20);
        String[] lettersOnHand1 = {"A", "P", "L", "E", "M", " ", " "};//1 3 1 1 3
        HashMap<String, Integer> result1 = player1.substituteBlank(words, lettersOnHand1);
        for(Map.Entry<String, Integer> entry: result1.entrySet()){
            System.out.println(entry);
        }
        System.out.println("*************");
        /********|||********/
        HashMap<String, Integer> words1 = new HashMap<>();
        words1.put("ORANGE", 15);
        words1.put("AI", 20);
        String[] lettersOnHand2 = {"O", "R", "A", "G", "E", " ", " "};//1 1 1 2 1 = 6
        HashMap<String, Integer> result2 = player1.substituteBlank(words1, lettersOnHand2);
        for(Map.Entry<String, Integer> entry: result2.entrySet()){
            System.out.println(entry);
        }
        System.out.println("*************");
    }

    @Test
    public void testRiskToleranceGenerator() {
        Random r = new Random();

        for (int i = 0; i < 1000; i++) {
            int risk = r.nextInt(120 - 50 + 1) + 50;

            if (risk < 50 || risk > 120) fail();
        }
    }
}

