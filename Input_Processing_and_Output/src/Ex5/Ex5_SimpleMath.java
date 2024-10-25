package Ex5;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex5_SimpleMath {
    private static final String FIRST = "first";
    private static final String SECOND = "second";

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);

        Map<String,String> userInputNumbersAsStrings = gatherInputStrings(sc);

        try {
            while(!userInputNumbersAsStrings.values().stream()
                    .filter(num -> Integer.parseInt(num) < 0)
                    .toList().isEmpty()) {
                System.out.println("Negative numbers are not allowed. Retry.");
                userInputNumbersAsStrings = gatherInputStrings(sc);
            }

            Integer firstNumber = Integer.parseInt(userInputNumbersAsStrings.get(FIRST));
            Integer secondNumber = Integer.parseInt(userInputNumbersAsStrings.get(SECOND));

            System.out.println(firstNumber + " + " + secondNumber + " = " + doAddiction(firstNumber, secondNumber) + "\n" +
                               firstNumber + " - " + secondNumber + " = " + doSubtraction(firstNumber, secondNumber) + "\n" +
                               firstNumber + " * " + secondNumber + " = " + doMultiplication(firstNumber, secondNumber) + "\n" +
                               firstNumber + " / " + secondNumber + " = " + doDivision(firstNumber, secondNumber));
        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }

        sc.close();
    }

    private static Map<String, String> gatherInputStrings(Scanner sc) {
        System.out.print("What is the first number? ");
        String firstInput = sc.nextLine();
        System.out.print("What is the second number? ");
        String secondInput = sc.nextLine();

        return new HashMap<>() {{
            put(FIRST, firstInput);
            put(SECOND, secondInput);
        }};
    }

    private static Integer doAddiction(Integer firstNumber, Integer secondNumber) {
        return firstNumber + secondNumber;
    }

    private static Integer doSubtraction(Integer firstNumber, Integer secondNumber) {
        return firstNumber - secondNumber;
    }

    private static Integer doMultiplication(Integer firstNumber, Integer secondNumber) {
        return firstNumber * secondNumber;
    }

    private static Integer doDivision(Integer firstNumber, Integer secondNumber) {
        return firstNumber / secondNumber;
    }
}