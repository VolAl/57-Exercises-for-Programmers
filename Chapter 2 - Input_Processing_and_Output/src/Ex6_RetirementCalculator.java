import java.text.MessageFormat;
import java.time.Year;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.System.in;

public class Ex6_RetirementCalculator {

    private static final String AGE = "age";
    private static final String RETIREMENT_AGE = "retirement_age";
    private static final Pattern isNumericPattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static void main(String[] args) {
        Scanner sc = new Scanner(in);

        Map<String,String> userAgesAsStrings = gatherInputStrings(sc);

        while(!userAgesAsStrings.values().stream()
                .filter(age -> !isNumericPattern.matcher(age).matches())
                .toList().isEmpty()) {
            System.out.println("Input is not numeric. Retry.");
            userAgesAsStrings = gatherInputStrings(sc);
        }

        while(!userAgesAsStrings.values().stream()
                .filter(age -> Integer.parseInt(age) < 0)
                .toList().isEmpty()) {
            System.out.println("Input cannot be a negative number. Retry.");
            userAgesAsStrings = gatherInputStrings(sc);
        }

        int age = Integer.parseInt(userAgesAsStrings.get(AGE));
        int retirementAge = Integer.parseInt(userAgesAsStrings.get(RETIREMENT_AGE));

        if(age >= retirementAge) {
            System.out.println("You can already retire!");
        } else {
            int yearsLeftBeforeRetirement = retirementAge - age;
            int currentYear = Year.now().getValue();
            System.out.println(MessageFormat.format(
                    "You have {0} years left before you can retire. \n" +
                    "It''s {1}, so you can retire in {2}.",
                    yearsLeftBeforeRetirement,
                    String.valueOf(currentYear),
                    String.valueOf(currentYear + yearsLeftBeforeRetirement)));
        }

        sc.close();
    }

    private static Map<String, String> gatherInputStrings(Scanner sc) {
        System.out.print("What is your current age? ");
        String firstInput = sc.nextLine();
        System.out.print("At what age would you like to retire? ");
        String secondInput = sc.nextLine();

        return new HashMap<>() {{
            put(AGE, firstInput);
            put(RETIREMENT_AGE, secondInput);
        }};
    }
}