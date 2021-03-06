package com.omnirio.assignment.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.omnirio.assignment.account.exception.ResourceNotFoundException;
import com.omnirio.assignment.account.model.User;

import reactor.core.publisher.Mono;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Override
	public Mono<User> getUserById(String id, String token) {
		return webClientBuilder.build().get().uri("http://user-service/user/{id}", id)
				.header("Authorization", token)
				.retrieve()
				.onStatus(status -> HttpStatus.NOT_FOUND.equals(status), response -> {
					return Mono.error(new ResourceNotFoundException("User not found"));
				})
				.bodyToMono(User.class);
	}

	@Override
	public Mono<User> createUser(User newUser, String token) {
		return webClientBuilder.build().post().uri("http://user-service/user")
		.header("Authorization", token)
		.body(Mono.just(newUser), User.class)
		.retrieve()
		.onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new AccessDeniedException("")))
		.onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new RuntimeException("Something went wrong")))
		.bodyToMono(User.class);
	}

}
