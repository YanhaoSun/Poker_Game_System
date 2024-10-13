package texas.hold_em;

import org.junit.Test;
import poker.Card;
import poker.NumberCard;
import texas.Rounds;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHoldEmComputerPlayer {
    @Test
    public void testRoyalStraightFlush() {
        //can not be RoyalStraightFlush
        System.out.println("--------------------------RoyalStraightFlush Flop Round--------------------------");
        Card[] allCards1 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        HoldEmComputerPlayer RoyalStraightFlush = new HoldEmComputerPlayer("Tom", 50, 0);
        int oddsOfRoyalStraightFlush = RoyalStraightFlush.oddsOfRoyalFlush(allCards1, Rounds.FLOP);
        assertEquals(0, oddsOfRoyalStraightFlush);
        System.out.println("Odds of RoyalStraightFlush: "+oddsOfRoyalStraightFlush);


        //cannot be RoyalStraightFlush
        Card[] allCards2 = new Card[]{
                new NumberCard("Ten", "diamonds", 10),
                new NumberCard("Jack", "diamonds", 11),
                new NumberCard("Queen", "hearts", 12),
                new NumberCard("King", "spades", 13),
                new NumberCard("Ace", "clubs", 1, 14)};
        oddsOfRoyalStraightFlush = RoyalStraightFlush.oddsOfRoyalFlush(allCards2, Rounds.FLOP);
        assertEquals(0, oddsOfRoyalStraightFlush);
        System.out.println("Odds of RoyalStraightFlush: "+oddsOfRoyalStraightFlush);


        //may be RoyalStraightFlush, test case: count=3
        Card[] allCards3 = new Card[]{
                new NumberCard("Ten", "diamonds", 10),
                new NumberCard("Jack", "diamonds", 11),
                new NumberCard("Queen", "diamonds", 12),
                new NumberCard("King", "hearts", 13),
                new NumberCard("Ace", "spades", 1, 14)};
        oddsOfRoyalStraightFlush = RoyalStraightFlush.oddsOfRoyalFlush(allCards3, Rounds.FLOP);
        assertEquals(4, oddsOfRoyalStraightFlush);
        System.out.println("Odds of RoyalStraightFlush when count is 3: "+oddsOfRoyalStraightFlush);

        //must be RoyalStraightFlush, test case: count=4
        Card[] allCards4 = new Card[]{
                new NumberCard("Ten", "diamonds", 10),
                new NumberCard("Jack", "diamonds", 11),
                new NumberCard("Queen", "diamonds", 12),
                new NumberCard("King", "diamonds", 13),
                new NumberCard("Deuce", "diamonds", 1, 14)};
        oddsOfRoyalStraightFlush = RoyalStraightFlush.oddsOfRoyalFlush(allCards4, Rounds.FLOP);
        assertEquals(2, oddsOfRoyalStraightFlush);
        System.out.println("Odds of RoyalStraightFlush when count is 4: "+oddsOfRoyalStraightFlush);

        //must be RoyalStraightFlush, test case: count=5
        Card[] allCards5 = new Card[]{
                new NumberCard("Ten", "diamonds", 10),
                new NumberCard("Jack", "diamonds", 11),
                new NumberCard("Queen", "diamonds", 12),
                new NumberCard("King", "diamonds", 13),
                new NumberCard("Ace", "diamonds", 1, 14)};
        oddsOfRoyalStraightFlush = RoyalStraightFlush.oddsOfRoyalFlush(allCards5, Rounds.FLOP);
        assertEquals(100, oddsOfRoyalStraightFlush);
        System.out.println("Odds of RoyalStraightFlush when count is 5: "+oddsOfRoyalStraightFlush);

        System.out.println("--------------------------RoyalStraightFlush Turn Round--------------------------");
        //cannot be RoyalStraightFlush, test case: count=3
        Card[] allCards6 = new Card[]{
                new NumberCard("Ten", "diamonds", 10),
                new NumberCard("Jack", "diamonds", 11),
                new NumberCard("Queen", "diamonds", 12),
                new NumberCard("King", "hearts", 13),
                new NumberCard("Ace", "spades", 1, 14),
                new NumberCard("Three", "hearts", 3)};
        oddsOfRoyalStraightFlush = RoyalStraightFlush.oddsOfRoyalFlush(allCards6, Rounds.TURN);
        assertEquals(0, oddsOfRoyalStraightFlush);
        System.out.println("Odds of RoyalStraightFlush when count is 3: "+oddsOfRoyalStraightFlush);

        //may be RoyalStraightFlush, test case: count=4
        Card[] allCards7 = new Card[]{
                new NumberCard("Ten", "diamonds", 10),
                new NumberCard("Jack", "diamonds", 11),
                new NumberCard("Queen", "diamonds", 12),
                new NumberCard("King", "diamonds", 13),
                new NumberCard("Deuce", "diamonds", 1, 14),
                new NumberCard("Three", "hearts", 3)};
        oddsOfRoyalStraightFlush = RoyalStraightFlush.oddsOfRoyalFlush(allCards7, Rounds.TURN);
        assertEquals(2, oddsOfRoyalStraightFlush);
        System.out.println("Odds of RoyalStraightFlush when count is 4: "+oddsOfRoyalStraightFlush);

        //must be RoyalStraightFlush, test case: count=5
        Card[] allCards8 = new Card[]{
                new NumberCard("Ten", "diamonds", 10),
                new NumberCard("Jack", "diamonds", 11),
                new NumberCard("Queen", "diamonds", 12),
                new NumberCard("King", "diamonds", 13),
                new NumberCard("Ace", "diamonds", 1, 14),
                new NumberCard("Three", "hearts", 3)};
        oddsOfRoyalStraightFlush = RoyalStraightFlush.oddsOfRoyalFlush(allCards8, Rounds.TURN);
        assertEquals(100, oddsOfRoyalStraightFlush);
        System.out.println("Odds of RoyalStraightFlush when count is 5: "+oddsOfRoyalStraightFlush);
    }

    @Test
    public void testStraightFlush(){
        System.out.println("--------------------------StraightFlush Flop Round--------------------------");

        //may be StraightFlush, test case: both odds of Straight and Flush is greater than 0 and less than 100
        Card[] allCards1 = new Card[]{
                new NumberCard("Seven", "diamonds", 7),
                new NumberCard("Nine", "diamonds", 9),
                new NumberCard("Ten", "diamonds", 10),
                new NumberCard("Jack", "hearts", 11),
                new NumberCard("Ace", "spades", 1, 14)};
        HoldEmComputerPlayer Straight = new HoldEmComputerPlayer("Tom", 50, 0);
        int oddsOfStraightFlush = Straight.oddsOfStraightFlush(allCards1, Rounds.FLOP);
        //assertEquals(0, oddsOfStraight);
        System.out.println("Odds of StraightFlush when both of Straight and Flush is greater than 0 and less than 100: "+oddsOfStraightFlush);


        //cant be StraightFlush, test case: only straight but odds of flush is zero
        Card[] allCards2 = new Card[]{
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Seven", "hearts", 7),
                new NumberCard("Eight", "clubs", 8),
                new NumberCard("King", "spades", 13),
                new NumberCard("Ace", "diamonds", 1, 14)};
        oddsOfStraightFlush = Straight.oddsOfStraightFlush(allCards2, Rounds.FLOP);
        assertEquals(0, oddsOfStraightFlush);
        System.out.println("Odds of StraightFlush when only straight but odds of flush is zero: "+oddsOfStraightFlush);

        //cant be StraightFlush, test case: Neither straight nor flush
        Card[] allCards3 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Seven", "spades", 7),
                new NumberCard("Nine", "clubs", 9),
                new NumberCard("King", "spades", 13)};
        oddsOfStraightFlush = Straight.oddsOfStraightFlush(allCards3, Rounds.FLOP);
        assertEquals(0, oddsOfStraightFlush);
        System.out.println("Odds of StraightFlush when neither straight nor flush: "+oddsOfStraightFlush);
    }
    @Test
    public void testFourOfAKind(){
        System.out.println("--------------------------FourOfAKind Flop Round--------------------------");

        //cant be FourOfAKind
        Card[] allCards1 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        HoldEmComputerPlayer FourOfAKind = new HoldEmComputerPlayer("Tom", 50, 0);
        FourOfAKind.sortCards(allCards1);
        int oddsOfFourOfAKind = FourOfAKind.oddsOfFourOfAKind(allCards1, Rounds.FLOP);
        assertEquals(0, oddsOfFourOfAKind);
        System.out.println("Odds of FourOfAKind: "+oddsOfFourOfAKind);

        //may be FourOfAKind
        Card[] allCards2 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        FourOfAKind.sortCards(allCards2);
        oddsOfFourOfAKind = FourOfAKind.oddsOfFourOfAKind(allCards2, Rounds.FLOP);
        assertEquals(2, oddsOfFourOfAKind);
        System.out.println("Odds of FourOfAKind: "+oddsOfFourOfAKind);

        //must be FourOfAKind
        Card[] allCards3 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3)};
        FourOfAKind.sortCards(allCards3);
        oddsOfFourOfAKind = FourOfAKind.oddsOfFourOfAKind(allCards3, Rounds.FLOP);
        assertEquals(100, oddsOfFourOfAKind);
        System.out.println("Odds of FourOfAKind: "+oddsOfFourOfAKind);

        System.out.println("--------------------------FourOfAKind Turn Round--------------------------");
        //cant be FourOfAKind
        Card[] allCards4 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Five", "hearts", 5),
                new NumberCard("Five", "hearts", 5),
                new NumberCard("Four", "hearts", 4)};
        FourOfAKind.sortCards(allCards4);
        oddsOfFourOfAKind = FourOfAKind.oddsOfFourOfAKind(allCards4, Rounds.TURN);
        assertEquals(0, oddsOfFourOfAKind);
        System.out.println("Odds of FourOfAKind: "+oddsOfFourOfAKind);

        //may be FourOfAKind
        Card[] allCards5 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Five", "hearts", 5),
                new NumberCard("Five", "hearts", 5)};
        FourOfAKind.sortCards(allCards5);
        oddsOfFourOfAKind = FourOfAKind.oddsOfFourOfAKind(allCards5, Rounds.TURN);
        assertEquals(2, oddsOfFourOfAKind);
        System.out.println("Odds of FourOfAKind: "+oddsOfFourOfAKind);

        //must be FourOfAKind
        Card[] allCards6 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Five", "hearts", 5)};
        FourOfAKind.sortCards(allCards6);
        oddsOfFourOfAKind = FourOfAKind.oddsOfFourOfAKind(allCards6, Rounds.TURN);
        assertEquals(100, oddsOfFourOfAKind);
        System.out.println("Odds of FourOfAKind: "+oddsOfFourOfAKind);
    }
    @Test
    public void testFullHouse(){
        System.out.println("--------------------------FullHouse Flop Round--------------------------");

        //cant be FullHouse
        Card[] allCards1 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        HoldEmComputerPlayer FullHouse = new HoldEmComputerPlayer("Tom", 50, 0);
        FullHouse.sortCards(allCards1);
        int oddsOfFullHouse = FullHouse.oddsOfFullHouse(allCards1, Rounds.FLOP);
        assertEquals(0, oddsOfFullHouse);
        System.out.println("Odds of FullHouse: "+oddsOfFullHouse);

        //may be FourOfAKind
        Card[] allCards2 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        FullHouse.sortCards(allCards2);
        oddsOfFullHouse = FullHouse.oddsOfFullHouse(allCards2, Rounds.FLOP);
        assertEquals(28, oddsOfFullHouse);
        System.out.println("Odds of FullHouse: "+oddsOfFullHouse);

        //must be FourOfAKind
        Card[] allCards3 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Ace", "hearts", 1, 14)};
        FullHouse.sortCards(allCards3);
        oddsOfFullHouse = FullHouse.oddsOfFullHouse(allCards3, Rounds.FLOP);
        assertEquals(100, oddsOfFullHouse);
        System.out.println("Odds of FullHouse: "+oddsOfFullHouse);

        System.out.println("--------------------------FullHouse Turn Round--------------------------");
        //cant be FourOfAKind
        Card[] allCards4 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Seven", "hearts", 7),
                new NumberCard("Five", "hearts", 5),
                new NumberCard("Four", "hearts", 4)};
        FullHouse.sortCards(allCards4);
        oddsOfFullHouse = FullHouse.oddsOfFullHouse(allCards4, Rounds.TURN);
        assertEquals(0, oddsOfFullHouse);
        System.out.println("Odds of FullHouse: "+oddsOfFullHouse);

        //may be FourOfAKind
        Card[] allCards5 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Five", "hearts", 5),
                new NumberCard("Seven", "hearts", 7)};
        FullHouse.sortCards(allCards5);
        oddsOfFullHouse = FullHouse.oddsOfFullHouse(allCards5, Rounds.TURN);
        assertEquals(17, oddsOfFullHouse);
        System.out.println("Odds of FullHouse: "+oddsOfFullHouse);

        //must be FourOfAKind
        Card[] allCards6 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Five", "hearts", 5),
                new NumberCard("Five", "hearts", 5)};
        FullHouse.sortCards(allCards6);
        oddsOfFullHouse = FullHouse.oddsOfFullHouse(allCards6, Rounds.TURN);
        assertEquals(100, oddsOfFullHouse);
        System.out.println("Odds of FullHouse: "+oddsOfFullHouse);
    }
    @Test
    public void testFlush() {
        System.out.println("--------------------------Flush Flop Round--------------------------");

        //cant be Flush, test case: count=2
        Card[] allCards1 = new Card[]{
                new NumberCard("Ace", "diamonds", 1, 14),
                new NumberCard("Deuce", "diamonds", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "spades", 5)};
        HoldEmComputerPlayer Flush = new HoldEmComputerPlayer("Tom", 50, 0);
        int oddsOfFlush = Flush.oddsOfFlush(allCards1, Rounds.FLOP);
        assertEquals(0, oddsOfFlush);
        System.out.println("Odds of Flush when count is 2: "+oddsOfFlush);

        //may be Flush, test case: count=3
        Card[] allCards2 = new Card[]{
                new NumberCard("Ace", "diamonds", 1, 14),
                new NumberCard("Deuce", "diamonds", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        oddsOfFlush = Flush.oddsOfFlush(allCards2, Rounds.FLOP);
        assertEquals(53, oddsOfFlush);
        System.out.println("Odds of Flush when count is 3: "+oddsOfFlush);

        //may be Flush, test case: count=4
        Card[] allCards3 = new Card[]{
                new NumberCard("Ace", "diamonds", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        oddsOfFlush = Flush.oddsOfFlush(allCards3, Rounds.FLOP);
        assertEquals(17, oddsOfFlush);
        System.out.println("Odds of Flush when count is 4: "+oddsOfFlush);

        //must be Flush, test case: count=5
        Card[] allCards4 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        oddsOfFlush = Flush.oddsOfFlush(allCards4, Rounds.FLOP);
        assertEquals(100, oddsOfFlush);
        System.out.println("Odds of Flush when count is 5: "+oddsOfFlush);

        System.out.println("--------------------------Flush Turn Round--------------------------");
        //ant be Flush, test case: count=3
        Card[] allCards5 = new Card[]{
                new NumberCard("Ace", "diamonds", 1, 14),
                new NumberCard("Deuce", "diamonds", 2),
                new NumberCard("Three", "diamonds", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5),
                new NumberCard("Three", "hearts", 3)};
        oddsOfFlush = Flush.oddsOfFlush(allCards5, Rounds.TURN);
        assertEquals(0, oddsOfFlush);
        System.out.println("Odds of Flush when count is 3: "+oddsOfFlush);

        //may be Flush, test case: count=4
        Card[] allCards6 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "diamonds", 5),
                new NumberCard("Three", "diamonds", 3)};
        oddsOfFlush = Flush.oddsOfFlush(allCards6, Rounds.TURN);
        assertEquals(17, oddsOfFlush);
        System.out.println("Odds of Flush when count is 4: "+oddsOfFlush);

        //may be Flush, test case: count>=5
        Card[] allCards7 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5),
                new NumberCard("Six", "hearts", 6)};
        oddsOfFlush = Flush.oddsOfFlush(allCards7, Rounds.TURN);
        assertEquals(100, oddsOfFlush);
        System.out.println("Odds of Flush when count is greater than or equal to 5: "+oddsOfFlush);
    }
    @Test
    public void testStraight() {
        System.out.println("--------------------------Straight Flop Round--------------------------");

        //may be Straight, test case: Ace is at end
        Card[] allCards1 = new Card[]{
                new NumberCard("Seven", "diamonds", 7),
                new NumberCard("Nine", "diamonds", 9),
                new NumberCard("Ten", "hearts", 10),
                new NumberCard("Jack", "hearts", 11),
                new NumberCard("Ace", "spades", 1, 14)};
        HoldEmComputerPlayer Straight = new HoldEmComputerPlayer("Tom", 50, 0);
        int oddsOfStraight = Straight.oddsOfStraight(allCards1, Rounds.FLOP);
        //assertEquals(0, oddsOfStraight);
        System.out.println("Odds of Straight when Ace is at end: "+oddsOfStraight);

        //cant be Straight, test case: Ace is at end
        Card[] allCards2 = new Card[]{
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Seven", "hearts", 7),
                new NumberCard("Eight", "hearts", 8),
                new NumberCard("King", "hearts", 13),
                new NumberCard("Ace", "diamonds", 1, 14)};
        oddsOfStraight = Straight.oddsOfStraight(allCards2, Rounds.FLOP);
        assertEquals(0, oddsOfStraight);
        System.out.println("Odds of Straight when Ace is at end: "+oddsOfStraight);



        //may be Straight, test case: Ace is at first
        Card[] allCards3 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Six", "hearts", 6),
                new NumberCard("Seven", "hearts", 7)};
        oddsOfStraight = Straight.oddsOfStraight(allCards3, Rounds.FLOP);
