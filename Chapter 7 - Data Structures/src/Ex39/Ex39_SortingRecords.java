package Ex39;

import javax.swing.*;
import java.util.List;
import java.util.*;

public class Ex39_SortingRecords {
    
    private static final String FIRST_NAME = "firstName",
            LAST_NAME = "lastName",
            POSITION = "position",
            SEPARATION_DATE = "separationDate";

    public static void main(String[] args) {
        
        List<Map<String,String>> employees = new ArrayList<>(initEmployeeList());

        JFrame f = new JFrame();
        f.setAlwaysOnTop(true);
        String[] options = {"First name", "Last Name", "Position", "Separation Date"};
        int sortingOption = JOptionPane.showOptionDialog(f, "How should employees be sorted by?", "Choose one",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        String fieldToSortBy = switch (sortingOption) {
            case 0 -> FIRST_NAME;
            case 1 -> LAST_NAME;
            case 2 -> POSITION;
            case 3 -> SEPARATION_DATE;
            default -> "";
        };

        Comparator<Map<String, String>> mapComparator = Comparator.comparing(m -> m.get(fieldToSortBy));

        employees.sort(mapComparator);

        System.out.format("%-25s%-25s%-25s\n", "Name", "| Position", "| Separation Date");
        System.out.println("_________________________|________________________|_________________");
        for(Map<String,String> employee : employees) {
            System.out.format("%-25s%-25s%-25s\n", employee.get(FIRST_NAME) + " " + employee.get(LAST_NAME) ,
                               "| " + employee.get(POSITION) , "| " +employee.get(SEPARATION_DATE));
        }

        f.dispose();

    }

    private static List<Map<String, String>> initEmployeeList() {
        
        return List.of(
                Map.of(FIRST_NAME, "John",
                        LAST_NAME, "Johnson",
                        POSITION, "Manager",
                        SEPARATION_DATE, "2016-31-12"),
                Map.of(FIRST_NAME, "Tou",
                        LAST_NAME, "Xiong",
                        POSITION, "Software Engineer",
                        SEPARATION_DATE, "2016-10-05"),
                Map.of(FIRST_NAME, "Michaela",
                        LAST_NAME, "Michaelson",
                        POSITION, "District Manager",
                        SEPARATION_DATE, "2015-12-19"),
                Map.of(FIRST_NAME, "Jake",
                        LAST_NAME, "Jacobson",
                        POSITION, "Programmer",
                        SEPARATION_DATE, ""),
                Map.of(FIRST_NAME, "Jacquelyn",
                        LAST_NAME, "Jackson",
                        POSITION, "DBA",
                        SEPARATION_DATE, ""),
                Map.of(FIRST_NAME, "Sally",
                        LAST_NAME, "Weber",
                        POSITION, "Web Developer",
                        SEPARATION_DATE, "2015-12-18")
        );
    }

}