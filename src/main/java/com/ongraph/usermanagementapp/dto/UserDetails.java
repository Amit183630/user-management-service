package com.ongraph.usermanagementapp.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.ongraph.usermanagementapp.entity.Role;
import com.ongraph.usermanagementapp.entity.User;

import lombok.Data;

@Data
public class UserDetails {

	private UUID id;
	
	private String firstName;
	
	private String lastName;
	
	private String userName;
	
	private String email;
	
	private String phoneNo;
	
	private Set<Role> roles=new HashSet<>();
	
	public UserDetails(User user) {
		this.id=user.getId();
		this.firstName=user.getFirstName();
		this.lastName=user.getLastName();
		this.userName=user.getUserName();
		this.email=user.getEmail();
		this.phoneNo=user.getPhoneNo();
		this.roles=user.getRoles();
	}
}
