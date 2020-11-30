package com.omnirio.assignment.account.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

@Configuration
public class AccountServiceConfig {

	@Bean
	@LoadBalanced
	public Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}
}
