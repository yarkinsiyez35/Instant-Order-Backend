package com.sabanci.instantOrder.service;

import com.sabanci.instantOrder.model.Employee;
import com.sabanci.instantOrder.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.sql.DataTruncation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImp implements EmployeeService{

    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeServiceImp(EmployeeRepository employeeRepository1)
    {
        this.employeeRepository = employeeRepository1;
    }

    @Override
    public List<Employee> getEmployees()
    {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findEmployeeByEmployeeId(int employeeId)
    {
        Optional<Employee> searchedEmployee = employeeRepository.findEmployeeByEmployeeId(employeeId);
        if (searchedEmployee.isPresent())
        {
            return searchedEmployee.get();
        }
        else
        {
            throw new RuntimeException("Employee with ID: " + employeeId + " does not exist!");
        }
    }




    @Override
    public Employee addEmployee(Employee employee) {

        if(employeeRepository.existsEmployeeByEmployeeId(employee.getEmployeeId()))         //protection against inserting an employee with same employeeId
        {
            throw new RuntimeException("Service: Employee with ID: "+ employee.getEmployeeId() + " already exists!");
        }
        return employeeRepository.insert(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {


        if(employeeRepository.existsEmployeeByObjectId(employee.getObjectId()))
        {
            return employeeRepository.save(employee);
        }
        else
        {
            throw new RuntimeException("No update");
        }
    }


    @Override
    public Employee findEmployeeByName(String name) {
        Optional<Employee> employee = employeeRepository.findEmployeeByFirstName(name);
        if(employee.isPresent())
        {
            return employee.get();
        }
        else
        {
            throw new RuntimeException("Service: Employee with firstName: " + name+ " does not exist!");
        }
    }

}
