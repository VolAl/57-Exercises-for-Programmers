package Ex44;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.function.Supplier;

public class Product implements Supplier<Product> {

    private String name;
    private double price;
    private int quantity;

    @JsonCreator
    public Product(@JsonProperty("name") String name, @JsonProperty("price") double price, @JsonProperty("quantity") int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public Product get() {
        return null;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
               "Price: $" + price + "\n" +
               "Quantity: " + quantity;
    }

}
