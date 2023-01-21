package com.ongraph.usermanagementapp.service;

import com.ongraph.usermanagementapp.dto.SignupRequest;
import com.ongraph.usermanagementapp.dto.UserDetails;

public interface UserService {

	public UserDetails addUser(SignupRequest signupRequest);
}
