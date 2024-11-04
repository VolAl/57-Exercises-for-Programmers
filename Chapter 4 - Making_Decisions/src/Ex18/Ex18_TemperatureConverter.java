package Ex18;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex18_TemperatureConverter {

    private static final Map<Character,String> scales = Map.of(
            'F', "Fahrenheit",
            'C', "Celsius",
            'K', "Kelvin");

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);

        System.out.print("""
                Press FC to convert Fahrenheit to Celsius.
                Press CF to convert Celsius to Fahrenheit.
                Press FK to convert Fahrenheit to Kelvin.
                Press KF to convert Kelvin to Fahrenheit.
                Press CK to convert Celsius to Kelvin.
                Press KC to convert Kelvin to Celsius.
                Your choice:\s""");

        String conversionInput = sc.next().toUpperCase();

        while(!conversionInput.equals("FC") && !conversionInput.equals("CF")
              && !conversionInput.equals("FK") && !conversionInput.equals("KF")
              && !conversionInput.equals("CK") && !conversionInput.equals("KC")) {
            System.out.println("You must choose one of the required scales. Retry.");
            conversionInput = sc.next().toUpperCase();
        }

        try {
            System.out.print(MessageFormat.format("Please enter the temperature in {0}: ",
                    scales.get(conversionInput.charAt(0))));
            double temperature = Double.parseDouble(sc.next());

            convertTemperatureToGivenScale(temperature, conversionInput);

        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }

        sc.close();
    }

    private static void convertTemperatureToGivenScale(double temperature, String conversionInput) {
        double result = switch (conversionInput) {
            case "FC" -> (temperature - 32) * 5 / 9;
            case "CF" -> (temperature * 9 / 5) + 32;
            case "FK" -> ((temperature - 32) / 1.8) + 273.15;
            case "KF" -> ((temperature - 273.15) * 1.8) + 32;
            case "CK" -> temperature + 273.15;
            case "KC" -> temperature - 273.15;
            default -> 0;
        };

        System.out.println(MessageFormat.format("The temperature in {0} is {1}.",
                scales.get(conversionInput.charAt(1)), result));
    }

}