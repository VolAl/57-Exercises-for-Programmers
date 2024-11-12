import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.System.in;

public class Ex28_AddingNumbers {

    private static final Pattern isNumericPattern = Pattern.compile("^\\d*$");

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);

        System.out.print("Enter how many numbers you would like to add: ");
        String totalNumbers = sc.nextLine();
        int sum = 0;

        while(!isNumericPattern.matcher(totalNumbers).matches()) {
            System.out.print("The value inserted is non numeric. Retry.\n" +
                             "Enter how many numbers you would like to add: ");
            totalNumbers = sc.nextLine();
        }

        for(int i=0; i<Integer.parseInt(totalNumbers); i++) {
            System.out.print("Enter a number: ");
            String number = sc.nextLine();
            if(isNumericPattern.matcher(number).matches()) {
                sum += Integer.parseInt(number);
            }
        }

        System.out.println("The total is " + sum + ".");

        sc.close();
    }
}