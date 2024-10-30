import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex12_ComputingSimpleInterest {

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);

        try {

            System.out.print("Enter the principal: ");
            BigDecimal principal = sc.nextBigDecimal();
            System.out.print("Enter the rate of interest: ");
            BigDecimal interestRate = new BigDecimal(sc.next());
            System.out.print("Enter the number of years: ");
            int years = sc.nextInt();

            BigDecimal interestRatePercentage = interestRate.divide(new BigDecimal("100.00"), 3, RoundingMode.HALF_UP);

            for(int year=1; year<=years; year++) {
                BigDecimal interest = calculateSimpleInterest(interestRatePercentage, principal, year);
                System.out.println(MessageFormat.format("After {0} year{1} at {2}%, the investment will be worth ${3}.",
                        year, (year > 1 ? "s" : ""), interestRate.toString(), interest.setScale(0, RoundingMode.UP).toString()));
            }

        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }

        sc.close();
    }

    private static BigDecimal calculateSimpleInterest(BigDecimal rate, BigDecimal principal, int years) {
        return principal.multiply((BigDecimal.valueOf(1).add((rate.multiply(BigDecimal.valueOf(years))))));
    }
}