//        assertEquals(100, oddsOfStraight);
        System.out.println("Odds of Straight when Ace is at first: "+oddsOfStraight);

        //cant be Straight, test case: Ace is at first
        Card[] allCards4 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Seven", "hearts", 7),
                new NumberCard("Nine", "hearts", 9),
                new NumberCard("King", "hearts", 13)};
        oddsOfStraight = Straight.oddsOfStraight(allCards4, Rounds.FLOP);
        assertEquals(0, oddsOfStraight);
        System.out.println("Odds of Straight when Ace is at first: "+oddsOfStraight);

        System.out.println("--------------------------Straight Turn Round(Edge case)--------------------------");
        //may be Straight, test case: J, Q, K, A, 2, 3
        Card[] allCards5 = new Card[]{
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Five", "hearts", 5),
                new NumberCard("Eight", "hearts", 8),
                new NumberCard("Ten", "hearts", 10),
                new NumberCard("Ace", "hearts", 1, 14)};
        Straight.sortCards(allCards5);
        oddsOfStraight = Straight.oddsOfStraight(allCards5, Rounds.TURN);
        assertEquals(0, oddsOfStraight);
        System.out.println("Odds of Straight when test case is J, Q, K, A, 2, 3: "+oddsOfStraight);

        //must be Straight, test case: K, A, 2, 3, 4, 5
        Card[] allCards10 = new Card[]{
                new NumberCard("King", "hearts", 13),
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        Straight.sortCards(allCards10);
        oddsOfStraight = Straight.oddsOfStraight(allCards10, Rounds.TURN);
        assertEquals(100, oddsOfStraight);
        System.out.println("Odds of Straight when test case is K, A, 2, 3, 4, 5: "+oddsOfStraight);

        System.out.println("--------------------------Straight Turn Round--------------------------");

        //may be Straight, test case: Ace is at end
        Card[] allCards6 = new Card[]{
                new NumberCard("Seven", "hearts", 7),
                new NumberCard("Nine", "hearts", 9),
                new NumberCard("Ten", "hearts", 10),
                new NumberCard("Jack", "hearts", 11),
                new NumberCard("King", "hearts", 13),
                new NumberCard("Ace", "hearts", 1, 14)};
        Straight.sortCards(allCards6);
        oddsOfStraight = Straight.oddsOfStraight(allCards6, Rounds.TURN);
//        assertEquals(100, oddsOfStraight);
        System.out.println("Odds of Straight when Ace is at end: "+oddsOfStraight);

        //cant be Straight, test case: Ace is at end
        Card[] allCards7 = new Card[]{
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Five", "hearts", 5),
                new NumberCard("Eight", "hearts", 8),
                new NumberCard("Nine", "hearts", 9),
                new NumberCard("King", "hearts", 13),
                new NumberCard("Ace", "hearts", 1, 14)};
        Straight.sortCards(allCards7);
        oddsOfStraight = Straight.oddsOfStraight(allCards7, Rounds.TURN);
        assertEquals(0, oddsOfStraight);
        System.out.println("Odds of Straight when Ace is at end: "+oddsOfStraight);



        //may be Straight, test case: Ace is at first
        Card[] allCards8 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Six", "hearts", 6),
                new NumberCard("Seven", "hearts", 7),
                new NumberCard("Nine", "hearts", 9)};
        oddsOfStraight = Straight.oddsOfStraight(allCards8, Rounds.TURN);
