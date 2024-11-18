package Ex40;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.in;

public class Ex40_FilteringRecords {

    private static final  Scanner sc = new Scanner(in);
    
    private static final String FIRST_NAME = "firstName",
            LAST_NAME = "lastName",
            POSITION = "position",
            SEPARATION_DATE = "separationDate";

    public static void main(String[] args) {
        
        List<Map<String,String>> employees = new ArrayList<>(initEmployeeList());

        System.out.println("Employee List:");
        printEmployeeList(employees);

        JFrame f = new JFrame();
        f.setAlwaysOnTop(true);
        String[] options = {"First name or Last Name", "Position", "Separation Date older than 6 months ago"};
        int sortingOption = JOptionPane.showOptionDialog(f, "How you like to search the employees by?", "Choose one",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        employees = switch (sortingOption) {
            case 0 -> searchByFirstOrLastName(employees);
            case 1 -> searchByPosition(employees);
            case 2 -> searchBySeparationDateOlderThanSixMonths(employees);
            default -> employees;
        };

        printEmployeeList(employees);

        sc.close();
        f.dispose();

    }

    private static List<Map<String, String>> initEmployeeList() {

        File file = new File("employeeList.txt");
        List<Map<String, String>> employeeList = new ArrayList<>();

        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataSeparated = data.split(",");
                Map<String, String> employee = new HashMap<>();
                employee.put(FIRST_NAME, dataSeparated[0]);
                employee.put(LAST_NAME, dataSeparated[1]);
                employee.put(POSITION, dataSeparated[2]);
                employee.put(SEPARATION_DATE, dataSeparated[3].equals("'") ? "" : dataSeparated[3]);
                employeeList.add(employee);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred." +e.getMessage());
        }

        return employeeList;
    }

    private static void printEmployeeList(List<Map<String, String>> employees) {
        System.out.format("\n%-25s%-25s%-25s\n", "Name", "| Position", "| Separation Date");
        System.out.println("_________________________|________________________|_________________");
        for(Map<String,String> employee : employees) {
            System.out.format("%-25s%-25s%-25s\n", employee.get(FIRST_NAME) + " " + employee.get(LAST_NAME) ,
                    "| " + employee.get(POSITION) , "| " +employee.get(SEPARATION_DATE));
        }
    }

    private static List<Map<String, String>> searchByFirstOrLastName(List<Map<String, String>> employees) {

        System.out.print("\nEnter a search string: ");
        String searchString = sc.nextLine();

        employees = employees.stream().filter( e -> e.get(FIRST_NAME).toLowerCase().contains(searchString.toLowerCase()) ||
                                                    e.get(LAST_NAME).toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());

        return employees;

    }

    private static List<Map<String, String>> searchByPosition(List<Map<String, String>> employees) {

        System.out.print("\nEnter a search string: ");
        String searchString = sc.nextLine();

        employees = employees.stream().filter( e -> e.get(POSITION).toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());

        return employees;

    }

    private static List<Map<String, String>> searchBySeparationDateOlderThanSixMonths(List<Map<String, String>> employees) {
        int currentYear = 2016;
        int currentMonth = 12;
        employees = employees.stream().filter( e -> !e.get(SEPARATION_DATE).isEmpty()
                        && (Integer.parseInt(e.get(SEPARATION_DATE).split("-")[0]) < currentYear
                        || Integer.parseInt(e.get(SEPARATION_DATE).split("-")[1]) < currentMonth-6))
                .collect(Collectors.toList());

        return employees;

    }

}