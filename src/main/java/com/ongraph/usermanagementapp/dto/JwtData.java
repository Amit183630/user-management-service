package com.ongraph.usermanagementapp.dto;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import com.ongraph.commonserviceapp.model.UserDetailsImpl;

import lombok.Data;

@Data
public class JwtData {

	private UUID id;
	
	private String userName;
	
	private String email;
	
	private List<String> roles;
	
	private String token;
	
	public JwtData(UserDetailsImpl user,String token) {
		this.id=user.getId();
		this.userName=user.getUsername();
		this.email=user.getEmail();
		this.roles=user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		this.token=token;
	}

}
