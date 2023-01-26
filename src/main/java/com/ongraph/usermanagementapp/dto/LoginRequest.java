package com.ongraph.usermanagementapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {

	@NotBlank
	private String username;
	@NotBlank
	private String password;
}
