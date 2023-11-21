package com.sabanci.instantOrder.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class FoodOrder {
    @Id
    @Field("_id")
    private String objectId;
    @DBRef
    private Food food;
   @Field("tableId")
    private int tableId;

   @Field("count")
   private int count;

   @Field("note")
   private String note;
   @Field("cookingStatus")
   private boolean cookingStatus;

    public FoodOrder() {
    }

    public FoodOrder(Food food, int tableId, int count, String note, boolean cookingStatus) {
        this.food = food;
        this.tableId = tableId;
        this.count = count;
        this.note = note;
        this.cookingStatus = cookingStatus;
    }

    public String getObjectId() {
        return objectId;
    }

    public Food getFood() {
        return food;
    }

    public int getTableId() {
        return tableId;
    }

    public int getCount() {
        return count;
    }

    public String getNote() {
        return note;
    }

    public boolean isCookingStatus() {
        return cookingStatus;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCookingStatus(boolean cookingStatus) {
        this.cookingStatus = cookingStatus;
    }
}

