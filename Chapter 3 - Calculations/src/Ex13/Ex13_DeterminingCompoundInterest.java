package Ex13;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex13_DeterminingCompoundInterest {

    private static final Scanner sc = new Scanner(in);

    public static void main(String[] args) {

        System.out.print("""
                Would you like to:
                (1) know the compound interest of your initial amount
                or
                (2) set a specific goal and determine the initial amount you'd need to invest?
                (Type 1 or 2) -\s""");
        switch (sc.nextLine()) {
            case "1":
                determiningCompoundInterestV1();
                break;
            case "2":
                determiningCompoundInterestV2();
                break;
            default:
                System.out.println("Invalid version. Exit.");
        }

        sc.close();
    }

    private static void determiningCompoundInterestV1() {
        try {
            System.out.print("What is the principal amount? ");
            double principal = Double.parseDouble(sc.next());
            System.out.print("What is the rate? ");
            double interestRate = Double.parseDouble(sc.next());
            System.out.print("What is the number of years? ");
            int years = sc.nextInt();
            System.out.print("What is the number of times the interest is compounded per year? ");
            int timesInterestYearlyCompounded = sc.nextInt();

            double interestRatePercentage = interestRate / 100.00;

            double interest = determineCompoundInterest(interestRatePercentage, principal, years, timesInterestYearlyCompounded);
            System.out.println(MessageFormat.format("${0} invested at {1}% for {2} years compounded {3} times per year is ${4}.",
                    BigDecimal.valueOf(principal).setScale(2, RoundingMode.HALF_UP).toString(),
                    Double.toString(interestRate),
                    years,
                    timesInterestYearlyCompounded,
                    BigDecimal.valueOf(interest).setScale(2, RoundingMode.HALF_UP).toString()));

        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }

    }

    private static void determiningCompoundInterestV2() {
        try {
            System.out.print("What is the amount at the end of the investment? ");
            double amount = Double.parseDouble(sc.next());
            System.out.print("What is the rate? ");
            double interestRate = Double.parseDouble(sc.next());
            System.out.print("What is the number of years? ");
            int years = sc.nextInt();
            System.out.print("What is the number of times the interest is compounded per year? ");
            int timesInterestYearlyCompounded = sc.nextInt();

            double interestRatePercentage = interestRate / 100.00;

            double initialAmount = determineInitialAmount(interestRatePercentage, amount, years, timesInterestYearlyCompounded);
            System.out.println(MessageFormat.format("In order to reach the goal of ${0} at {1}% for {2} years compounded {3} times per year, your initial investment must be ${4}.",
                    BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP).toString(),
                    Double.toString(interestRate),
                    years,
                    timesInterestYearlyCompounded,
                    BigDecimal.valueOf(initialAmount).setScale(2, RoundingMode.HALF_UP).toString()));

        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }
    }

    private static double determineCompoundInterest(double rate, double principal, int years, int timesInterestYearlyCompounded) {
        return principal * (Math.pow( (1 + (rate / timesInterestYearlyCompounded)), (timesInterestYearlyCompounded*years)));
    }

    private static double determineInitialAmount(double rate, double amount, int years, int timesInterestYearlyCompounded) {
        return amount / (Math.pow( (1 + (rate / timesInterestYearlyCompounded)), (timesInterestYearlyCompounded*years)));
    }
}