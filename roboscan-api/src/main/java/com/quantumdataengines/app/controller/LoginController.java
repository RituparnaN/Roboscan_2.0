package com.quantumdataengines.app.controller;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quantumdataengines.app.annotation.ApplicationModule;
import com.quantumdataengines.app.dto.TokenDTO;
import com.quantumdataengines.app.service.authentication.AuthenticationService;


@RestController
@RequestMapping(value = "/public")
@ApplicationModule(name = "Login Access", description = "Login Access Module", isLinkEnabled = false)
@CrossOrigin(allowedHeaders="*", origins="*")
public class LoginController {
	
	private static final String BEARER = "Bearer";
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/login")
	@ApplicationModule(name = "Login", description = "Log In to the application", isDefaultModule = true)
	public ResponseEntity<TokenDTO> login(@RequestParam("username") final String username, 
    		@RequestParam("password") final String password,
    		HttpServletRequest request) {
        String token = authenticationService.authenticate(username, password, request.getRemoteAddr());
        //System.out.println("token = "+token);
        TokenDTO token1 =new TokenDTO(token);
        return ResponseEntity.ok(token1);
    }
	
	@GetMapping("/isAlived")
    public ResponseEntity<Boolean> isAlived(HttpServletRequest request) {
    	final String authorizationHeader = ofNullable(request.getHeader(AUTHORIZATION)).get();
        final Optional<String> tokenOptional = ofNullable(authorizationHeader)
                .map(value -> removeStart(value, BEARER))
                .map(String::trim);
        
        return tokenOptional.isPresent() ? ResponseEntity.ok(authenticationService.isAlived(tokenOptional.get())) : ResponseEntity.ok(false);
    }
	
}
