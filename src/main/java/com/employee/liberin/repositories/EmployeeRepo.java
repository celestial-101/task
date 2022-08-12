package com.employee.liberin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.liberin.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

}
