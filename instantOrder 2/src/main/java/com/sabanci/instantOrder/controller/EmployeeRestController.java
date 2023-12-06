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
    //this controller is responsible for
    //@Get List<Employee> -->returns List<Employee>
    //@Get Employee -->returns the requested Employee
    //@Post Employee -->creates a new Employee with given Employee
    //@Put Employee  -->updates an existing Employee with given Employee
    //@Delete Employee  -->deletes an existing Employee

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
            //find the Employee
            Employee searchedEmployee = employeeService.findEmployeeByEmployeeId(employeeId);
            //return the Employee
            return ResponseEntity.ok(searchedEmployee);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/employees/{employeeId}")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee, @PathVariable int employeeId)
    {
        try
        {
            //prevention against updating an existing Employee with nonexistent employeeId
            employee.setEmployeeId(employeeId);
            //add the Employee
            Employee addedEmployee = employeeService.addEmployee(employee);
            //return the Employee
            return ResponseEntity.status(HttpStatus.OK).body(addedEmployee);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
        }
    }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee emp, @PathVariable int employeeId)
    {
        try
        {
            //check if the Employee exists with that employeeId
            Employee toBeUpdatedEmployee = employeeService.findEmployeeByEmployeeId(employeeId);
            //these assignments prevent changing the objectId
            toBeUpdatedEmployee.setFirstName(emp.getFirstName());
            toBeUpdatedEmployee.setLastName(emp.getLastName());
            toBeUpdatedEmployee.setPassword(emp.getPassword());
            //update the Employee
            Employee updatedEmployee = employeeService.updateEmployee(toBeUpdatedEmployee);
            //return the Employee
            return ResponseEntity.ok(updatedEmployee);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
        }
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable int employeeId)
    {
        try
        {
            //find the Employee
            Employee toBeDeletedEmployee = employeeService.findEmployeeByEmployeeId(employeeId);
            //delete the Employee
            Employee deletedEmployee = employeeService.deleteEmployee(toBeDeletedEmployee);
            //return the Employee
            return ResponseEntity.ok(deletedEmployee);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
