package com.ongraph.usermanagementapp.service;

import com.ongraph.usermanagementapp.dto.LoginRequest;
import com.ongraph.usermanagementapp.dto.SignupRequest;
import com.ongraph.usermanagementapp.security.model.UserDetailsImpl;
import com.ongraph.usermanagementapp.dto.JwtData;

public interface UserService {

	public UserDetailsImpl addUser(SignupRequest signupRequest);
	
	public JwtData loginUser(LoginRequest loginRequest);
}
