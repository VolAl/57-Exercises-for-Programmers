package com.tracking_inventory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracking_inventory.model.ItemDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

import static java.lang.System.in;

@Component
public class OperationsRunner implements CommandLineRunner {

    private final static File file = new File("items.json");
    private final static ObjectMapper objMapper = new ObjectMapper();
    private final static Scanner sc = new Scanner(in);

    @Override
    public void run(String... args) throws Exception {

        if(isFileEmpty()) {
            FileWriter myWriter = new FileWriter(file.getName());
            myWriter.write("[]");
            myWriter.close();
        }

        List<ItemDao> items = new ArrayList<>();

        try {
            items = objMapper.readValue(file, new TypeReference<>() {
            });
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred." +e.getMessage());
            sc.close();
            System.exit(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String message = "Welcome";
        startInventory(message, items);
    }

    private static void startInventory(String message, List<ItemDao> items) {
        String messageToPrint = message + """
                 to your Inventory!
                What would you like to do?
                1) Add an item
                2) Search for an item
                3) Print report in HTML / CSV
                4) Exit.""";
        System.out.println(messageToPrint);
        String answer = sc.nextLine();

        while(!answer.equals("1") && !answer.equals("2") && !answer.equals("3") && !answer.equals("4")) {
            System.out.print("Invalid answer, specify only 1, 2, 3 or 4. Retry: ");
            answer = sc.nextLine();
        }

        switch (answer) {
            case "1":
                addNewItem(items);
                break;
            case "2":
                searchItem(items);
                break;
            case "3":
                printReport(items);
                break;
            case "4":
                System.out.println("See you next time!");
                sc.close();
                System.exit(0);
        }
    }

    private static void printReport(List<ItemDao> items) {
        System.out.println("""
                Would you like to do print the report in:
                1) HTML
                2) CSV
                3) Exit""");
        String answer = sc.nextLine();

        while(!answer.equals("1") && !answer.equals("2") && !answer.equals("3")) {
            System.out.print("Invalid answer, specify only 1, 2 or 3. Retry: ");
            answer = sc.nextLine();
        }

        switch (answer) {
            case "1":
                printHtmlReport(items);
                break;
            case "2":
                printCsvReport(items);
                break;
            case "3":
                backToInventory(items);
                break;
        }
    }

    private static void backToInventory(List<ItemDao> items) {
        String message = "Back ";
        startInventory(message, items);
    }

    private static void printCsvReport(List<ItemDao> items) {
        File csvOutputFile = new File("out/report.csv");
        FileWriter writer;
        try {
            writer = new FileWriter(csvOutputFile.getName());
            writer.write("Name,Serial Number,Value\n");

            for(ItemDao item : items) {
                writer.write(item.getName() + "," + item.getSerialNumber() + ",$" + item.getValue() + "\n" );
            }
            writer.close();

            System.out.println("Report generated!");
            backToInventory(items);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printHtmlReport(List<ItemDao> items) {
        File csvOutputFile = new File("out/report.html");
        FileWriter writer;
        try {
            writer = new FileWriter(csvOutputFile.getName());
            StringBuilder htmlString = new StringBuilder("""
                    <div>
                        <table>
                            <thead>
                                <tr>
                                  <th>Name</th>
                                  <th>Serial Number</th>
                                  <th>Value</th>
                                </tr>
                              </thead>
                              <tbody>""");

            for (ItemDao item : items) {
                htmlString.append("<tr><td>").append(item.getName()).append("</td>");
                htmlString.append("<td>").append(item.getSerialNumber()).append("</td>");
                htmlString.append("<td>").append("$").append(item.getValue()).append("</td></tr>");
            }

            htmlString.append("""
                            </tbody>
                        </table>
                    </div>
                    """);
            writer.write(htmlString.toString());
            writer.close();

            System.out.println("Report generated!");
            backToInventory(items);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    boolean isFileEmpty() throws IOException {
        if (!OperationsRunner.file.exists()) {
            return OperationsRunner.file.createNewFile();
        }
        return OperationsRunner.file.length() == 0;
    }

    private static void searchItem(List<ItemDao> items) {
        System.out.print("What is the item's name? ");
        String itemName = sc.nextLine();
        ItemDao foundItem = findItemByName(itemName, items);
        if(foundItem == null) {
            System.out.print("Sorry, that item was not found in your inventory.\nWould you like to add it? (y/n): ");
            String answer = sc.nextLine();
            while(!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
                System.out.print("Invalid answer, specify only 'y' or 'n'. Retry: ");
                answer = sc.nextLine();
            }
            if(answer.equalsIgnoreCase("y")) {
                addNewItem(items);
            }
        } else {
            System.out.println(foundItem);
        }

        System.out.print("Would you like to search another item? (y/n): ");
        String answer = sc.nextLine();
        while(!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
            System.out.print("Invalid answer, specify only 'y' or 'n'. Retry: ");
            answer = sc.nextLine();
        }
        if(answer.equalsIgnoreCase("y")) {
            try {
                items = objMapper.readValue(file, new TypeReference<>() {
                });
                searchItem(items);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            backToInventory(items);
        }
    }

    private static ItemDao findItemByName(String itemName, List<ItemDao> items) {
        return !items.isEmpty() ?
                items.stream().filter(item -> item.getName().toUpperCase().contains(itemName.toUpperCase())).findFirst().orElseGet(new ItemDao("", "not_found","",0, null))
                :
                null;
    }

    private static void addNewItem(List<ItemDao> items) {
        System.out.print("Enter the item's name: ");
        String name = sc.nextLine();
        System.out.print("Enter the item's serial number: ");
        String serialNumber = sc.nextLine();
        System.out.print("Enter the item's value: ");
        double value;
        try {
            value = sc.nextDouble();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String md5 = DigestUtils.md5Hex(name.replace(" ", "-")).toUpperCase();
        System.out.print("Enter the item's photo: ");
        byte[] photo = uploadItemPhoto();

        //consume last line
        sc.nextLine();

        items.add(new ItemDao(md5, name, serialNumber, value, photo));
        try {
            objMapper.writeValue(file, items);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.print("Item added!\nWould you like to add a new one? (y/n): ");
        String answer = sc.nextLine();
        while(!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
            System.out.print("Invalid answer, specify only 'y' or 'n'. Retry: ");
            answer = sc.nextLine();
        }
        if(answer.equalsIgnoreCase("y")) {
            addNewItem(items);
        } else {
            backToInventory(items);
        }
    }

    private static byte[] uploadItemPhoto() {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        byte[] photo = createUI(frame);
        frame.setSize(560, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);
        frame.dispose();
        return photo;
    }

    private static byte[] createUI(final JFrame frame){
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);
        final JLabel label = new JLabel();
        final byte[][] fileContent = {null};
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new ImageFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);

        int option = fileChooser.showOpenDialog(frame);
        if(option == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            try {
                fileContent[0] = Files.readAllBytes(file.toPath());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            label.setText("File Selected: " + file.getName());
        }else{
            label.setText("Open command canceled");
        }
        panel.add(label);
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        return fileContent[0];
    }
}