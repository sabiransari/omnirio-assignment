package com.omnirio.assignment.account.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnirio.assignment.account.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
	
}
