package com.employee.liberin.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.liberin.dto.JWTAuthRequest;
import com.employee.liberin.dto.JWTAuthResponse;
import com.employee.liberin.security.JWTTokenHelper;
import com.employee.liberin.security.JWTUserDetailService;

@RestController
public class AuthController {
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	
	@Autowired
	private JWTUserDetailService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping(value="/login")
	public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest request){
		
		authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails=userDetailsService.loadUserByUsername(request.getUsername());
		String token =jwtTokenHelper.generateJwtToken(userDetails);
		JWTAuthResponse response=new JWTAuthResponse();
		response.setToken(token);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(
				username, password);
		try {
		authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		}catch(Exception e) {
			System.out.println("AuthController.authenticate()");
		}
	}
	

}
