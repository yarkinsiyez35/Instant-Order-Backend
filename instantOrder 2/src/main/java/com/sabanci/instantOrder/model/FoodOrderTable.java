package com.sabanci.instantOrder.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class FoodOrderTable {
    @Field("food")
    @DBRef
    private Food food;

    @Field("count")
    private int count;

    @Field("tableId")
    private int tableId;

    public FoodOrderTable(Food food, int count, int tableId) {
        this.food = food;
        this.count = count;
        this.tableId = tableId;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }
}
