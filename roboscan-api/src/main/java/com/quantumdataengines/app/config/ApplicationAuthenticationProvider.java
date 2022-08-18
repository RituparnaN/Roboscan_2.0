package com.quantumdataengines.app.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.quantumdataengines.app.service.authentication.AuthenticationService;

@Component
public class ApplicationAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{
	
	@Autowired
	private AuthenticationService authenticationService;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		Object token = authentication.getCredentials();
		return Optional
					.ofNullable(token)
					.map(i -> String.valueOf(i))
					.flatMap(t -> authenticationService.isAuthenticated(t))
					.orElseThrow(()-> new RuntimeException("token not provided"));
	}
}
