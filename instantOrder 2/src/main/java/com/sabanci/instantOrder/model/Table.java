package com.sabanci.instantOrder.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Objects;

@Document
public class Table {
    @Id
    private String objectId;
    @Field("tableId")
    private int tableId;

    @Field("employeeId")
    private int employeeId;
    @Field("foodOrders")
    @DBRef
    private List<FoodOrder> foodOrders;
    @Field("paid")
    private boolean paymentReceived;

    public Table(int tableId, int employeeId, List<FoodOrder> foodOrders, boolean paymentReceived) {
        this.tableId = tableId;
        this.employeeId = employeeId;
        this.foodOrders = foodOrders;
        this.paymentReceived = paymentReceived;
    }

    public Table(int tableId) {
        this.tableId = tableId;
    }

    public String getObjectId() {
        return objectId;
    }

    public int getTableId() {
        return tableId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public List<FoodOrder> getFoodOrders() {
        return foodOrders;
    }

    public boolean isPaymentReceived() {
        return paymentReceived;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setFoodOrders(List<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
    }

    public void setPaymentReceived(boolean paymentReceived) {
        this.paymentReceived = paymentReceived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return tableId == table.tableId && employeeId == table.employeeId && paymentReceived == table.paymentReceived && Objects.equals(objectId, table.objectId) && Objects.equals(foodOrders, table.foodOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId, tableId, employeeId, foodOrders, paymentReceived);
    }

    @Override
    public String toString() {
        return "Table{" +
                "objectId='" + objectId + '\'' +
                ", tableId=" + tableId +
                ", employeeId=" + employeeId +
                ", foodOrders=" + foodOrders +
                ", paymentReceived=" + paymentReceived +
                '}';
    }
}
