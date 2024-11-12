import java.text.MessageFormat;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex26_MonthsToPayOffCreditCard {

    private static final Scanner sc = new Scanner(in);

    public static void main(String[] args) {

        System.out.print("Would you like to figure out the number of months until payoff or " +
                         "the amount needed to pay per month? (Type 1 or 2) - ");
        switch (sc.nextLine()) {
            case "1":
                monthsToPayOffCreditCardV1();
                break;
            case "2":
                monthsToPayOffCreditCardV2();
                break;
            default:
                System.out.println("Invalid version. Exit.");
        }
        sc.close();
    }

    private static void monthsToPayOffCreditCardV1() {
        try {

            System.out.print("What is your balance? ");
            double balance = sc.nextDouble();
            System.out.print("What is the APR on the card (as a percent)? ");
            double aprPercentage = sc.nextDouble();
            System.out.print("What the monthly payment you can make? ");
            double monthlyPayment = sc.nextDouble();

            double monthsToPayOff = Math.ceil(calculateMonthsUntilPaidOff(balance, aprPercentage, monthlyPayment));

            System.out.println(MessageFormat.format("It will take you {0} months to pay off this card.", monthsToPayOff));
        } catch (Exception e) {
            System.out.println("Cannot proceed, value is not numeric. Exit.");
        }
    }

    private static double calculateMonthsUntilPaidOff(double balance, double aprPercentage, double monthlyPayment) {
        double dailyRate = (aprPercentage/100)/365;
        return ((double) -1 / 30) * ((Math.log(1 + balance / monthlyPayment * (1 - Math.pow(1 + dailyRate, 30)))) / (Math.log(1 + dailyRate)));
    }


    private static void monthsToPayOffCreditCardV2() {
        try {

            System.out.print("What is your balance? ");
            double balance = sc.nextDouble();
            System.out.print("What is the APR on the card (as a percent)? ");
            double aprPercentage = sc.nextDouble();
            System.out.print("What the number of months until payoff? ");
            double monthsUntilPayoff = sc.nextDouble();

            double monthlyPayment = Math.ceil(calculateMonthlyPayment(balance, aprPercentage, monthsUntilPayoff));

            System.out.println(MessageFormat.format("It will take you a monthly payment of ${0} to pay off this card in {1} months.",
                    monthlyPayment,
                    monthsUntilPayoff));
        } catch (Exception e) {
            System.out.println("Cannot proceed, value is not numeric. Exit.");
        }
    }

    private static double calculateMonthlyPayment(double balance, double aprPercentage, double monthsUntilPayoff) {
        double dailyRate = (aprPercentage/100)/365;
        return balance * (1 -  Math.pow(1 + dailyRate, 30)) / (Math.pow(2.71828, -30 * monthsUntilPayoff * Math.log(1 + dailyRate)) - 1) ;
    }


}