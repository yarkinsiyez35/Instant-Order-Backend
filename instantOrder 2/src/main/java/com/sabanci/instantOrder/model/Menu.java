package com.sabanci.instantOrder.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Objects;

@Document
public class Menu {
    @Id
    private String objectId;
    @Field("categories")
    @DBRef
    private List<Category> categories;

    public Menu() {
    }

    public Menu(List<Category> categories) {
        this.categories = categories;
    }

    public String getObjectId() {
        return objectId;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category)
    {
        this.categories.add(category);
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(objectId, menu.objectId) && Objects.equals(categories, menu.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId, categories);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "objectId='" + objectId + '\'' +
                ", categories=" + categories +
                '}';
    }
}

