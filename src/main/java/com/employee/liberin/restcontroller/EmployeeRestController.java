package com.employee.liberin.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.liberin.entity.Employee;
import com.employee.liberin.services.EmployeeService;

@RestController
public class EmployeeRestController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("/addEmployee")
    public ResponseEntity<Employee> addProduct(@RequestBody Employee employee) {
		
		employee=employeeService.saveEmployee(employee);
		return new ResponseEntity<>(employee,HttpStatus.OK);
    }

}
