package com.sabanci.instantOrder.repo;

import com.sabanci.instantOrder.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Optional<Employee> findEmployeeByEmployeeId(int empId);             //finds Employee by employeeId
    boolean existsEmployeeByEmployeeId(int empId);                      //returns true if an Employee has empId as employeeId
    boolean existsEmployeeByObjectId(String objectId);                  //returns true if an Employee has objectId as objectId
    boolean existsEmployeeByEmployeeIdAndPassword(int empId, String password);      //returns true if an Employee exists with empId and password
}
