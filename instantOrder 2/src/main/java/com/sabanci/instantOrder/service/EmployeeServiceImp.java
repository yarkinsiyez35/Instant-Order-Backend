package com.sabanci.instantOrder.service;

import com.sabanci.instantOrder.model.Employee;
import com.sabanci.instantOrder.repo.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @PostConstruct
    public void init()
    {
        if(employeeRepository.count() == 0)         //dummy values will be inserted to database if database is empty
        {
            Employee employee1 = new Employee(1,"password", "Bob", "Bring");
            Employee employee2 = new Employee(2,"pswrd", "Charles", "Leave");
            Employee employee3 = new Employee(3,"pswrd123", "Aaron", "Take");
            Employee employee4 = new Employee(4,"strongpassword","Kevin", "Check");
            List<Employee> employees = new ArrayList<>();
            employees.add(employee1);
            employees.add(employee2);
            employees.add(employee3);
            employees.add(employee4);
            employeeRepository.insert(employees);
        }
    }

    @Override
    public List<Employee> getEmployees()
    {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findEmployeeByEmployeeId(int employeeId)
    {       //returns the Employee if it exists, throws exception otherwise
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
    public Employee findEmployeeByObjectId(String objectId)
    {       //returns Employee if it exists, throws exception otherwise
        Optional<Employee> searchedEmployee = employeeRepository.findById(objectId);
        if(searchedEmployee.isPresent())
        {
           return searchedEmployee.get();
        }
        else
        {
            throw new RuntimeException("Employee with objectId: " + objectId + " does not exist!");
        }
    }

    @Override
    public Employee addEmployee(Employee employee)
    {   //adds Employee if it has a unique employeeId, throws exception otherwise

        if(employee.hasNull()) //protection against empty bodies
        {
            throw new RuntimeException("Employee with ID: " + employee.getEmployeeId() + " has null values!");
        }
        if(employeeRepository.existsEmployeeByEmployeeId(employee.getEmployeeId()))         //protection against inserting an employee with same employeeId
        {
            throw new RuntimeException("Employee with ID: "+ employee.getEmployeeId() + " already exists!");
        }
        return employeeRepository.insert(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee)
    {   //updates Employee with existing objectId, throws exception otherwise
        if(employee.hasNull()) //protection against empty bodies
        {
            throw new RuntimeException("Employee with ID: " + employee.getEmployeeId() + " has null values!");
        }
        if(employeeRepository.existsEmployeeByObjectId(employee.getObjectId()))
        {
            return employeeRepository.save(employee);
        }
        else
        {
            throw new RuntimeException("Employee with objectID: " + employee.getObjectId() + " cannot be updated!");
        }
    }

    @Override
    public Employee deleteEmployee(Employee employee)
    {   //deletes Employee with existing objectId, throws exception otherwise
        Optional<Employee> toBeDeletedEmployee = employeeRepository.findById(employee.getObjectId());
        if(toBeDeletedEmployee.isPresent())
        {
            employeeRepository.delete(employee);
            return toBeDeletedEmployee.get();
        }
        else
        {
            throw new RuntimeException(("Employee with objectId: " + employee.getObjectId() + " cannot be deleted!"));
        }
    }

    @Override
    public boolean existsEmployeeByEmployeeIdAndPassword(int employeeId, String password) {
        return employeeRepository.existsEmployeeByEmployeeIdAndPassword(employeeId,password);
    }
}
