import java.text.MessageFormat;
import java.util.*;

import static java.lang.System.in;

public class Ex22_ComparingNumbers {

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);
        List<Integer> numbersInserted = new ArrayList<>();

        try {
            System.out.printf("Welcome to Largest Number Finder!\nEnter as many integer numbers as you like.\nIn order to stop inserting numbers, just press \"↩\" (\"Enter\" key) instead of inserting a number.\nWarning: Any non-numeric character and non-integer value will make the program exit.\n%n");
            int itemNumber = 1;
            while(true) {
                System.out.print("Enter the " + itemNumber + " number: ");
                String item = sc.nextLine();

                if (item.isEmpty()) {
                    break;
                } else {
                    int number = Integer.parseInt(item);
                    if (numbersInserted.contains(number)) {
                        System.out.println("Number already entered. Retry.");
                    } else {
                        numbersInserted.add(number);
                        itemNumber++;
                    }
                }
            }

            int largestNumber = Integer.MIN_VALUE;
            for(Integer n : numbersInserted) {
                if(n > largestNumber) {
                    largestNumber = n;
                }
            }

            System.out.println(MessageFormat.format("The largest number is {0}.", String.valueOf(largestNumber)));

        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }


        sc.close();
    }
}