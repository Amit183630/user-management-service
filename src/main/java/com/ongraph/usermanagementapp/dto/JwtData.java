package com.ongraph.usermanagementapp.dto;

import java.util.List;
import java.util.UUID;

import com.ongraph.usermanagementapp.security.model.UserDetailsImpl;

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
		this.roles=user.getAuthorities().stream().map(item->item.getAuthority()).toList();
		this.token=token;
	}

}
