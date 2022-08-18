package com.employee.liberin.restcontroller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.employee.liberin.dto.EmployeeDto;
import com.employee.liberin.services.EmployeeService;

@RestController
@RequestMapping(value="/employees")
public class EmployeeRestController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping
    public ResponseEntity<EmployeeDto> addProduct(@RequestBody EmployeeDto employeeDto) {
		
		employeeDto=employeeService.saveEmployee(employeeDto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		        .buildAndExpand(employeeDto.getId()).toUri();
	     return	ResponseEntity.created(location).build();
		
    }
	
	@GetMapping
	public CollectionModel<EmployeeDto> retrieveAllEmployees(){
		List<EmployeeDto> employees=employeeService.findallEmployees();
		for (EmployeeDto ee : employees) {
	        ee.add(linkTo(methodOn(EmployeeRestController.class).findEmployee(ee.getId())).withSelfRel());
	    }
	     
	    CollectionModel<EmployeeDto> collectionModel = CollectionModel.of(employees);
	     
	    collectionModel.add(linkTo(methodOn(EmployeeRestController.class)).withSelfRel());
	     
	    return collectionModel;		
	}
	
	@GetMapping(value="/{employeeId}")
	public EntityModel<EmployeeDto> findEmployee(@PathVariable ("employeeId") Long id){
		EmployeeDto employee=employeeService.findEmployee(id);
		EntityModel<EmployeeDto> resource = EntityModel.of(employee);
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllEmployees());
		resource.add(linkTo.withRel("self"));
		return resource;
	}

}
