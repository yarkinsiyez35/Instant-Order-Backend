package com.sabanci.instantOrder.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Objects;

@Document
public class Category {
    @Id
    private String objectId;
    @Field("name")
    private String name;
    @Field("foods")
    @DBRef
    private List<Food> foods;

    public Category() {
    }

    public Category(String name, List<Food> foods) {
        this.name = name;
        this.foods = foods;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getName() {
        return name;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(objectId, category.objectId) && Objects.equals(name, category.name) && Objects.equals(foods, category.foods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId, name, foods);
    }

    @Override
    public String toString() {
        return "Category{" +
                "objectId='" + objectId + '\'' +
                ", name='" + name + '\'' +
                ", foods=" + foods +
                '}';
    }
}
