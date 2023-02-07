package com.ongraph.usermanagementapp.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ongraph.commonserviceapp.model.UserDetailsImpl;
import com.ongraph.usermanagementapp.context.UserDetailsContextHolder;
import com.ongraph.usermanagementapp.repository.UserRepository;
import com.ongraph.usermanagementapp.transformer.ModelTransformer;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		var user=userRepository.findByUserName(username).orElseThrow(()->new UsernameNotFoundException("User not found with username:"+username));
		var userDetails=ModelTransformer.convertToUserDetails(user);
		UserDetailsContextHolder.set(userDetails);
		return UserDetailsImpl.build(userDetails);
	}
}
