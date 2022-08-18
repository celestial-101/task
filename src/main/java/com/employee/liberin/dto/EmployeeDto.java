package com.employee.liberin.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto extends RepresentationModel<EmployeeDto>{
	
	private long id;
	private String firstName;
	private String lastName;
	private String designation;
	private String phoneNumber;
    private String email;
}
