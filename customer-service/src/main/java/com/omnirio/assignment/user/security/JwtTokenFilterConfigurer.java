package com.omnirio.assignment.user.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	private JwtTokenHandler jwtTokenHandler;
	
	public JwtTokenFilterConfigurer(JwtTokenHandler jwtTokenHandler) {
		this.jwtTokenHandler = jwtTokenHandler;
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenHandler);
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
