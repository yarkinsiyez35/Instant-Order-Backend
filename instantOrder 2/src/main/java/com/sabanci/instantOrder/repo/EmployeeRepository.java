package com.sabanci.instantOrder.repo;

import com.sabanci.instantOrder.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    
}
