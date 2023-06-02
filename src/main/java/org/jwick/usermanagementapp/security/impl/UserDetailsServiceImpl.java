package org.jwick.usermanagementapp.security.impl;

import org.jwick.usermanagementapp.repository.UserRepository;
import org.jwick.usermanagementapp.transformer.ModelTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.jwick.commonserviceapp.context.UserDetailsContextHolder;
import org.jwick.commonserviceapp.model.UserDetailsImpl;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		var user=userRepository.findByUserName(username).orElseThrow(()->new UsernameNotFoundException("User not found with username:"+username));
		var userDetails= ModelTransformer.convertToUserDetails(user);
		UserDetailsContextHolder.set(userDetails);
		return UserDetailsImpl.build(userDetails);
	}
}
