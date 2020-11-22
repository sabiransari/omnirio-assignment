package com.omnirio.assignment.user.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.omnirio.assignment.user.entities.User;
import com.omnirio.assignment.user.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> allUsers = userService.getAllCustomers();
		return ResponseEntity.ok(allUsers);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable String id) {
		User userById = userService.getUserById(id);
		if (userById == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(userById);
	}
	
	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody User user) throws URISyntaxException {
		@Valid
		User createdUser = userService.createUser(user);
		return ResponseEntity.created(new URI("/api/user/" + user.getUserId())).body(createdUser);
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		@Valid
		User updatedUser = userService.updateUser(user);
		return ResponseEntity.ok(updatedUser);
	}
}
