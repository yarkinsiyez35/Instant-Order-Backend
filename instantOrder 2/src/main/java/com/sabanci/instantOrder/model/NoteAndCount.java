package com.sabanci.instantOrder.model;


//this class will be used to get Note and Count for creating FoodOrder and FoodOrderTable through TableRestController
public class NoteAndCount {
    private String note;
    private int count;

    public NoteAndCount() {
    }

    public NoteAndCount(String note, int count) {
        this.note = note;
        this.count = count;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
