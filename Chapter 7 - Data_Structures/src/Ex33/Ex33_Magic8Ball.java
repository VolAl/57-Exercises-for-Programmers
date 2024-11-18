package Ex33;

import java.util.Random;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex33_Magic8Ball {
    public static void main(String[] args) {

        Scanner sc = new Scanner(in);

        String newQuestion;
        String[] possibleAnswers = new String[4];
        possibleAnswers[0] = "Yes";
        possibleAnswers[1] = "No";
        possibleAnswers[2] = "Maybe";
        possibleAnswers[3] = "Ask again later";

        do {
            System.out.print("What's your question? ");
            sc.nextLine();
            System.out.println(possibleAnswers[new Random().nextInt(0, 3)]);
            System.out.print("Do you have another question (y/n)? ");
            newQuestion = sc.nextLine();
        } while(!newQuestion.equalsIgnoreCase("n"));
    }
}