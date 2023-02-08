package com.ongraph.usermanagementapp.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ongraph.commonserviceapp.cache.UserCacheRepository;
import com.ongraph.commonserviceapp.context.UserDetailsContextHolder;
import com.ongraph.commonserviceapp.model.ErrorCodes;
import com.ongraph.commonserviceapp.model.UserDetails;
import com.ongraph.commonserviceapp.model.UserDetailsImpl;
import com.ongraph.commonserviceapp.util.JwtUtil;
import com.ongraph.usermanagementapp.dto.AddLoginHistoryDTO;
import com.ongraph.usermanagementapp.dto.JwtData;
import com.ongraph.usermanagementapp.dto.LoginRequest;
import com.ongraph.usermanagementapp.dto.SignupRequest;
import com.ongraph.usermanagementapp.entity.Role;
import com.ongraph.usermanagementapp.entity.User;
import com.ongraph.usermanagementapp.exception.CustomException;
import com.ongraph.usermanagementapp.repository.RoleRepository;
import com.ongraph.usermanagementapp.repository.UserRepository;
import com.ongraph.usermanagementapp.service.UserLoginHistoryService;
import com.ongraph.usermanagementapp.service.UserService;
import com.ongraph.usermanagementapp.transformer.ModelTransformer;
import com.ongraph.usermanagementapp.util.Common;
import com.ongraph.usermanagementapp.util.Time;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
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
	UserCacheRepository userCacheRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Value("${ongraph.kafka.emit}")
	private boolean emit;
	
	@Autowired
	HttpServletRequest httpServletRequest;
	
	@Autowired
	UserLoginHistoryService loginHistoryService;
	
	
	@Override
	@Transactional
	public UserDetails addUser(SignupRequest signupRequest) {
		log.debug("addUser()-> signupRequest:{}",signupRequest);
		validateSignupRequest(signupRequest);
		return ModelTransformer.convertToUserDetails(
				userRepository.save(populateUser(signupRequest)));
	}
	
	@Override
	public JwtData loginUser(LoginRequest loginRequest) {
		log.debug("loginUser()-> loginRequest:{}",loginRequest);
		try {

			Authentication authentication=authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			var token=jwtUtil.generateToken(authentication);
			addLoginHistory(true, loginRequest.getUsername());
			var userDetailsImpl=(UserDetailsImpl)authentication.getPrincipal();
			setLogginUserDetailsInCache();
			return new JwtData(userDetailsImpl,token);
		}catch(BadCredentialsException e) {
			addLoginHistory(false, loginRequest.getUsername());
			throw new CustomException(ErrorCodes.E_AUTH401, "Invalid password");
		}
		catch(UsernameNotFoundException e) {
			throw new CustomException(ErrorCodes.E_AUTH401, e.getMessage());
		}
		catch(AuthenticationException e) {
			log.error("AuthenticationException:{}",e);
			throw new CustomException(ErrorCodes.E_AUTH401, e.getMessage());
		}
		
	}
	
	@Override
	public UserDetails getUserDetails(String userName) {
		log.debug("getUserDetails()->userName:{}",userName);
		User user= userRepository.findByUserName(userName).orElseThrow(()->new CustomException(ErrorCodes.E_NOTFOUND404, "User not found with username:"+userName));
		return ModelTransformer.convertToUserDetails(user);
	}
	
	private void validateSignupRequest(SignupRequest signupRequest) {
		log.debug("validateSignupRequest()-> signupRequest:{}",signupRequest);
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
		log.debug("populateUser()-> signupRequest:{}",request);

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
	
	private void addLoginHistory(boolean loggedIn,String username) {
		var ip=Common.getIpAddress(this.httpServletRequest);
		
		AddLoginHistoryDTO loginHistoryDTO=AddLoginHistoryDTO.builder()
				.ip(ip)
				.loggedIn(loggedIn)
				.loggedInTime(Time.getCurrentTime())
				.username(username)
				.build();
		if(emit) {
			loginHistoryService.emitAddLoginHistoryEvent(loginHistoryDTO);
		}else {
			loginHistoryService.addLoginHistory(loginHistoryDTO);
		}
	}
	
	private void setLogginUserDetailsInCache() {
		var userDetails=UserDetailsContextHolder.getContext();
		userCacheRepository.saveOrUpdate(userDetails);
	}
	
	
	
}
