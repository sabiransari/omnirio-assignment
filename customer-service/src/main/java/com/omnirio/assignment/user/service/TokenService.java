package com.omnirio.assignment.user.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omnirio.assignment.user.entities.User;
import com.omnirio.assignment.user.repository.UserRepository;
import com.omnirio.assignment.user.security.JwtTokenHandler;

@Service
public class TokenService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenHandler jwtTokenHandler;

	public String getToken(String userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			throw new RuntimeException("UserId: " + userId + " not found");
		}
		return jwtTokenHandler.createToken(userId, Collections.singletonList(user.get().getRole()));
	}
}
