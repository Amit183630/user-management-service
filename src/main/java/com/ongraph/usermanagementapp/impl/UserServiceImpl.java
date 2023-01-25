package com.ongraph.usermanagementapp.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ongraph.usermanagementapp.dto.JwtData;
import com.ongraph.usermanagementapp.dto.LoginRequest;
import com.ongraph.usermanagementapp.dto.SignupRequest;
import com.ongraph.usermanagementapp.entity.Role;
import com.ongraph.usermanagementapp.entity.User;
import com.ongraph.usermanagementapp.exception.CustomException;
import com.ongraph.usermanagementapp.model.ErrorCodes;
import com.ongraph.usermanagementapp.repository.RoleRepository;
import com.ongraph.usermanagementapp.repository.UserRepository;
import com.ongraph.usermanagementapp.security.model.UserDetailsImpl;
import com.ongraph.usermanagementapp.security.util.JwtUtil;
import com.ongraph.usermanagementapp.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableJpaAuditing
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Override
	public UserDetailsImpl addUser(SignupRequest signupRequest) {
		validateSignupRequest(signupRequest);
		return UserDetailsImpl.build(
				userRepository.save(populateUser(signupRequest)));
	}
	
	@Override
	public JwtData loginUser(LoginRequest loginRequest) {
		Authentication authentication=authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		var token=jwtUtil.generateToken(authentication);
		var userDetails=(UserDetailsImpl)authentication.getPrincipal();
		
		return new JwtData(userDetails,token);
	}
	
	private void validateSignupRequest(SignupRequest signupRequest) {
		
		if(signupRequest!=null) {

			if(userRepository.existsByUserName(signupRequest.getUserName())) {
				throw new CustomException(ErrorCodes.E_BAD400,
						"User already exist with username:"+signupRequest.getUserName());
			}
		}else {
			log.debug("validateSignupRequest()->signupRequest is null");
			throw new CustomException(ErrorCodes.E_NOTFOUND404, "validateSignupRequest()->signupRequest is null");
		}
	}
	
	private User populateUser(SignupRequest request) {

		Optional<Role> optional=roleRepository.findByUserRole(request.getRole());
		if(optional.isPresent()) {
			Set<Role> roles=new HashSet<>();
			roles.add(optional.get());
			
			return User.builder()
					.email(request.getEmail())
					.enabled(true)
					.firstName(request.getFirstName())
					.lastName(request.getLastName())
					.password(encoder.encode(request.getPassword()))
					.phoneNo(request.getPhoneNo())
					.roles(roles)
					.userName(request.getUserName())
					.confirmationToken(UUID.randomUUID().toString())
					.build();
		}else {
			throw new CustomException(ErrorCodes.E_ISE500,"Role not found:"+request.getRole().name());
		}
		
	}
}
