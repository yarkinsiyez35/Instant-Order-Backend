package com.sabanci.instantOrder.model;

public class UpdateMessage {
    private boolean update;

    public UpdateMessage(){}

    public UpdateMessage(boolean paid) {
        this.update = paid;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
