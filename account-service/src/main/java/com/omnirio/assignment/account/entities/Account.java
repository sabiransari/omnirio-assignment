package com.omnirio.assignment.account.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Entity
public class Account {
	@Id
	private String accountId;
	private String accountType;
	private Date openDate;
	@Column(nullable = false)
	private String customerId;
	private String customerName;
	private String branch;
	private Character minorIndicator;
	
	@PrePersist
	public void set() {
		this.openDate = new Date();
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Character getMinorIndicator() {
		return minorIndicator;
	}

	public void setMinorIndicator(Character minorIndicator) {
		this.minorIndicator = minorIndicator;
	}
}
