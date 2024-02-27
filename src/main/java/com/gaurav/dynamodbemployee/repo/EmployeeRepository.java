package com.gaurav.dynamodbemployee.repo;

import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.springframework.stereotype.Repository;

import com.gaurav.dynamodbemployee.model.employee.Employee;

@Repository
public interface EmployeeRepository extends DynamoDBCrudRepository<Employee, String> {
}