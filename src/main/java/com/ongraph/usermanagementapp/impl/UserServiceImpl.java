package com.ongraph.usermanagementapp.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ongraph.usermanagementapp.dto.SignupRequest;
import com.ongraph.usermanagementapp.dto.UserDetails;
import com.ongraph.usermanagementapp.entity.User;
import com.ongraph.usermanagementapp.exception.CustomException;
import com.ongraph.usermanagementapp.model.ErrorCodes;
import com.ongraph.usermanagementapp.repository.UserRepository;
import com.ongraph.usermanagementapp.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails addUser(SignupRequest signupRequest) {
		validateSignupRequest(signupRequest);
		return new UserDetails(
				userRepository.save(
						new User(signupRequest)));
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
}
