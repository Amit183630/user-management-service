package com.ongraph.usermanagementapp.model;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ongraph.usermanagementapp.entity.Role;
import com.ongraph.usermanagementapp.entity.UserLoginHistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

	private UUID id;
	
	private String firstName;
	
	private String lastName;
	
	private String userName;
	
	private String email;
	
	private String phoneNo;
	
	private boolean enabled;
	
	private Set<Role> roles;
	@JsonIgnoreProperties(value = {"user","id"})
	private Set<UserLoginHistory>loginHistories;
	
}
