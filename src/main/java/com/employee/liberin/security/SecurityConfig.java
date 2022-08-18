package com.employee.liberin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {
	
	@Autowired
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;
	
//	@Autowired
//	   private UserDetailsService userDetailsService;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ 
        http
            .csrf()
            .disable()
            .cors()
            .disable()
            .authorizeHttpRequests()
            .antMatchers("/login").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
	
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
//
//    @Bean
//	public AuthenticationManagerBuilder authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
//	    return auth.authenticationManagerBuilder( userDetailsService);
//	}
//    
}