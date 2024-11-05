import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex20_MultistateSalesTaxCalculator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(in);

        try {
            System.out.print("What is the order amount? ");
            BigDecimal orderAmount = sc.nextBigDecimal();
            // Consume the newline character
            sc.nextLine();
            System.out.print("What state do you live in? ");
            String state = sc.nextLine();

            BigDecimal orderTax = BigDecimal.valueOf(-1);
            if(state.equalsIgnoreCase("WI") || state.equalsIgnoreCase("Wisconsin") ) {
                BigDecimal taxPercentage = BigDecimal.valueOf(5.5);
                System.out.print("In which county do you live in? ");
                String county = sc.nextLine().trim();
                if(county.equalsIgnoreCase("Eau Claire")){
                    taxPercentage = taxPercentage.add(BigDecimal.valueOf(5.0));
                } else if(county.equalsIgnoreCase("Dunn")){
                    taxPercentage = taxPercentage.add(BigDecimal.valueOf(4.0));
                }

                orderTax = orderAmount.multiply(taxPercentage).divide(BigDecimal.valueOf(100.00), 2, RoundingMode.FLOOR);                orderAmount = orderAmount.add(orderTax);
            } else if(state.equalsIgnoreCase("IL") || state.equalsIgnoreCase("Illinois") ) {
                BigDecimal taxPercentage = BigDecimal.valueOf(8.0);

                orderTax = orderAmount.multiply(taxPercentage).divide(BigDecimal.valueOf(100.00), 2, RoundingMode.FLOOR);                orderAmount = orderAmount.add(orderTax);
            }

            System.out.println( (orderTax.signum() > 0 ? "The tax is $" + orderTax + ".\n" : "") + "The total is $" + orderAmount + ".");

        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }

        sc.close();
    }
}