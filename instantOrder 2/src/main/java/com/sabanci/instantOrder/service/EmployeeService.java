package com.sabanci.instantOrder.service;


import com.sabanci.instantOrder.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface EmployeeService {

    List<Employee> getEmployees();                                      //returns every employee
    Employee findEmployeeByEmployeeId(int employeeId);        //finds Employee by employeeId
             //finds Employee by ObjectId

    Employee addEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    Employee findEmployeeByName(String name);





}