//        assertEquals(100, oddsOfStraight);
        System.out.println("Odds of Straight when Ace is at first: "+oddsOfStraight);

        //may be Straight, test case: Ace is at first
        Card[] allCards9 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Seven", "hearts", 7),
                new NumberCard("Nine", "hearts", 9),
                new NumberCard("Queen", "hearts", 12),
                new NumberCard("King", "hearts", 13)};
        oddsOfStraight = Straight.oddsOfStraight(allCards9, Rounds.TURN);
        assertEquals(0, oddsOfStraight);
        System.out.println("Odds of Straight when Ace is at first: "+oddsOfStraight);
    }
    @Test
    public void testThreeOfAKind(){
        System.out.println("--------------------------ThreeOfAKind Flop Round--------------------------");

        //cant be ThreeOfAKind
        Card[] allCards1 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        HoldEmComputerPlayer ThreeOfAKind = new HoldEmComputerPlayer("Tom", 50, 0);
        ThreeOfAKind.sortCards(allCards1);
        int oddsOfThreeOfAKind = ThreeOfAKind.oddsOfThreeOfAKind(allCards1, Rounds.FLOP);
        assertEquals(23, oddsOfThreeOfAKind);
        System.out.println("Odds of ThreeOfAKind: "+oddsOfThreeOfAKind);

        //may be ThreeOfAKind
        Card[] allCards2 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        ThreeOfAKind.sortCards(allCards2);
        oddsOfThreeOfAKind = ThreeOfAKind.oddsOfThreeOfAKind(allCards2, Rounds.FLOP);
        assertEquals(4, oddsOfThreeOfAKind);
        System.out.println("Odds of ThreeOfAKind: "+oddsOfThreeOfAKind);

        //must be ThreeOfAKind
        Card[] allCards3 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4)};
        ThreeOfAKind.sortCards(allCards3);
        oddsOfThreeOfAKind = ThreeOfAKind.oddsOfThreeOfAKind(allCards3, Rounds.FLOP);
        assertEquals(100, oddsOfThreeOfAKind);
        System.out.println("Odds of ThreeOfAKind: "+oddsOfThreeOfAKind);

        System.out.println("--------------------------ThreeOfAKind Turn Round--------------------------");
        //cant be ThreeOfAKind
        Card[] allCards4 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5),
                new NumberCard("Six", "hearts", 6),
                new NumberCard("Seven", "hearts", 7)};
        ThreeOfAKind.sortCards(allCards4);
        oddsOfThreeOfAKind = ThreeOfAKind.oddsOfThreeOfAKind(allCards4, Rounds.TURN);
        assertEquals(0, oddsOfThreeOfAKind);
        System.out.println("Odds of ThreeOfAKind: "+oddsOfThreeOfAKind);

        //may be ThreeOfAKind
        Card[] allCards5 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5),
                new NumberCard("Six", "hearts", 6)};
        ThreeOfAKind.sortCards(allCards5);
        oddsOfThreeOfAKind = ThreeOfAKind.oddsOfThreeOfAKind(allCards5, Rounds.TURN);
        assertEquals(4, oddsOfThreeOfAKind);
        System.out.println("Odds of ThreeOfAKind: "+oddsOfThreeOfAKind);

        //must be ThreeOfAKind
        Card[] allCards6 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        ThreeOfAKind.sortCards(allCards6);
        oddsOfThreeOfAKind = ThreeOfAKind.oddsOfThreeOfAKind(allCards6, Rounds.TURN);
        assertEquals(100, oddsOfThreeOfAKind);
        System.out.println("Odds of ThreeOfAKind: "+oddsOfThreeOfAKind);
    }
    @Test
    public void testTwoPair(){
        System.out.println("--------------------------TwoPair Flop Round--------------------------");

        //cant be TwoPair
        Card[] allCards1 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3)};
        HoldEmComputerPlayer TwoPair = new HoldEmComputerPlayer("Tom", 50, 0);
        TwoPair.sortCards(allCards1);
        int oddsOfTwoPair = TwoPair.oddsOfTwoPair(allCards1, Rounds.FLOP);
        assertEquals(0, oddsOfTwoPair);
        System.out.println("Odds of TwoPair: "+oddsOfTwoPair);

        //may be TwoPair
        Card[] allCards2 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        TwoPair = new HoldEmComputerPlayer("Tom", 50, 0);
        TwoPair.sortCards(allCards2);
        oddsOfTwoPair = TwoPair.oddsOfTwoPair(allCards2, Rounds.FLOP);
        assertEquals(23, oddsOfTwoPair);
        System.out.println("Odds of TwoPair: "+oddsOfTwoPair);

        //may be TwoPair
        Card[] allCards3 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        TwoPair = new HoldEmComputerPlayer("Tom", 50, 0);
        TwoPair.sortCards(allCards3);
        oddsOfTwoPair = TwoPair.oddsOfTwoPair(allCards3, Rounds.FLOP);
        assertEquals(17, oddsOfTwoPair);
        System.out.println("Odds of TwoPair: "+oddsOfTwoPair);

        //may be TwoPair
        Card[] allCards4 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Four", "hearts", 4)};
        TwoPair = new HoldEmComputerPlayer("Tom", 50, 0);
        TwoPair.sortCards(allCards4);
        oddsOfTwoPair = TwoPair.oddsOfTwoPair(allCards4, Rounds.FLOP);
        assertEquals(100, oddsOfTwoPair);
        System.out.println("Odds of TwoPair: "+oddsOfTwoPair);

        System.out.println("--------------------------TwoPair Turn Round--------------------------");
        //may be TwoPair
        Card[] allCards5 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Four", "hearts", 4)};
        TwoPair = new HoldEmComputerPlayer("Tom", 50, 0);
        TwoPair.sortCards(allCards5);
        oddsOfTwoPair = TwoPair.oddsOfTwoPair(allCards5, Rounds.TURN);
        assertEquals(100, oddsOfTwoPair);
        System.out.println("Odds of TwoPair: "+oddsOfTwoPair);
        //must be TwoPair
        Card[] allCards6 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Four", "hearts", 4)};
        TwoPair = new HoldEmComputerPlayer("Tom", 50, 0);
        TwoPair.sortCards(allCards6);
        oddsOfTwoPair = TwoPair.oddsOfTwoPair(allCards6, Rounds.TURN);
        assertEquals(100, oddsOfTwoPair);
        System.out.println("Odds of TwoPair: "+oddsOfTwoPair);
    }
    @Test
    public void testPair(){
        System.out.println("--------------------------Pair Flop Round--------------------------");

        //cant be TwoPair
        Card[] allCards1 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Three", "hearts", 3)};
        HoldEmComputerPlayer Pair = new HoldEmComputerPlayer("Tom", 50, 0);
        Pair.sortCards(allCards1);
        int oddsOfPair = Pair.oddsOfPair(allCards1, Rounds.FLOP);
        assertEquals(0, oddsOfPair);
        System.out.println("Odds of Pair: "+oddsOfPair);

        //may be TwoPair
        Card[] allCards2 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        Pair.sortCards(allCards2);
        oddsOfPair = Pair.oddsOfPair(allCards2, Rounds.FLOP);
        assertEquals(23, oddsOfPair);
        System.out.println("Odds of Pair: "+oddsOfPair);

        //must be TwoPair
        Card[] allCards3 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5)};
        Pair.sortCards(allCards3);
        oddsOfPair = Pair.oddsOfPair(allCards3, Rounds.FLOP);
        assertEquals(100, oddsOfPair);
        System.out.println("Odds of Pair: "+oddsOfPair);

        System.out.println("--------------------------Pair Turn Round--------------------------");
        //cant be TwoPair
        Card[] allCards4 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Five", "hearts", 5),
                new NumberCard("Five", "hearts", 5)};
        Pair.sortCards(allCards4);
        oddsOfPair = Pair.oddsOfPair(allCards4, Rounds.TURN);
        assertEquals(0, oddsOfPair);
        System.out.println("Odds of Pair: "+oddsOfPair);

        //may be TwoPair
        Card[] allCards5 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Three", "hearts", 3),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5),
                new NumberCard("Six", "hearts", 6)};
        Pair.sortCards(allCards5);
        oddsOfPair = Pair.oddsOfPair(allCards5, Rounds.TURN);
        assertEquals(26, oddsOfPair);
        System.out.println("Odds of Pair: "+oddsOfPair);

        //must be TwoPair
        Card[] allCards6 = new Card[]{
                new NumberCard("Ace", "hearts", 1, 14),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Deuce", "hearts", 2),
                new NumberCard("Four", "hearts", 4),
                new NumberCard("Five", "hearts", 5),
                new NumberCard("Six", "hearts", 6)};
        Pair.sortCards(allCards6);
        oddsOfPair = Pair.oddsOfPair(allCards6, Rounds.TURN);
        assertEquals(100, oddsOfPair);
        System.out.println("Odds of Pair: "+oddsOfPair);
    }

}
