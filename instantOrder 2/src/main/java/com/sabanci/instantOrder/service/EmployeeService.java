package com.sabanci.instantOrder.service;


import com.sabanci.instantOrder.model.Employee;
import java.util.List;

public interface EmployeeService
{
    //Employee Service will not return Optional<Employee>, instead it will throw a RuntimeException if Employee is not found

    List<Employee> getEmployees();                                      //returns every employee
    Employee findEmployeeByEmployeeId(int employeeId);                  //finds Employee by employeeId
    Employee addEmployee(Employee employee);                            //adds one Employee
    Employee updateEmployee(Employee employee);                         //updates an existing Employee
    Employee deleteEmployee(Employee employee);                         //deletes an Employee

    boolean existsEmployeeByEmployeeIdAndPassword(int employeeId, String password); //used in login
}
