package com.sabanci.instantOrder.repo;

import com.sabanci.instantOrder.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Optional<Employee> findEmployeeByObjectId(String objectId);
    Optional<Employee> findEmployeeByEmployeeId(int empId);
    List<Employee> findEmployeesByFirstNameIgnoreCase(String objectId);
    Optional<Employee> findEmployeeByFirstName(String name);

    List<Employee> findEmployeesByLastNameIgnoreCase(String objectId);
    boolean existsEmployeeByEmployeeId(int empId);
    boolean existsEmployeeByObjectId(String objectId);
    boolean existsEmployeeByEmployeeIdAndPassword(int empId, String password);

}
