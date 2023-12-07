package com.sabanci.instantOrder.model;

//this class is used in login page
public class EmployeeLogin {
    private int employeeId;
    private String password;

    public EmployeeLogin() {
    }

    public EmployeeLogin(int employeeId, String password) {
        this.employeeId = employeeId;
        this.password = password;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
