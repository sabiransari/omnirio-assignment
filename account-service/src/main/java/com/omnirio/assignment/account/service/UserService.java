package com.omnirio.assignment.account.service;

import com.omnirio.assignment.account.model.User;

import reactor.core.publisher.Mono;

public interface UserService {
	public Mono<User> getUserById(String id, String token);

	public Mono<User> createUser(User newUser, String token);
}
