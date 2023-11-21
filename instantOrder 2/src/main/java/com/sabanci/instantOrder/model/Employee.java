package com.sabanci.instantOrder.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Document
public class Employee {

    @Id
    private String objectId;
    @Field("employeeId")
    private int employeeId;
    @Field("password")
    private String password;
    @Field("firstName")
    private String firstName;
    @Field("lastName")
    private String lastName;

    public Employee() {
    }

    public Employee(int employeeId, String password, String firstName, String lastName) {
        this.employeeId = employeeId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId && Objects.equals(objectId, employee.objectId) && Objects.equals(password, employee.password) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId, employeeId, password, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "objectId='" + objectId + '\'' +
                ", employeeId=" + employeeId +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
