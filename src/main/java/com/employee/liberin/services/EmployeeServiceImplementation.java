package com.employee.liberin.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.liberin.entity.Employee;
import com.employee.liberin.repositories.EmployeeRepo;

@Service
public class EmployeeServiceImplementation implements EmployeeService {
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Override
	public Employee saveEmployee(Employee employee) {
		String firstName=employee.getFirstName();
		String lastName=employee.getLastName();
		String designation=employee.getDesignation();
		String email=null;
		String password=generateRandomPassword(8);
		employee.setPassword(password);
		if(designation.isEmpty()) {
			email=firstName+"."+lastName+"@"+"liberintechnologies.com";
		}else {
			email=firstName+"."+lastName+"@"+designation+".liberintechnologies.com";
		}
		String e=email.replaceAll("\\s", "");
		String d=e.toLowerCase();
		employee.setEmail(d);
		return employeeRepo.save(employee);
	}
	
	public String generateRandomPassword(int len) {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi"
          +"jklmnopqrstuvwxyz!@#$%&";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
	}

}
