package org.jwick.usermanagementapp.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.jwick.commonserviceapp.security.filter.AuthEntryPoint;
import org.jwick.commonserviceapp.security.filter.AuthTokenFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
	
	private String[] unsecuredPaths={"/auth/**"};
			
	private AuthenticationProvider authenticationProvider;
	
	private AuthTokenFilter authTokenFilter;
	
	private AuthEntryPoint unauthorizedHandler;
	
	
	public WebSecurityConfig(AuthenticationProvider authenticationProvider,AuthTokenFilter authTokenFilter,
			AuthEntryPoint authEntryPoint) {
		this.authenticationProvider=authenticationProvider;
		this.authTokenFilter=authTokenFilter;
		this.unauthorizedHandler=authEntryPoint;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeHttpRequests(authorizeHttpRequest->
			authorizeHttpRequest.requestMatchers(this.unsecuredPaths).permitAll()
			.anyRequest().authenticated()
		)
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
		.httpBasic(withDefaults())
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
		return http.build();
		
	}

}
