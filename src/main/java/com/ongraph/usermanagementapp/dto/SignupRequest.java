package com.ongraph.usermanagementapp.dto;

import com.ongraph.usermanagementapp.model.UserRoles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignupRequest {

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String userName;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;
 
	@NotBlank
	private String phoneNo;

	@NotNull
	private UserRoles role;
	
}
