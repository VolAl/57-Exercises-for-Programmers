package Ex42;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Ex42_ParsingDataFile {
    
    private static final String FIRST_NAME = "first",
            LAST_NAME = "last",
            SALARY = "salary";

    public static void main(String[] args) {
        
        List<Map<String,String>> employees = new ArrayList<>(initEmployeeList());

        Comparator<Map<String, String>> mapComparator = Comparator.comparing(m -> m.get(SALARY));
        employees.sort(mapComparator);
        Collections.reverse(employees);

        System.out.println("Employee List:");
        printEmployeeList(employees);

    }

    private static List<Map<String, String>> initEmployeeList() {

        // File file = new File("data.csv");
        List<Map<String, String>> employeeList = new ArrayList<>();

        /*
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataSeparated = data.split(",");
                Map<String, String> employee = new HashMap<>();
                employee.put(LAST_NAME, dataSeparated[0]);
                employee.put(FIRST_NAME, dataSeparated[1]);
                employee.put(SALARY, dataSeparated[2]);
                employeeList.add(employee);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred." +e.getMessage());
        }
        */

        try (CSVReader csvReader = new CSVReader(new FileReader("data.csv"))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                Map<String, String> employee = new HashMap<>();
                employee.put(LAST_NAME, values[0]);
                employee.put(FIRST_NAME, values[1]);
                employee.put(SALARY, values[2]);
                employeeList.add(employee);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return employeeList;
    }

    private static void printEmployeeList(List<Map<String, String>> employees) {
        int longestFirstNameSize = findLongest(employees).get(0) + 1;
        int longestLastNameSize = findLongest(employees).get(1) + 1;
        int longestSalarySize = findLongest(employees).get(2) + 1;
        System.out.format("\n%-" + longestFirstNameSize + "s%-" + longestLastNameSize + "s%-" + longestSalarySize +"s\n",
                "Last", "First", "Salary");
        System.out.println("____________________________________________________________");
        for(Map<String,String> employee : employees) {
            double amount = Double.parseDouble(employee.get(SALARY));
            String salaryFormatted = "$" + String.format("%,.2f", amount);
            System.out.format("%-" + longestFirstNameSize + "s%-" + longestLastNameSize + "s%-" + longestSalarySize +"s\n",
                    employee.get(LAST_NAME), employee.get(FIRST_NAME) ,
                    salaryFormatted);
        }
    }

    private static Map<Integer,Integer> findLongest(List<Map<String, String>> employees) {
        int longestFirstNameSize = 0, longestLastNameSize = 0, longestSalarySize = 0;
        Map<Integer,Integer> result = new HashMap<>();
        for(Map<String,String> employee : employees) {
            if(employee.get(FIRST_NAME).length() > longestFirstNameSize) {
                longestFirstNameSize = employee.get(FIRST_NAME).length();
            }
            if(employee.get(LAST_NAME).length() > longestLastNameSize) {
                longestLastNameSize = employee.get(LAST_NAME).length();
            }
            if(employee.get(SALARY).length() > longestSalarySize) {
                longestSalarySize = employee.get(SALARY).length();
            }
        }
        result.put(0,longestFirstNameSize);
        result.put(1,longestLastNameSize);
        result.put(2,longestSalarySize);

        return result;
    }

}