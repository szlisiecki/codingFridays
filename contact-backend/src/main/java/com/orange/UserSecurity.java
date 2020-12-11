package com.orange;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {

	public boolean hasUserId(Authentication authentication, String userId, String role) {	
		// TODO sprawdz rolę USER
		return authentication.getName().equals(userId);
    }
}
