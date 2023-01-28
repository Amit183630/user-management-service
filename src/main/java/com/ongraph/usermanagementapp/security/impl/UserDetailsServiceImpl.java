package com.ongraph.usermanagementapp.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ongraph.usermanagementapp.exception.CustomException;
import com.ongraph.usermanagementapp.model.ErrorCodes;
import com.ongraph.usermanagementapp.repository.UserRepository;
import com.ongraph.usermanagementapp.security.model.UserDetailsImpl;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	@Cacheable(value = "user_details")
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		var user=userRepository.findByUserName(username).orElseThrow(()->new CustomException(ErrorCodes.E_NOTFOUND404, "User not found with username:"+username));
		return UserDetailsImpl.build(user);
	}
}
