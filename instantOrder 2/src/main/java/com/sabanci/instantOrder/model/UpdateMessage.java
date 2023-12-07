package com.sabanci.instantOrder.model;


//this class is used to send update messages in PUT request
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
