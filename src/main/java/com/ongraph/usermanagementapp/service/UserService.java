package com.ongraph.usermanagementapp.service;

import com.ongraph.commonserviceapp.model.UserDetails;
import com.ongraph.usermanagementapp.dto.JwtData;
import com.ongraph.usermanagementapp.dto.LoginRequest;
import com.ongraph.usermanagementapp.dto.SignupRequest;
import com.ongraph.usermanagementapp.entity.User;

public interface UserService {

	public UserDetails addUser(SignupRequest signupRequest);
	
	public JwtData loginUser(LoginRequest loginRequest);
	
	public UserDetails getUserDetails(String userName);
	
	public void accountConfirmation(String confirmationToken);
	
}
