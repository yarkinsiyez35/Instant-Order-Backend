package com.sabanci.instantOrder.controller;


import com.sabanci.instantOrder.model.Employee;
import com.sabanci.instantOrder.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/instantOrder")
public class EmployeeRestController {



    private final EmployeeRepository employeeRepository;

    @Autowired
    EmployeeRestController(EmployeeRepository employeeRepository1){
        this.employeeRepository = employeeRepository1;
    }
    @GetMapping("/employees")
    public List<Employee> getEmployees()
    {
        return employeeRepository.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId)
    {
        Optional<Employee> employee = employeeRepository.findEmployeeByEmployeeId(employeeId);
        if (employee.isPresent())
        {
            return  employee.get();
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with ID: " + employeeId);
        }
    }

    @PostMapping("/employees/save")
    public Employee saveEmployee(@RequestBody Employee employee)
    {
        return employeeRepository.save(employee);
    }
}
