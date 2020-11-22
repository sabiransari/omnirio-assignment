package com.omnirio.assignment.user.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.omnirio.assignment.user.entities.User;
import com.omnirio.assignment.user.enums.Role;
import com.omnirio.assignment.user.exception.ResourceAlreadyExists;
import com.omnirio.assignment.user.exception.ResourceNotFoundException;
import com.omnirio.assignment.user.repository.UserRepository;

@Service
@Validated
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	public void init() {
		// create super user root
		// we must at least have one user with branch manager role for testing
		if(userRepository.findById("root").isEmpty()) {
			User superUser = new User();
			superUser.setUserId("root");
			superUser.setUserName("root");
			superUser.setGender('M');
			superUser.setRole(Role.BRANCH_MANAGER);
			
			userRepository.saveAndFlush(superUser);
			
		}
	}

	@PreAuthorize("hasRole('BRANCH_MANAGER')")
	public List<User> getAllCustomers() {
		return userRepository.findByRole(Role.CUSTOMER);
	}

	@PreAuthorize("hasRole('BRANCH_MANAGER') OR #lookupId == authentication.principal.username")
	public User getUserById(String lookupId) {
		return userRepository.findById(lookupId).orElse(null);
	}

	@Validated
	@PreAuthorize("hasRole('BRANCH_MANAGER')")
	public @Valid User createUser(@Valid User user) {
		userRepository.findById(user.getUserId()).ifPresentOrElse(existingUser -> {
			throw new ResourceAlreadyExists("User already exists");
		}, () -> {
			userRepository.saveAndFlush(user);
		});
		return user;
	}

	@Validated
	@PreAuthorize("hasRole('BRANCH_MANAGER')")
	public @Valid User updateUser(@Valid User user) {
		userRepository.findById(user.getUserId()).ifPresentOrElse(existingUser -> {
			userRepository.saveAndFlush(user);
		}, () -> {
			throw new ResourceNotFoundException("User not found");
		});
		return user;
	}

}
