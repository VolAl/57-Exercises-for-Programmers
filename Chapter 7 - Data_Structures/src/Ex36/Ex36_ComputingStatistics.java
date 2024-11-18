package Ex36;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.*;
// import java.util.regex.Pattern;
// import static java.lang.System.in;

public class Ex36_ComputingStatistics {

    // private static final Pattern isNumericPattern = Pattern.compile("^\\d*$");

    public static void main(String[] args) {

        // Scanner sc = new Scanner(in);
        File file = new File("responseTimes.txt");
        List<Integer> responseTimes = new ArrayList<>();

        /*
        while (true) {
            System.out.print("Enter a number: ");
            String number = sc.nextLine();
            if(number.equals("done")) {
                break;
            }
            while(!isNumericPattern.matcher(number).matches()) {
                System.out.print("The value inserted is non numeric. Retry.\n" +
                                 "Enter a number: ");
                number = sc.nextLine();
            }
            responseTimes.add(Integer.parseInt(number));
        }
         */
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                int data = myReader.nextInt();
                responseTimes.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred." +e.getMessage());
        }

        System.out.println(MessageFormat.format("""
                        Numbers: {0}
                        The average is {1}.
                        The minimum is {2}.
                        The maximum is {3}.
                        The standard deviation is {4}""",
                responseTimes,
                mean(responseTimes),
                min(responseTimes),
                max(responseTimes),
                standardDeviation(responseTimes)));

    }

    private static int mean(List<Integer> responseTimes) {
        int sum = 0;
        for(Integer i : responseTimes) {
            sum += i;
        }
        return sum / responseTimes.size();
    }

    private static int min(List<Integer> responseTimes) {
        Collections.sort(responseTimes);
        return responseTimes.getFirst();
    }

    private static int max(List<Integer> responseTimes) {
        Collections.sort(responseTimes);
        return responseTimes.getLast();
    }

    private static double standardDeviation(List<Integer> responseTimes) {
        List<Integer> differenceNumberFromMeanSquared = new ArrayList<>();
        int mean = mean(responseTimes);
        for(Integer i : responseTimes) {
            int difference = i - mean;
            differenceNumberFromMeanSquared.add(difference * difference);
        }
        int meanSquaredValues = mean(differenceNumberFromMeanSquared);
        return Math.sqrt(meanSquaredValues);
    }
}