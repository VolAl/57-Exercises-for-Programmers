import java.util.*;

import static java.lang.System.in;

public class Ex3_PrintingQuotes {
    public static void main(String[] args) {
        System.out.print("Which version of \"Printing Quotes\" would you like to run? (Type 1 or 2) - ");
        switch (new Scanner(in).nextLine()) {
            case "1":
                printingQuotesV1();
                break;
            case "2":
                printingQuotesV2();
                break;
            default:
                System.out.println("Invalid version. Exit.");
        }
    }

    private static void printingQuotesV1() {
        System.out.print("What is the quote? ");
        String quote = new Scanner(in).nextLine();
        System.out.print("WHo said it? ");
        String author = new Scanner(in).nextLine();
        System.out.println(author + " says, \"" + quote + "\"");
    }

    private static void printingQuotesV2() {
        Map<String,String> quotesMap = Map.of(
                "Obi-Wan Kenobi", "Your eyes can deceive you. Don’t trust them.",
                "Darth Vader", "I find your lack of faith disturbing.",
                "Yoda", "Do. Or do not. There is no try",
                "C-3PO", "Don’t call me a mindless philosopher, you overweight glob of grease!"
        );

        for (Map.Entry<String, String> entry : quotesMap.entrySet()) {
            System.out.println(entry.getKey() + " says, \"" + entry.getValue() + "\"");
        }
    }
}