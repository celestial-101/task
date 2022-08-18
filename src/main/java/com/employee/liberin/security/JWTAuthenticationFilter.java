package com.employee.liberin.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JWTUserDetailService userDetailsService;
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestToken=request.getHeader("Authorization");
		String username=null;
		String token=null;
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			token=requestToken.substring(7);
			try {
			username=jwtTokenHelper.getUsernameFromToken(token);
			}catch(IllegalArgumentException e) {
				System.out.println("Unable to get JWT token ");
			}catch(ExpiredJwtException e) {
				System.out.println("Token has been expired");
			}catch(MalformedJwtException e) {
				System.out.println("Invalid JWT");
			}
			
		}else {
			System.out.println("JWT token does not begin with 'Bearer' ");
		}
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails=userDetailsService.loadUserByUsername(username);
			if(jwtTokenHelper.validateJwtToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}else {
				System.err.println("Invalid JWT token ");
			}
		}else {
			System.out.println("Username is null or context is not null");
		}
		filterChain.doFilter(request, response);
		
	}

}
