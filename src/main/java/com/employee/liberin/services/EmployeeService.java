package com.employee.liberin.services;

import java.util.List;

import com.employee.liberin.dto.EmployeeDto;

public interface EmployeeService {

	EmployeeDto saveEmployee(EmployeeDto employee);

	EmployeeDto findEmployee(long id);

	List<EmployeeDto> findallEmployees();

}
