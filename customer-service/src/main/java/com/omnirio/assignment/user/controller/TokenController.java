package com.omnirio.assignment.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omnirio.assignment.user.service.TokenService;

@RestController
public class TokenController {
	
	@Autowired
	private TokenService tokenService;

	@PostMapping("/token")
	public ResponseEntity<String> getToken(@RequestParam String userId) {
		String token = tokenService.getToken(userId);
		return ResponseEntity.ok(token);
	}
}
