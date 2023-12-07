package com.sabanci.instantOrder.service;


import com.sabanci.instantOrder.model.Employee;
import java.util.List;

public interface EmployeeService
{
    List<Employee> getEmployees();
    Employee findEmployeeByEmployeeId(int employeeId);
    Employee addEmployee(Employee employee);
    Employee updateEmployee(Employee employee);
    Employee deleteEmployee(Employee employee);
    boolean existsEmployeeByEmployeeIdAndPassword(int employeeId, String password);
}
