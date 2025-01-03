package com.tracking_inventory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracking_inventory.model.ItemDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.System.in;

@Component
public class OperationsRunner implements CommandLineRunner {

    private final static File file = new File("items.json");
    private final static ObjectMapper objMapper = new ObjectMapper();
    private final static Scanner sc = new Scanner(in);

    @Override
    public void run(String... args) throws Exception {

        if(isFileEmpty(file)) {
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

        String message = "Welcome to your Inventory!\nWhat would you like to do?\n1) Add an item\n2) Search for an item\n3) Exit.";
        startInventory(message, items);
    }

    private static void startInventory(String message, List<ItemDao> items) {
        System.out.println(message);
        String answer = sc.nextLine();

        while(!answer.equals("1") && !answer.equals("2") && !answer.equals("3")) {
            System.out.print("Invalid answer, specify only 1, 2 or 3. Retry: ");
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
                System.out.println("See you next time!");
                sc.close();
                System.exit(0);
        }
    }

    boolean isFileEmpty(File file) throws IOException {
        if (!file.exists()) {
            return file.createNewFile();
        }
        return file.length() == 0;
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
            String message = "Back to your Inventory!\nWhat would you like to do?\n1) Add an item\n2) Search for an item\n3) Exit.";
            startInventory(message, items);
        }
    }

    private static void addNewItem(List<ItemDao> items) {
        System.out.print("Enter the item's name: ");
        String name = sc.nextLine();
        System.out.print("Enter the item's serial number: ");
        String serialNumber = sc.nextLine();
        System.out.print("Enter the item's value: ");
        double value = sc.nextDouble();
        String md5 = DigestUtils.md5Hex(name.replace(" ", "-")).toUpperCase();

        //consume last line
        sc.nextLine();

        items.add(new ItemDao(md5, name, serialNumber, value, null));
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
            String message = "Back to your Inventory!\nWhat would you like to do?\n1) Add an item\n2) Search for an item\n3) Exit.";
            startInventory(message, items);
        }
    }

    private static ItemDao findItemByName(String itemName, List<ItemDao> items) {
        return !items.isEmpty() ?
                items.stream().filter(item -> item.getName().equalsIgnoreCase(itemName)).findFirst().orElseGet(new ItemDao("", "not_found","",0, null))
                :
                null;
    }
}
