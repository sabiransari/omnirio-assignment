package com.omnirio.assignment.user.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenFilter extends OncePerRequestFilter {
	
	private JwtTokenHandler jwtTokenHandler;
	
	public JwtTokenFilter(JwtTokenHandler jwtTokenHandler) {
		this.jwtTokenHandler = jwtTokenHandler;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = jwtTokenHandler.resolveToken(request);
		try {
			if (token != null && jwtTokenHandler.validateToken(token)) {
				Authentication authentication = jwtTokenHandler.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpStatus.FORBIDDEN.value(), "User not authorized");
		}
		filterChain.doFilter(request, response);
	}

}
