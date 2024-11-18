package Ex34;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.MessageFormat;
import java.util.*;

import static java.lang.System.in;

public class Ex34_EmployeeListRemoval {

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);

        File file = new File("employeeList.txt");
        List<String> employeeList = retrieveEmployeeListFromFile(file);

        printEmployeeList(employeeList);

        System.out.print("\nEnter an employee name to remove: ");

        String employeeToRemove = sc.nextLine();

        while(!employeeList.contains(employeeToRemove)) {
            System.out.print(employeeToRemove + " is not an employee. Please try again.\n\nEnter an employee name to remove: ");
            employeeToRemove = sc.nextLine();
        }

        employeeList.remove(employeeToRemove);

        try {
            FileWriter writer = new FileWriter(file.getName());
            for (String str : employeeList) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();

            printEmployeeList(employeeList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        sc.close();
    }

    private static List<String> retrieveEmployeeListFromFile(File file) {
        List<String> employeeList = new ArrayList<>();
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                employeeList.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred." +e.getMessage());
        }
        return employeeList;
    }

    private static void printEmployeeList(List<String> employeeList) {
        System.out.println(MessageFormat.format("\nThere are {0} employees:",
                employeeList.size()));
        for(String s : employeeList) {
            System.out.println(s);
        }
    }
}