import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.System.in;

public class Ex29_HandlingBadInput {

    private static final Pattern isNumericPattern = Pattern.compile("^\\d*$");

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);

        String returnRate;
        int years = 0;

        do {
            System.out.print("What is the rate of return? ");
            returnRate = sc.nextLine();
            if(!isNumericPattern.matcher(returnRate).matches()) {
                System.out.println("Sorry. That's not a valid input.");
            } else if(returnRate.equals("0")) {
                System.out.println("Sorry. Dividing by 0 will make the world explode.");
            } else {
                years = 72 / Integer.parseInt(returnRate);
            }
        } while (!isNumericPattern.matcher(returnRate).matches() || returnRate.equals("0"));

        System.out.println("It will take " + years + " years to double your initial investment.");

        sc.close();
    }
}