package Ex38;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.*;

public class Ex38_FilteringValues {
    public static void main(String[] args) {

        File file = new File("numberList.txt");
        List<Integer> numberList = new ArrayList<>();

        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                int data = myReader.nextInt();
                numberList.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred." +e.getMessage());
        }

        System.out.print("The list of numbers is ");
        for(int i : numberList) {
            System.out.print(i + " ");
        }

        List<Integer> evenNumberList = filterEvenNumbers(numberList);
        System.out.print("\nThe even numbers are ");
        for(int i : evenNumberList) {
            System.out.print(i + " ");
        }

        List<String> evenNumberedLinesList = filterEvenNumberedLines(numberList);
        System.out.println("\nThe even numbered file lines (with numbers) are: ");
        for(String s : evenNumberedLinesList) {
            System.out.print(s);
        }
    }

    private static List<Integer> filterEvenNumbers(List<Integer> numberList) {
        List<Integer> evenNumberList = new ArrayList<>();
        for(int i : numberList) {
            if(i % 2 == 0) {
                evenNumberList.add(i);
            }
        }
        return evenNumberList;
    }

    private static List<String> filterEvenNumberedLines(List<Integer> numberList) {
        List<String> evenNumberedLinesList = new ArrayList<>();
        for(int i=0; i < numberList.size()-1; i++) {
            if(i % 2 == 0) {
                evenNumberedLinesList.add("index: " + i + ", number: " + numberList.get(i) + "\n");
            }
        }
        return evenNumberedLinesList;
    }
}