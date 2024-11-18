package Ex41;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;import java.util.Scanner;

import static java.lang.System.in;

public class Ex41_NameSorter {
    public static void main(String[] args) {

        Scanner sc = new Scanner(in);
        List<String> names = new ArrayList<>();

        String name;
        do {
            System.out.print("Enter a name: ");
            name = sc.nextLine();
            if(!name.isEmpty()) {
                names.add(name);
            }
        } while (!name.isEmpty());

        Collections.sort(names);

        try {
            File file = new File("output.txt");
            FileWriter writer = new FileWriter(file.getName());

            writer.write("Total of " + names.size() + " names" + System.lineSeparator());
            writer.write("-----------------------" + System.lineSeparator());
            for (String str : names) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        sc.close();
    }
}