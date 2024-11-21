package Ex44;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static java.lang.System.in;

public class Ex44_ProductSearch {

    private final static ObjectMapper objMapper = new ObjectMapper();
    private final static Scanner sc = new Scanner(in);
    private final static File file = new File("products.json");


    public static void main(String[] args) {

        Map<String, List<Product>> products = new HashMap<>();

        try {
            products = objMapper.readValue(file, new TypeReference<>() {
            });


        } catch (FileNotFoundException e) {
            System.out.println("An error occurred." +e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        searchProduct(products);
    }

    private static void searchProduct(Map<String, List<Product>> products) {
        System.out.print("What is the product name? ");
        String productName = sc.nextLine();
        Product foundProduct = findProductByName(productName, products.get("products"));
        if(foundProduct == null) {
            System.out.print("Sorry, that product was not found in our inventory.\nWould you like to add it? (y/n): ");
            String answer = sc.nextLine();
            while(!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
                System.out.print("Invalid answer, specify only 'y' or 'n'. Retry: ");
                answer = sc.nextLine();
            }
            if(answer.equalsIgnoreCase("y")) {
                System.out.print("Enter the price: ");
                double price = Double.parseDouble(sc.nextLine());
                System.out.print("Enter the quantity: ");
                int qty = Integer.parseInt(sc.nextLine());
                List<Product> productList = products.get("products");
                productList.add(new Product(productName, price, qty));
                products = new HashMap<>();
                products.put("products", productList);
                try {
                    objMapper.writeValue(file, products);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            System.out.println(foundProduct);
        }

        System.out.print("Would you like to search another product? (y/n): ");
        String answer = sc.nextLine();
        while(!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
            System.out.print("Invalid answer, specify only 'y' or 'n'. Retry: ");
            answer = sc.nextLine();
        }
        if(answer.equalsIgnoreCase("y")) {
            try {
                products = objMapper.readValue(file, new TypeReference<>() {
                });
                searchProduct(products);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.exit(0);
        }
    }

    private static Product findProductByName(String productName, List<Product> products) {
        return products.stream().filter(product -> product.getName().equalsIgnoreCase(productName)).findFirst().orElseGet(new Product("not_found", 0, 0));
    }

}