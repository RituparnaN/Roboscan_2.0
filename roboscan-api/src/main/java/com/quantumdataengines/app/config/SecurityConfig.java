package com.quantumdataengines.app.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.quantumdataengines.app.service.authentication.AuthenticationService;

import com.quantumdataengines.app.filter.TokenAuthenticationFilter;
//import com.quantumdataengines.app.cognifi.service.authentication.AuthenticationService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private static final RequestMatcher SECUREDURLS = new OrRequestMatcher(new AntPathRequestMatcher("/api/**"));
	
	private static final RequestMatcher PUBLICURLS = new NegatedRequestMatcher(SECUREDURLS);
	
	@Autowired
	private ApplicationAuthenticationProvider authenticationProvider;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
			.and() 				//terminal back to http parameter, either and() or disable(), etc
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.logout().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling().defaultAuthenticationEntryPointFor(authenticationEntryPoint(), SECUREDURLS)
			.and()
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class)
			.authorizeRequests().requestMatchers(SECUREDURLS).authenticated();
	}

	private TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
		TokenAuthenticationFilter filter = new TokenAuthenticationFilter(SECUREDURLS, authenticationService);
        filter.setAuthenticationManager(authenticationManager());


        SimpleUrlAuthenticationSuccessHandler successHandler = new AuthenticationSuccessHandler();
        successHandler.setRedirectStrategy(new NoRedirectStrategy());
        filter.setAuthenticationSuccessHandler(successHandler);


        SimpleUrlAuthenticationFailureHandler failureHandler = new AuthenticationFailureHandler();
        failureHandler.setAllowSessionCreation(false);
        failureHandler.setRedirectStrategy(new NoRedirectStrategy());
        filter.setAuthenticationFailureHandler(failureHandler);
        return filter;
    }

	private AuthenticationEntryPoint authenticationEntryPoint() {
		return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
	}
	
	
}
