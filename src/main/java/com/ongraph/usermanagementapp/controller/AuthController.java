package com.ongraph.usermanagementapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ongraph.usermanagementapp.dto.SignupRequest;
import com.ongraph.usermanagementapp.model.DataResponse;
import com.ongraph.usermanagementapp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<DataResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {

		return ResponseEntity.ok(
				new DataResponse(true,
						userService.addUser(signupRequest)
						)
				);
	}
}
