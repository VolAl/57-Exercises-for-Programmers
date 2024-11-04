package Ex17;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex17_BloodAlcoholCalculator {

    private static final Map<String,BigDecimal> LEGAL_BAC_FOR_DRIVING_PER_COUNTRY = Map.of(
            "Default", BigDecimal.valueOf(0.08),
            "China", BigDecimal.valueOf(0.02),
            "France", BigDecimal.valueOf(0.05),
            "Japan", BigDecimal.valueOf(0.03)
    );

    public static void main(String[] args) {
        Scanner sc = new Scanner(in);

        try {
            System.out.print("Would you like to use metric or imperial units? (M/I): ");
            String units = sc.next();
            while(!units.equalsIgnoreCase("M") && !units.equalsIgnoreCase(("I"))) {
                System.out.println("Sorry, you must choose between M or I to continue. Retry.");
                units = sc.next();
            }

            System.out.print("In which State/Country are you? ");
            String country = sc.next();
            BigDecimal legalBacForDriving = LEGAL_BAC_FOR_DRIVING_PER_COUNTRY.containsKey(country) ?
                    LEGAL_BAC_FOR_DRIVING_PER_COUNTRY.get(country) : LEGAL_BAC_FOR_DRIVING_PER_COUNTRY.get("Default");
            System.out.print("How much do you weigh? ");
            BigDecimal weight = sc.nextBigDecimal();
            System.out.print("What is your gender? (M/F): ");
            String gender = sc.next();
            while(!gender.equalsIgnoreCase("M") && !gender.equalsIgnoreCase(("F"))) {
                System.out.println("Sorry, you must choose between M or F to continue. Retry.");
                gender = sc.next();
            }
            System.out.print("How many drinks did you have? ");
            int drinksNumber = sc.nextInt();
            System.out.print("What is the amount of alcohol by volume of the drinks consumed? ");
            BigDecimal drinksVolume = sc.nextBigDecimal();
            System.out.print("How many hours have passed since the last drink? ");
            BigDecimal hoursSinceLastDrink = sc.nextBigDecimal();

            BigDecimal totalAlcoholConsumed = drinksVolume.multiply(BigDecimal.valueOf(drinksNumber));
            BigDecimal alcoholDistributionRatio = gender.equals("M") ? BigDecimal.valueOf(0.73) : BigDecimal.valueOf(0.66);

            if(units.equalsIgnoreCase("M")) {
                weight = weight.divide(BigDecimal.valueOf(2.2046), RoundingMode.UP);

                totalAlcoholConsumed = totalAlcoholConsumed.multiply(BigDecimal.valueOf(0.0338140227));
            }

            BigDecimal bac = (totalAlcoholConsumed.multiply(BigDecimal.valueOf(5.14))
                    .divide(weight.multiply(alcoholDistributionRatio), RoundingMode.UP))
                    .subtract((BigDecimal.valueOf(0.015).multiply(hoursSinceLastDrink)));

            System.out.println(MessageFormat.format("Yor BAC is {0}\nIt is {1}legal for you to drive.",
                    bac, (bac.compareTo(legalBacForDriving) > 0 ? "not " : "")));

        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }

        sc.close();
    }
}