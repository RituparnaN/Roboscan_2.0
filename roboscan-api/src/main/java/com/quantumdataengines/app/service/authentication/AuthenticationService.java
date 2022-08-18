package com.quantumdataengines.app.service.authentication;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import com.quantumdataengines.app.model.DomainUser;
import com.quantumdataengines.app.model.User;

public interface AuthenticationService {
	public boolean isAlived(String token);
	public Optional<UserDetails> isAuthenticated(String token);
	public User validateToken(String token, String terminal);
	public String authenticate(String username, String password, String ipAddress);
	public String logout(DomainUser domainUser);
	public void autoLogout();
}