package Ex32;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.System.in;

public class Ex32_GuessTheNumberGame {

    private static final Pattern isNumericPattern = Pattern.compile("^\\d*$");

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);

        String playAgain;

        do {

            System.out.print("Let's play Guess the Number.\nPick a difficulty level (1, 2, or 3): ");
            String difficultyLevel = sc.nextLine();

            while (!difficultyLevel.equals("1") && !difficultyLevel.equals("2") && !difficultyLevel.equals("3")) {
                System.out.print("There are only 3 levels.\nPlease specify which level you would like to play: ");
                difficultyLevel = sc.nextLine();
            }

            int numberToGuess = switch (difficultyLevel) {
                case "1" -> new Random().nextInt(10) + 1;
                case "2" -> new Random().nextInt(100) + 1;
                case "3" -> new Random().nextInt(1000) + 1;
                default -> 0;
            };

            System.out.print("I have my number. What's your guess? ");
            String guess = sc.nextLine();

            int countGuesses = 0;
            List<String> userGuesses = new ArrayList<>();

            while (!isNumericPattern.matcher(guess).matches() || Integer.parseInt(guess) != numberToGuess) {
                while(userGuesses.contains(guess)) {
                    System.out.print("You already guessed " + guess + ".\nGuess again: ");
                    guess = sc.nextLine();
                    countGuesses++;
                }
                userGuesses.add(guess);
                String message;
                if (!isNumericPattern.matcher(guess).matches()) {
                    message = "Guess is non-numeric. ";
                } else {
                    int guessInt = Integer.parseInt(guess);
                    if (guessInt < numberToGuess) {
                        message = "Too low. ";
                    } else {
                        message = "Too high. ";
                    }
                }

                System.out.print(message + "Guess again: ");
                guess = sc.nextLine();
                countGuesses++;
            }

            System.out.println("You got it in " + countGuesses + " guesses!");
            if (countGuesses == 1) {
                System.out.println("You are a mind reader!");
            } else if (countGuesses >= 2 && countGuesses <= 4) {
                System.out.println("Most impressive.");
            } else if (countGuesses >= 5 && countGuesses <= 6) {
                System.out.println("You can do better than that.");
            } else if (countGuesses >= 7) {
                System.out.println("Better luck next time.");
            }

            System.out.print("Play again? ");
            playAgain = sc.nextLine();
        } while(!playAgain.equals("n"));

        System.out.println("Goodbye!");

        sc.close();
    }
}