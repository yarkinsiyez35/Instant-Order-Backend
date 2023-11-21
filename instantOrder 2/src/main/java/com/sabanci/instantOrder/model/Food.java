package com.sabanci.instantOrder.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Document
public class Food {

    @Id
    @Field("_id")
    private String objectId;
    @Field("name")
    private String name;
    @Field("price")
    private double price;

    public Food() {
    }

    public Food(String name, double price) {
        this.name = name;
        this.price = price;
    }


    public String getObjectId() {
        return objectId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Double.compare(price, food.price) == 0 && Objects.equals(objectId, food.objectId) && Objects.equals(name, food.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId, name, price);
    }

    @Override
    public String toString() {
        return "Food{" +
                "objectId='" + objectId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
