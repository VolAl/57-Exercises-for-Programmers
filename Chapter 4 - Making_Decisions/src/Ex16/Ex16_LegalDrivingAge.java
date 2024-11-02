package Ex16;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.System.in;

public class Ex16_LegalDrivingAge {

    private static final Scanner sc = new Scanner(in);
    private static final Pattern isNumericPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    private static final int legalDrivingAge = 16;

    public static void main(String[] args) {

        File file = new File("legalDrivingAgePerCountry.txt");
        Map<String,Integer> legalDrivingAgePerCountry = retrieveLegalDrivingAgePerCountryFromFile(file);

        String age = getAgeString();

        while(!isNumericPattern.matcher(age).matches() || Integer.parseInt(age) < 0) {
            System.out.println("You should enter a valid age. Retry.");
            age = getAgeString();
        }
        int ageInt = Integer.parseInt(age);

        List<String> allowedCountriesToDriveBySpecifiedAge = new ArrayList<>();
        legalDrivingAgePerCountry.forEach((k,val) -> {
            if (val <= ageInt) {
                allowedCountriesToDriveBySpecifiedAge.add(k);
            }
        });

        Collections.sort(allowedCountriesToDriveBySpecifiedAge);

        StringBuilder allowedCountriesToDriveBySpecifiedAgeMessage = new StringBuilder("\nYou can legally drive in the following countries:\n");
        for(String c : allowedCountriesToDriveBySpecifiedAge) {
            allowedCountriesToDriveBySpecifiedAgeMessage.append(c).append("\n");
        }

        System.out.println(MessageFormat.format("According to the legal age of {0}, you are {1}old enough to legally drive. {2}",
                legalDrivingAge,
                (ageInt < legalDrivingAge ? "not " : ""),
                (!allowedCountriesToDriveBySpecifiedAge.isEmpty() ? allowedCountriesToDriveBySpecifiedAgeMessage.toString() : "")));

        sc.close();
    }

    private static String getAgeString() {
        System.out.print("What is your age? ");
        return sc.next();
    }

    private static Map<String, Integer> retrieveLegalDrivingAgePerCountryFromFile(File file) {
        Map<String,Integer> legalDrivingAgePerCountry = new HashMap<>();
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String country = data.split(",")[0];
                Integer legalDrivingAge = Integer.parseInt(data.split(",")[1]);
                legalDrivingAgePerCountry.put(country, legalDrivingAge);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred." +e.getMessage());
        }
        return legalDrivingAgePerCountry;
    }
}