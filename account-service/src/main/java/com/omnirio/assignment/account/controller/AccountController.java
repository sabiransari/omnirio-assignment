package com.omnirio.assignment.account.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.omnirio.assignment.account.entities.Account;
import com.omnirio.assignment.account.service.AccountService;

@RestController
public class AccountController {
	
	@Autowired
	private AccountService accountService;

	@GetMapping("/accounts")
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> allAccounts = accountService.getAllAccounts();
		return ResponseEntity.ok(allAccounts);
	}
	
	@GetMapping("/account/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable String id) {
		Account accountById = accountService.getAccountById(id);
		if (accountById == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(accountById);
	}
	
	@PostMapping("/account")
	public ResponseEntity<Account> createAccount(@RequestBody Account account, @RequestHeader("Authorization") String token) throws URISyntaxException {
		Account createdAccount = accountService.createAccount(account, token);
		if (createdAccount != null) {
			return ResponseEntity.created(new URI("/account/" + createdAccount.getAccountId()))
					.body(createdAccount);
		}
		return null;
	}
	
	@PutMapping("/account/{id}")
	public ResponseEntity<Account> updateAccount(@RequestBody Account account, @RequestHeader("Authorization") String token) {
		Account updatedAccount = accountService.updateAccount(account, token);
		return ResponseEntity.ok(updatedAccount);
		
	}
}
