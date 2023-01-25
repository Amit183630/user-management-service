package com.ongraph.usermanagementapp.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.ongraph.usermanagementapp.security.filter.AuthEntryPoint;
import com.ongraph.usermanagementapp.security.filter.AuthTokenFilter;
import com.ongraph.usermanagementapp.security.impl.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	private String[] unsecuredPaths={"/auth/**"};
			

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,AuthEntryPoint unauthorizedHandler) throws Exception {
		
		http.csrf().disable()
		.authorizeHttpRequests(authorizeHttpRequest->
			authorizeHttpRequest.requestMatchers(this.unsecuredPaths).permitAll()
			.anyRequest().authenticated()
		)
		.authenticationProvider(authenticationProvider())
		.addFilterBefore(authenticationJwtTokenFilter(), BasicAuthenticationFilter.class)
		.httpBasic(withDefaults())
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
		return http.build();
		
	}

}
