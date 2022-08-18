package com.quantumdataengines.app.filter;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.quantumdataengines.app.model.User;
import com.quantumdataengines.app.service.authentication.AuthenticationService;


public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String BEARER = "Bearer";
    private AuthenticationService authenticationService;

    public TokenAuthenticationFilter(final RequestMatcher requiresAuth, AuthenticationService authenticationService) {
    	super(requiresAuth);
        this.setAllowSessionCreation(false);
        this.authenticationService = authenticationService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    	final String authorizationHeader = ofNullable(request.getHeader(AUTHORIZATION)).orElse(request.getParameter("token"));
        final String token = Optional.ofNullable(authorizationHeader)
                .map(value -> removeStart(value, BEARER))
                .map(String::trim) 
                .orElseThrow(() -> new BadCredentialsException("Missing Authentication Token"));
        
        
        String terminal = request.getRemoteAddr();
        User user = authenticationService.validateToken(token, terminal);
        
        if(user == null)
        	throw new BadCredentialsException("Invalid Token");
        
        authenticationService.isAuthenticated(token);
        
        return new UsernamePasswordAuthenticationToken(user, token, new ArrayList<GrantedAuthority>());
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
