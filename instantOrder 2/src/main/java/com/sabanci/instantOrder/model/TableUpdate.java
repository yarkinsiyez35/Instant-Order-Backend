package com.sabanci.instantOrder.model;

public class TableUpdate {
    private boolean paid;

    public TableUpdate(){}

    public TableUpdate(boolean paid) {
        this.paid = paid;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
