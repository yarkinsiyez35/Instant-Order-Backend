package com.sabanci.instantOrder.service;


import com.sabanci.instantOrder.model.Employee;
import java.util.List;

public interface EmployeeService
{
    //Employee Service will not return Optional<Employee>, instead it will throw a RuntimeException if Employee is not found

    List<Employee> getEmployees();                                      //returns every employee
    Employee findEmployeeByEmployeeId(int employeeId);                  //finds Employee by employeeId
    Employee findEmployeeByObjectId(String objectId);                   //finds Employee by objectId
    Employee addEmployee(Employee employee);                            //adds one Employee
    List<Employee> addEmployees(List<Employee> employees);              //adds a list of Employees
    Employee updateEmployee(Employee employee);                         //updates an existing Employee
    Employee deleteEmployee(Employee employee);                         //deletes an Employee
}
