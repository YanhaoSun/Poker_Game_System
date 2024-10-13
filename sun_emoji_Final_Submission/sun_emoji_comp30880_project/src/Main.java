import blackjack.GameOfBlackJack;
import poker.GameOfPoker;
import texas.hold_em.HoldEmController;
import texas.scramble.controller.ScrambleController;

public class Main {
    public static void main(String[] args) {
        byte[] input = new byte[100];

        System.out.println("Welcome to the Automated Card Game Machine ....");

        while (true) {
            try {
                System.out.print("\n>> Pick an option: 1. Poker  2. Blackjack  3. Texas Hold'em  4. Texas Scramble  5. Quit  ");

                System.in.read(input);

                for (byte b : input) {
                    switch ((char) b) {
                        case '\0', '\n' -> {}
                        case '1' -> GameOfPoker.startGame();
                        case '2' -> GameOfBlackJack.startGame();
                        case '3' -> HoldEmController.startGame();
                        case '4' -> ScrambleController.startGame();
                        case '5' -> {
                            return;
                        }
                        default -> System.out.println("Invalid option.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
