package com.gaurav.dynamodbemployee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.dynamodbemployee.model.employee.Employee;
import com.gaurav.dynamodbemployee.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	@PostMapping("/")
	public Employee createEmployee(Employee employee) {
		return service.saveEmployee(employee);
	}

	@GetMapping("/")
	public List<Employee> getEmployees() {
		return service.getAllEmployees();
	}
}
