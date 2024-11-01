import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex14_TaxCalculator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(in);

        try {
            System.out.print("What is the order amount? ");
            BigDecimal orderAmount = sc.nextBigDecimal();
            System.out.print("What is the state? ");
            String state = sc.next();

            BigDecimal orderTax = BigDecimal.valueOf(-1);
            if(state.equalsIgnoreCase("WI") || state.equalsIgnoreCase("Wisconsin") ) {
                BigDecimal taxPercentage = BigDecimal.valueOf(5.5);
                orderTax = orderAmount.multiply(taxPercentage).divide(BigDecimal.valueOf(100.00), 2, RoundingMode.FLOOR);                orderAmount = orderAmount.add(orderTax);
            }

            System.out.println( (orderTax.signum() > 0 ? "The tax is $" + orderTax + ".\n" : "") + "The total is $" + orderAmount + ".");

        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }

        sc.close();
    }
}