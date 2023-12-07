package com.sabanci.instantOrder.model;


//this class is used to get EmployeeId, Note and  Count for creating FoodOrder and FoodTable through TableRestController
public class NoteAndCount {
    private int employeeId;
    private String note;
    private int count;

    public NoteAndCount() {
    }

    public NoteAndCount(int employeeId, String note, int count) {
        this.employeeId = employeeId;
        this.note = note;
        this.count = count;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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
