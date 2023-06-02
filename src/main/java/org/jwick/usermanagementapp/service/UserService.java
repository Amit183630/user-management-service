package org.jwick.usermanagementapp.service;

import org.jwick.usermanagementapp.dto.JwtData;
import org.jwick.usermanagementapp.dto.LoginRequest;
import org.jwick.usermanagementapp.dto.SignupRequest;
import org.jwick.commonserviceapp.model.UserDetails;

public interface UserService {

	public UserDetails addUser(SignupRequest signupRequest);
	
	public JwtData loginUser(LoginRequest loginRequest);
	
	public UserDetails getUserDetails(String userName);
	
	public void accountConfirmation(String confirmationToken);
	
}
