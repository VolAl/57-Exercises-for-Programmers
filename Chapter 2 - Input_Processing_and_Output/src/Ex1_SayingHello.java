import java.text.MessageFormat;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex1_SayingHello {
    public static void main(String[] args) {
        try {
            System.out.print("What's your name? ");
            System.out.println(MessageFormat.format("Hello, {0}, nice to meet you!",
                    new Scanner(in).nextLine()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}