package com.omnirio.assignment.user.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	CUSTOMER, BRANCH_MANAGER;

	@Override
	public String getAuthority() {
		return "ROLE_" + name();
	}
}
