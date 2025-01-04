package com.tracking_inventory.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.function.Supplier;

@Setter
@Getter
@Data
public class ItemDao implements Serializable, Supplier<ItemDao> {

    private String id;
    private String name;
    private String serialNumber;
    private double value;
    private byte[] photo;

    @JsonCreator
    public ItemDao(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("serialNumber") String serialNumber, @JsonProperty("value") double value, @JsonProperty("photo") byte[] photo) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.value = value;
        this.photo = photo;
    }

    @Override
    public ItemDao get() {
        return null;
    }

    @Override
    public String toString() {
        return "ItemDao{" +
               "name='" + name + '\'' +
               ", serialNumber='" + serialNumber + '\'' +
               ", value= $" + value +
               '}';
    }
}