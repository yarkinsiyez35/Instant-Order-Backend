package com.sabanci.instantOrder.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class FoodOrderServe {

    @Id
    private String objectId;

    @Field("foodOrder")
    private FoodOrder foodOrder;

    @Field("served")
    private boolean served;

    public FoodOrderServe() {
    }

    public FoodOrderServe(FoodOrder foodOrder, boolean served) {
        this.foodOrder = foodOrder;
        this.served = served;
    }

    public String getObjectId() {
        return objectId;
    }

    public FoodOrder getFoodOrder() {
        return foodOrder;
    }

    public boolean isServed() {
        return served;
    }

    public void setFoodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
    }

    public void setServed(boolean served) {
        this.served = served;
    }

    @Override
    public String toString() {
        return "FoodOrderServe{" +
                "objectId='" + objectId + '\'' +
                ", foodOrder=" + foodOrder +
                ", served=" + served +
                '}';
    }
}
