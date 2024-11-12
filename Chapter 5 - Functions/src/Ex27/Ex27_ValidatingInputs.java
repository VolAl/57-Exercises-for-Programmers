package Ex27;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.System.in;

public class Ex27_ValidatingInputs {

    private static final String FIRST_NAME = "first_name", LAST_NAME = "last_name", ZIP_CODE = "zip_code", EMPLOYEE_ID = "employee_id";
    private static final Pattern containsOnlyNumbersPattern = Pattern.compile("^\\d*$");
    private static final Pattern employeeIDFormatPattern = Pattern.compile("^([a-zA-Z]){2}-(\\d){4}$");


    public static void main(String[] args) {

        Scanner sc = new Scanner(in);

        String validationMessages = "\n";

        while(!validationMessages.isEmpty()) {
            System.out.print("Enter the first name: ");
            String firstName = sc.nextLine();
            System.out.print("Enter the last name: ");
            String lastName = sc.nextLine();
            System.out.print("Enter the ZIP code: ");
            String zipCode = sc.nextLine();
            System.out.print("Enter the employee ID: ");
            String employeeID = sc.nextLine();

            Map<String,String> inputData = Map.of(
                    FIRST_NAME, firstName,
                    LAST_NAME, lastName,
                    ZIP_CODE, zipCode,
                    EMPLOYEE_ID, employeeID
            );

            validationMessages = validateInput(inputData);

            System.out.println(validationMessages);
        }

        System.out.println("There were no errors found.");

        sc.close();
    }

    private static String validateInput(Map<String,String> inputData) {

        String validationMessages = "";

        String firstName = inputData.get(FIRST_NAME);
        String lastName = inputData.get(LAST_NAME);
        validationMessages += firstName.isEmpty() ? validateInputMustNotBeEmpty(firstName, FIRST_NAME)
                : validateInputMustBeLongerThanTwoCharacters(firstName, FIRST_NAME);
        validationMessages += lastName.isEmpty() ? validateInputMustNotBeEmpty(lastName, LAST_NAME)
                : validateInputMustBeLongerThanTwoCharacters(lastName, LAST_NAME);
        validationMessages += validateZipCodeMustBeNumeric(inputData.get(ZIP_CODE));
        validationMessages += validateFormatEmployeeID(inputData.get(EMPLOYEE_ID));

        validationMessages += validationMessages.isEmpty() ? "" : "\nCorrect your input and retry.\n";

        return validationMessages;
    }

    private static String validateInputMustNotBeEmpty(String s, String inputName) {
        if(s.isEmpty()) {
            return MessageFormat.format("The {0} name must be filled in.\n",
                    inputName.split("_")[0]);
        }
        return "";
    }

    private static String validateInputMustBeLongerThanTwoCharacters(String s, String inputName) {
        if(s.length() < 2) {
            return MessageFormat.format("\"{0}\" is not a valid {1} name. It is too short.\n",
                    s,
                    inputName.split("_")[0]);
        }
        return "";
    }

    private static String validateZipCodeMustBeNumeric(String s) {
        if(!containsOnlyNumbersPattern.matcher(s).matches()) {
            return "The ZIP code must be numeric.\n";
        }
        return "";
    }

    private static String validateFormatEmployeeID(String s) {
        if(!employeeIDFormatPattern.matcher(s).matches()) {
            return MessageFormat.format("{0} is not a valid ID.\n",
                    s);
        }
        return "";
    }
}