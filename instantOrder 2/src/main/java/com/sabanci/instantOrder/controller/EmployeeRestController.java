package com.sabanci.instantOrder.controller;


import com.sabanci.instantOrder.model.Employee;
import com.sabanci.instantOrder.repo.EmployeeRepository;
import com.sabanci.instantOrder.service.EmployeeService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/instantOrder")
public class EmployeeRestController {

    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;

    @Autowired
    EmployeeRestController(EmployeeService employeeService1, EmployeeRepository employeeRepository1)
    {
        this.employeeService = employeeService1;
        this.employeeRepository = employeeRepository1;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees()
    {
        return employeeService.getEmployees();
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Object> getEmployee(@PathVariable int employeeId)
    {
        try
        {
            Employee searchedEmployee = employeeService.findEmployeeByEmployeeId(employeeId);
            return ResponseEntity.ok(searchedEmployee);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    @PostMapping("/employees/save")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee)
    {
        try
        {
            if (employeeService.addEmployee(employee) != null)
            {
                return ResponseEntity.ok(employee);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something is wrong");
            }
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Controller: " + e.getMessage());
        }
    }


    @PutMapping("/employees/save")
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee emp)
    {
        try
        {
            //Optional<Employee> searchedEmployee = employeeRepository.findById(name);
            Employee updatedEmployee = employeeService.updateEmployee(emp);
            return ResponseEntity.ok(updatedEmployee);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Controller: " + e.getMessage());
        }
    }


    @DeleteMapping("/employees/delete")
    public ResponseEntity<Object> deleteEmployee(@RequestBody String name)
    {
        try
        {
            Employee searchedEmployee = employeeService.findEmployeeByName(name);
            return ResponseEntity.ok(name);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + "controller!");
        }
    }
}
