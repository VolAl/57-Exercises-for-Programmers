package Ex19;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex19_BmiCalculator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);
        BigDecimal lowerEndBmiRange = BigDecimal.valueOf(18.5);
        BigDecimal higherEndBmiRange = BigDecimal.valueOf(25);

        try {
            System.out.print("What is your height in inches? ");
            BigDecimal height = new BigDecimal(sc.next());
            System.out.print("What is your weight in pounds? ");
            BigDecimal weight = new BigDecimal(sc.next());

            BigDecimal bmi = (weight.divide((height.multiply(height)), RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(703));

            System.out.println( "Your BMI is " + bmi.setScale(2, RoundingMode.HALF_UP) + ".\nYou are " +
                                (bmi.compareTo(lowerEndBmiRange) >= 0 && bmi.compareTo(higherEndBmiRange) <= 0 ? "within the ideal weight range." :
                                        (bmi.compareTo(lowerEndBmiRange) < 0 ? "underweight" :
                                                ( bmi.compareTo(higherEndBmiRange) > 0 ? "overweight" : ""))
                                        + ". You should see your doctor."));

        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }

        sc.close();
    }

}