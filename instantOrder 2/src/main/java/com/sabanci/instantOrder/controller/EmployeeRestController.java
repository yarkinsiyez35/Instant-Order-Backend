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

    @Autowired
    EmployeeRestController(EmployeeService employeeService1)
    {
        this.employeeService = employeeService1;
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
            Employee addedEmployee = employeeService.addEmployee(employee);
            return ResponseEntity.status(HttpStatus.OK).body(addedEmployee);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PutMapping("/employees/save")
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee emp)
    {
        try
        {
            Employee updatedEmployee = employeeService.updateEmployee(emp);
            return ResponseEntity.ok(updatedEmployee);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/employees/delete")
    public ResponseEntity<Object> deleteEmployee(@RequestBody Employee employee)
    {
        try
        {
            Employee deletedEmployee = employeeService.deleteEmployee(employee);
            return ResponseEntity.ok(deletedEmployee);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
