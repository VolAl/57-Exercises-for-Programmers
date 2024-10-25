package Ex2;

import java.text.MessageFormat;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex2_CountingTheNumberOfCharacters {
    public static void main(String[] args) {
        try {
            String name = askInputString();
            while(name.isEmpty()) {
                System.out.println("You must give me an input! Retry.");
                name = askInputString();
            }
            System.out.println(MessageFormat.format("{0} has {1} characters",
                    name, name.length()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String askInputString() {
        System.out.print("What is the input string? ");
        return new Scanner(in).nextLine();
    }
}