package com.sabanci.instantOrder.controller;

import com.sabanci.instantOrder.model.EmployeeLogin;
import com.sabanci.instantOrder.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("instantOrder")
public class LoginRestController {
    //this controller is responsible for
    //@POST EmployeeLogin --> returns the given json if password and employeeId is correct
    EmployeeService employeeService;

    @Autowired
    LoginRestController(EmployeeService employeeService1)
    {
        this.employeeService = employeeService1;
    }

    @PostMapping("/login")
    ResponseEntity<Object> login(@RequestBody EmployeeLogin employeeLogin)
    {
        if(employeeService.existsEmployeeByEmployeeIdAndPassword(employeeLogin.getEmployeeId(), employeeLogin.getPassword()))
        {
            return ResponseEntity.ok(employeeLogin);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EmployeeId or password is wrong!");
        }
    }
}
