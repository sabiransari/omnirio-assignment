package com.omnirio.assignment.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omnirio.assignment.account.entities.Account;
import com.omnirio.assignment.account.enums.Role;
import com.omnirio.assignment.account.exception.ResourceNotFoundException;
import com.omnirio.assignment.account.model.User;

@Service
public class AccountService {
	
	@Autowired
	private UserService userService;

	@Autowired
	private AccountRepository accountRepository;

	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	public Account getAccountById(String lookupId) {
		return accountRepository.findById(lookupId).orElse(null);
	}

	public Account createAccount(Account account, String token) {
		User user = null;
		try {
			user = userService.getUserById(account.getCustomerId(), token).block();
		} catch (ResourceNotFoundException e) {
			
		}
		if (user == null) {
			User newUser = new User();
			newUser.setUserId(account.getCustomerId());
			newUser.setUserName(account.getCustomerName());
			newUser.setGender('M');
			newUser.setRole(Role.CUSTOMER);
			userService.createUser(newUser, token).block();
		}
		accountRepository.saveAndFlush(account);
		return account;
	}

	public Account updateAccount(Account account, String token) {
		try {
			userService.getUserById(account.getCustomerId(), token).block();
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		accountRepository.saveAndFlush(account);
		return account;
	}

}
